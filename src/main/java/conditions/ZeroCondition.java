package conditions;

import conditions.Condition;
import values.Value;

public class ZeroCondition implements Condition {

    private static ZeroCondition instance;

    public static synchronized ZeroCondition getInstance() {
        if (instance == null) {
            instance = new ZeroCondition();
        }
        return instance;
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
}
