package domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GuessedColorCode implements ColorCodeInterface {

    private final List<String> guessedColorCode;

    public GuessedColorCode (String guessedColorCode) {

        this.guessedColorCode = stringToColorCode(guessedColorCode);
        //System.out.println("Guessed code is " + getColorCode());
    }

    private List<String> stringToColorCode(String givenColorCode) {

        List<String> splitCode = List.of(givenColorCode.split("\s"));

        if ( splitCode.size() == numberColors && splitCode.stream().allMatch(availableColors::contains)) {

            return new ArrayList<>(Arrays.asList(givenColorCode.split("\s")).subList(0, numberColors));
        } else {
            return null;
        }

    }

    @Override
    public List<String> getColorCode() {

        return guessedColorCode;
    }
}
