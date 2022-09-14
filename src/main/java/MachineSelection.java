import conditions.Condition;
import conditions.UsualCondition;
import values.Value;

import java.util.HashMap;

public class MachineSelection {
    private UsualCondition actualCondition;
    private HashMap<Condition, HashMap<Value, Condition>> values;

    public MachineSelection(HashMap<Condition, HashMap<Value, Condition>> values) {
        this.values = values;
    }

    public void changeCond(){}

}
