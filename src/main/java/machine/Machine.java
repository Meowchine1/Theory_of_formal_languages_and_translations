package machine;
// вывод таблицы
//     ....  values  ....
// cond[i]
import conditions.Condition;
import conditions.MultipleCondition;
import values.Value;

import java.io.File;
import java.util.ArrayList;

public abstract  class Machine {
    protected static ArrayList<MultipleCondition> multipleConditions;
    protected static ArrayList<Condition> conditions;
    protected static ArrayList<Value> values;
    protected static ArrayList<String > words;
    protected static File fileInPath;
    protected static File fileOutPath;

    public Machine() {

    }


    public Value getValueByName(String name){
        return values.stream().filter(v -> v.getValue().equals(name)).findFirst().get();
    }
    public Condition getConditionByName(String name){
        return conditions.stream().filter(v -> v.getName().equals(name)).findFirst().get();
    }

   // public MultipleCondition getMultipleConditionByConditions( MultipleCondition condition){


      // }

    public Machine(File fileInPath, File fileOutPath){
        this.fileInPath = fileInPath;
        this.fileOutPath = fileOutPath;
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

    protected void readWord(File wordFile){}

    protected void executeWord(){}

    protected void changeActualCondition(String word){}
    public void work(File wordFile){}

}
