package conditions;

import conditions.Condition;
import values.Value;

public class ZeroCondition implements Condition {

    private String name;
    public ZeroCondition(){}
    public ZeroCondition(String name) {

        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
