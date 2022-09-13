package domain;

import ui.MasterMindUI;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class GuessedColorCode implements ColorCodeInterface {

    private final List<String> guessColorCode;

    public GuessedColorCode (List<String> guessedColorCode) {

        guessColorCode = guessedColorCode;
    }

    @Override
    public List<String> get() {

        return guessColorCode;
    }
}
