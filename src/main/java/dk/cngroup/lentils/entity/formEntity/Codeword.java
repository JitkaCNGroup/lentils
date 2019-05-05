package dk.cngroup.lentils.entity.formEntity;

public class Codeword {

    private String guess;

    public String getGuess() {
        return guess;
    }

    public void setGuess(final String guess) {
        this.guess = guess;
    }

    @Override
    public String toString() {
        return guess;
    }
}
