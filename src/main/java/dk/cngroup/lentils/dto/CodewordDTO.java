package dk.cngroup.lentils.dto;

public class CodewordDTO {

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
