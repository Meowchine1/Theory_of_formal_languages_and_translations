package conditions;

import java.util.HashMap;

public class DeterministicCondition implements Condition {
    private String name;
    private boolean isEnded;
    private boolean isStarted;


    public DeterministicCondition(String name, boolean isEnded, boolean isStarted ) {
        this.name = name;
        this.isEnded = isEnded;
        this.isStarted = isStarted;
    }
}
