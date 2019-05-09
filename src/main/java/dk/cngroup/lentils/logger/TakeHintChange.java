package dk.cngroup.lentils.logger;

public class TakeHintChange {
    private final Long hintId;
    private final boolean success;

    public TakeHintChange(Long hintId, boolean success) {
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
