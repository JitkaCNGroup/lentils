package dk.cngroup.lentils.logger.change;

public class TakeHintChange {
    private final Long hintId;
    private final boolean success;

    public TakeHintChange(final Long hintId, final boolean success) {
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
