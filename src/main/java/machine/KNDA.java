package machine;

import conditions.Condition;
import values.Value;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
// несколько начальных состояний распараллеливание сразу
public class KNDA extends Machine {

    private HashMap<Condition, HashMap<Value, ArrayList<Condition>>> transitions;

    public KNDA(String filePath) {
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
