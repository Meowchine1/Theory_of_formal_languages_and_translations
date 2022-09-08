package machine;

import conditions.Condition;
import values.Value;

import java.util.ArrayList;
import java.util.HashMap;

public class NDA_e_transitions extends Machine {

    private HashMap<Condition, HashMap<Value, ArrayList<Condition>>> transitions;

    public NDA_e_transitions(String filePath) {
        super(filePath);
    }

    @Override
    public void readTxt() {

    }

}
