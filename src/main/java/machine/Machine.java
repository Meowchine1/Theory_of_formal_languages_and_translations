package machine;

import conditions.Condition;
import values.Value;

import java.util.ArrayList;

public abstract class Machine {
    private ArrayList<Condition> conditions;
    private ArrayList<Value> values;
    private String filePath;

    public Machine(String filePath){
        this.filePath = filePath;
    }
    public void readTxt(){}

}
