import conditions.Condition;
import conditions.DeterministicCondition;
import conditions.ZeroCondition;
import machine.KNDA;
import machine.Machine;
import machine.NDA_e_transitions;
import machine.NotFound;
import values.Value;
import java.util.Map.Entry;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

public class FileReader {
    private MachineSelection machine;
    private Value[] values;
    private Condition[] conditions;
    private HashMap<Condition, HashMap<Value, String>> intermediate_transition = new HashMap<>();
    private String filePath = "example";


    public Machine defineMachine(){
        try{
            Scanner scanner = new Scanner(new File(filePath));
            String[] numbers = scanner.nextLine().replaceAll(" ", "").split(";");
            int valuesN = Integer.parseInt(numbers[0]);
            int condN = Integer.parseInt(numbers[1]);
            String ndaRegex = "[{](\\w;){2,}[}]";
            Pattern pattern = Pattern.compile(ndaRegex);
            while(scanner.hasNextLine()){
                 String line = scanner.nextLine();
                 if(line.contains("e")){
                    return new NDA_e_transitions(filePath);
                 }

                 if( pattern.matcher(line).matches()){
                     return new KNDA(filePath);
                 }
            }
            return new KNDA(filePath);

        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return new NotFound(filePath);
    }

    public FileReader() {
        try {
            Scanner scanner = new Scanner(new File("example"));
            while (scanner.hasNextLine()) {
                String[] numbers = scanner.nextLine().replaceAll(" ", "").split(";");
                int valuesN = Integer.parseInt(numbers[0]);
                int condN = Integer.parseInt(numbers[1]);

                values = new Value[valuesN];
                conditions = new DeterministicCondition[condN];

                String[] values_line = scanner.nextLine().replaceAll(" ", "").split(";");
                for(int i = 0; i< valuesN; i++){
                    Value value = new Value(values_line[i]);
                    values[i] = value;
                }
                HashMap<Value, String> transition;
                for(int i = 0; i< condN; i++){ // обработка * ^
                    String unit = scanner.nextLine();

                    String[] line = unit.replaceAll(" ", "").split(";");
                    String conditionInitialization = line[0];// if cond name contains ^ *
                    boolean isEnded_condition = conditionInitialization.contains("*");
                    boolean isStarted_condition = conditionInitialization.contains("^");
                    DeterministicCondition condition = new DeterministicCondition(line[0], isEnded_condition, isStarted_condition);
                    conditions[i] = condition;

                     transition = new HashMap<>(); // тк во время считывания строк автомата не все состояния определены определяю промкжуточный словарь

                    for(int j = 0; j < valuesN; j++){
                        Value value = values[j];     // ключ
                        int j_for_line = j + 1;
                       transition.put(value, line[j_for_line]);
                    }
                    intermediate_transition.put(condition, transition);
                }

                HashMap<Condition, HashMap<Value, Condition>> final_transition = new HashMap<>();
                for(Entry<Condition, HashMap<Value, String>> entry : intermediate_transition.entrySet()) {
                    Condition main_condition = entry.getKey();
                    HashMap<Value, Condition> unit_transition = new HashMap<>();
                    for (Map.Entry<Value, String> nameEntry : entry.getValue().entrySet()) {
                        Value value = nameEntry.getKey();
                        String condition = nameEntry.getValue();

                        if(condition.equals("0")){
                            unit_transition.put(value, new ZeroCondition());
                        }
                        else{
                            boolean isEnded_condition = condition.contains("*");
                            boolean isStarted_condition = condition.contains("^");
                            // unit_transition.put(value,new DeterministicCondition())
                        }
                        // ...
                    }



                    final_transition.put(main_condition, unit_transition);



                }
            }

            // n k
            //     x1 x2 x3    values
            // y1  y3  y1  .
            // y2
            // y3
            //  * -- ending

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
