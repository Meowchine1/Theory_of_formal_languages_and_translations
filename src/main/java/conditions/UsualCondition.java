package conditions;

import java.util.LinkedHashSet;

public class UsualCondition implements Condition {
    private String name;
    private boolean isEnded;
    private boolean isStarted;

    public String getName() {
        return name;
    }

    public boolean isEnded() {
        return isEnded;
    }

    public boolean isStarted() {
        return isStarted;
    }

    @Override
    public void SetEnded(Boolean ended) {
        this.isEnded = ended;
    }

    @Override
    public LinkedHashSet<Condition> getConditions() {
        LinkedHashSet<Condition> conditions = new LinkedHashSet<>();
        conditions.add(this);
        return  conditions;
    }

    public UsualCondition(String name, boolean isEnded, boolean isStarted ) {
        this.name = name;
        this.isEnded = isEnded;
        this.isStarted = isStarted;
    }
}
