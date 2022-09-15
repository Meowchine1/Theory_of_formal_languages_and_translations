package machine;

import conditions.Condition;
import values.Value;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class NDA_e_transitions extends Machine {

    private HashMap<Condition, HashMap<Value, ArrayList<Condition>>> transitions;

    public NDA_e_transitions(String fileInPath, String fileOutPath) {
        super(fileInPath, fileOutPath);
    }

    @Override
    public void readTxt() {
        try {
            Scanner scanner = new Scanner(new File(fileInPath));
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
