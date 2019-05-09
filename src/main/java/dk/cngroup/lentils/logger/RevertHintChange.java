package dk.cngroup.lentils.logger;

public class RevertHintChange {
    private final Long hintId;
    private final boolean success;

    public RevertHintChange(Long hintId, boolean success) {
        this.hintId = hintId;
        this.success = success;
    }

    @Override
    public String toString() {
        return "TakeHintChange{" +
                "hintId=" + hintId +
                ", success=" + success +
                '}';
    }
}
