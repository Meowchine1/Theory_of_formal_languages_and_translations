package machine;

import conditions.Condition;
import values.Value;

import java.util.HashMap;

public class KDA extends Machine {

    private HashMap<Condition, HashMap<Value, Condition>> transitions;

    public KDA(String filePath) {
        super(filePath);
    }

    @Override
    public void readTxt() {

    }
}
