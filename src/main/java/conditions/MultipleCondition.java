package conditions;

import java.util.*;

public class MultipleCondition implements Condition {
    private LinkedHashSet<Condition> conditions = new LinkedHashSet<>();

    public void addCondition(Condition condition){
        conditions.add(condition);
    }

    public void addAll(Set<Condition> conditions){
        this.conditions.addAll(conditions);

    }
    public MultipleCondition() {
    }

    public LinkedHashSet<Condition> getConditions(){
        return conditions;
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
    public void SetEnded(Boolean ended) {

    }





}
