package dk.cngroup.lentils.entity.formEntity;

import org.springframework.stereotype.Component;

@Component
public class Codeword {

    private String guess;

    public String getGuess() {
        return guess;
    }

    public void setGuess(String guess) {
        this.guess = guess;
    }
}
