package machine;

import conditions.Condition;
import values.Value;

import java.util.ArrayList;
import java.util.HashMap;

public class KNDA extends Machine {

    private HashMap<Condition, HashMap<Value, ArrayList<Condition>>> transitions;

    public KNDA(String filePath) {
        super(filePath);
    }

    @Override
    public void readTxt() {

    }

}
