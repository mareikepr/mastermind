package domain;

import java.util.List;

public class GuessedColorCode implements ColorCode {

    private final List<String> guessColorCode;

    public GuessedColorCode (List<String> guessColorCode) {

        this.guessColorCode = guessColorCode;
    }

    @Override
    public List<String> get() {

        return guessColorCode;
    }

    @Override
    public String getColor(int index) {
        return guessColorCode.get(index);
    }
}
