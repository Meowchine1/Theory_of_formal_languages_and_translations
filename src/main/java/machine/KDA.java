package machine;

import conditions.Condition;
import values.Value;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class KDA extends Machine {

    private HashMap<Condition, HashMap<Value, Condition>> transitions;

    public KDA(String filePath) {
        super(filePath);
    }

    @Override
    public void readTxt() {
        try {
            Scanner scanner = new Scanner(new File(filePath));
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
