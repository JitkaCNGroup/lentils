package dk.cngroup.lentils.logger;

public class RevertHintChange {
    private final Long hintId;
    private final boolean seccuss;

    public RevertHintChange(Long hintId, boolean success) {
        this.hintId = hintId;
        this.seccuss = success;
    }

    @Override
    public String toString() {
        return "TakeHintChange{" +
                "hintId=" + hintId +
                ", seccuss=" + seccuss +
                '}';
    }
}
