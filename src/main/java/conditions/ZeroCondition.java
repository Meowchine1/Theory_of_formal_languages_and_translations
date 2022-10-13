package conditions;

import conditions.Condition;
import values.Value;

import java.util.LinkedHashSet;

public class ZeroCondition implements Condition {

    private static ZeroCondition instance;

    public static synchronized ZeroCondition getInstance() {
        if (instance == null) {
            instance = new ZeroCondition();
        }
        return instance;
    }
    @Override
    public LinkedHashSet<Condition> getConditions() {
        LinkedHashSet<Condition> conditions = new LinkedHashSet<>();
        conditions.add(this);
        return  conditions;
    }
    @Override
    public String getName() {
        return "null";
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
