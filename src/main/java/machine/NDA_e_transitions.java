package machine;

import conditions.Condition;
import values.Value;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class NDA_e_transitions extends Machine {

    private HashMap<Condition, HashMap<Value, ArrayList<Condition>>> transitions;

    public NDA_e_transitions(String fileInPath, String fileOutPath) {
        super(fileInPath, fileOutPath);
    }

    @Override
    public Value getValueByName(String name) {
        return super.getValueByName(name);
    }

    @Override
    public Condition getConditionByName(String name) {
        return super.getConditionByName(name);
    }


    @Override
    protected boolean valueDoesNotExists(String valueName) {
        return super.valueDoesNotExists(valueName);
    }

    @Override
    protected boolean conditionDoesNotExists(String conditionName) {
        return super.conditionDoesNotExists(conditionName);
    }

    @Override
    protected boolean endedAndStartedConditionsExist() {
        return super.endedAndStartedConditionsExist();
    }

    @Override
    public void print() {
        super.print();
    }

    @Override
    protected void readWord(String wordFile) {
        super.readWord(wordFile);
    }

    @Override
    protected void executeWord() {
        super.executeWord();
    }

    @Override
    protected void changeActualCondition(String word) {
        super.changeActualCondition(word);
    }

    @Override
    public void work(String wordFile) {
        super.work(wordFile);
    }

    @Override
    public void readTxt() {
        try {
            Scanner scanner = new Scanner(new File(fileInPath));
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
