package machine;
// вывод таблицы
//     ....  values  ....
// cond[i]
import conditions.Condition;
import values.Value;

import java.util.ArrayList;

public abstract  class Machine {
    protected static ArrayList<Condition> conditions;
    protected static ArrayList<Value> values;
    protected static String filePath;
    protected static Condition actualCondition;

    public static void setActualCondition(Condition actualCondition) {
        Machine.actualCondition = actualCondition;
    }


    public Machine(String filePath){
        this.filePath = filePath;
    }
    public void readTxt(){}

}
