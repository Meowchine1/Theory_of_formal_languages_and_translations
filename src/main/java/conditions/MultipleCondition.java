package conditions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MultipleCondition implements Condition, Iterable<Condition>, Iterator<Condition> {

    private int count = 0;
    private  ArrayList<Condition> conditions = new ArrayList<>();

    public void AddCondition(Condition condition){
        conditions.add(condition);
    }

    public void  ClearCondition(){
        conditions.clear();
    }

    @Override
    public String getName() {
        return "multiple";
    }

    @Override
    public boolean isEnded() {
        return false;
    }

    @Override
    public boolean isStarted() {
        return false;
    }


    @Override
    public Iterator<Condition> iterator() {
        return this;
    }

    @Override
    public boolean hasNext() {
        if (count < conditions.size()){
            return true;
        }
        count = 0;
        return false;
    }

    @Override
    public Condition next() {
        if (count == conditions.size())
            throw new NoSuchElementException();

        count++;
        return conditions.get(count - 1);
    }
}
