package dk.cngroup.lentils.entity.view;

public class Rank {
    private final int fromPlace;
    private final int toPlace;

    public Rank(final int fromPlace, final int toPlace) {
        this.fromPlace = fromPlace;
        this.toPlace = toPlace;
    }

    public int getFromPlace() {
        return fromPlace;
    }

    public int getToPlace() {
        return toPlace;
    }

    @Override
    public String toString() {
        if (fromPlace == toPlace) {
            return String.format("%d.", fromPlace);
        }
        return String.format("%d. - %d.", fromPlace, toPlace);
    }
}
