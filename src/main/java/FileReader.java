import machine.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class FileReader {
    private String fileIn;
    private String fileOut;
    public FileReader(String fileIn, String fileOut){
        this.fileIn = fileIn;
        this.fileOut = fileOut;
    }
    public Machine defineMachine(){
        int startedConditionsNum = 0;
        try{
            Scanner scanner = new Scanner(new File(fileIn));
            String[] numbers = scanner.nextLine().replaceAll(" ", "").split(";");

            String values = scanner.nextLine();

            if (values.contains("e")){
                return new NDA_e_transitions(fileIn, fileOut);
            }
            else{
                String ndaRegex = "[{](\\w;){2,}[}]";
                Pattern pattern = Pattern.compile(ndaRegex);
                while(scanner.hasNextLine()){
                    String line = scanner.nextLine();

                    String[] conditions = line.replaceAll(" ", "").split(";");
                    if(conditions[0].contains("^")){
                        startedConditionsNum++;
                    }
                    if(pattern.matcher(line).matches()){
                        return new KNDA(fileIn, fileOut);
                    }
                }
                if(startedConditionsNum == 1){
                    return new KDA(fileIn, fileOut);
                }

            }

        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return new NotFound(fileIn, fileOut);
    }
            // n k
            //     x1 x2 x3    values
            // y1  y3  y1  .
            // y2
            // y3
            //  * -- ending

}
