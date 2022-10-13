package conditions;

import java.util.LinkedHashSet;

public interface Condition {
    public String getName();
    public boolean isEnded();
    public boolean isStarted();
    public void SetEnded(Boolean ended);
    public LinkedHashSet<Condition> getConditions();
}
