import conditions.Condition;
import conditions.DeterministicCondition;
import values.Value;

import java.util.HashMap;

public class MachineSelection {
    private DeterministicCondition actualCondition;
    private HashMap<Condition, HashMap<Value, Condition>> values;

    public MachineSelection(HashMap<Condition, HashMap<Value, Condition>> values) {
        this.values = values;
    }

    public void changeCond(){}

}
