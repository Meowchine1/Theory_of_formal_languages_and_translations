package conditions;

public class UsualCondition implements Condition {
    private String name;
    private boolean isEnded;
    private boolean isStarted;

    public String getName() {
        return name;
    }

    public UsualCondition(String name, boolean isEnded, boolean isStarted ) {
        this.name = name;
        this.isEnded = isEnded;
        this.isStarted = isStarted;
    }
}
