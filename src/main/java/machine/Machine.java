package machine;
// вывод таблицы
//     ....  values  ....
// cond[i]
import conditions.Condition;
import values.Value;

import java.util.ArrayList;

public abstract  class Machine {
    protected  ArrayList<Condition> conditions;
    protected  ArrayList<Value> values;
    protected ArrayList<String > words;
    protected  String fileInPath;
    protected  String fileOutPath;
    protected static Condition actualCondition;

    public Value getValueByName(String name){
        return values.stream().filter(v -> v.getValue().equals(name)).findFirst().get();
    }
    public Condition getConditionByName(String name){
        return conditions.stream().filter(v -> v.getName().equals(name)).findFirst().get();
    }
    public Machine(String fileInPath, String fileOutPath){
        this.fileInPath = fileInPath;
        this.fileOutPath = fileOutPath;
    }

    protected void setActualCondition(Condition actualCondition) {
        Machine.actualCondition = actualCondition;
    }

    protected String getActualConditionString(){
        return "\nActual condition is ==> " + actualCondition.getName();
    }

    protected  boolean valueDoesNotExists(String valueName){
        if(!values.isEmpty())
        {
            for (Value entry:values) {
                if(entry.getValue().equals(valueName)){
                    return false;

                }
            }
        }
        return true;
    }
    protected boolean conditionDoesNotExists(String conditionName){// не может быть нулевых

        if(!conditions.isEmpty()){
            for (Condition entry:conditions) {

                if(entry.getName().equals(conditionName)){
                    return false;
                }
            }
        }
        return true;
    }

    protected boolean endedAndStartedConditionsExist(){
        return conditions.stream().anyMatch(Condition::isEnded) & conditions.stream().anyMatch(Condition::isStarted);
    }

    public void readTxt(){}
    public void print(){}

    protected void readWord(String wordFile){}

    protected void executeWord(){}

    protected void changeActualCondition(String word){}
    public void work(String wordFile){}

}
