package domain;

import ui.MasterMindUI;

import java.util.*;

public class GameLogic {

    MasterMindUI masterMindUI;

    private List<String> guessCode = new ArrayList<>();
    private final List<String> genCode;

    public GameLogic (MasterMindUI masterMindUI) {

        this.masterMindUI = masterMindUI;
        ColorCodeInterface genColorCode = new GeneratedColorCode();
        genCode = genColorCode.getColorCode();
        gameLoop();
    }

    public void gameLoop () {

        boolean correctGuess = false;
        int guessCount = 0;

        while (!correctGuess) {

            guessCount += 1;
            guessCode = getValidCode(guessCount);

            if (genCode.equals(guessCode)) {
                correctGuess = genCode.equals(guessCode);
                masterMindUI.printEnd(guessCode);
            } else {
                List<String> blackWhiteStringArray = checkBlackWhite();
                masterMindUI.printResponse(blackWhiteStringArray);
            }
        }
    }

    private List<String> getValidCode(int guessCount) {

        boolean validCode = false;
        GuessedColorCode guessedColorCode;
        do {
            String guess = masterMindUI.makeAGuess(guessCount);
            guessedColorCode = new GuessedColorCode(guess);
            guessCode = guessedColorCode.getColorCode();

            if (guessCode != null) {
                validCode = true;
            } else {
                masterMindUI.printInvalidCodeMessage();
            }
        } while (!validCode);

        return guessCode;
    }

    private List<String> checkBlackWhite() {

        List<String> blackWhites = new ArrayList<>();

        List<String> tempGuessCode = new ArrayList<>(guessCode);
        List<String> tempGenCode = new ArrayList<>(genCode);

        List<Integer> blackIndex = new ArrayList<>();
        List<Integer> whiteIndex = new ArrayList<>();

        for (int i = 0; i < genCode.size(); i++) {
            if (guessCode.get(i).equals(genCode.get(i))) {
                blackIndex.add(1);
                tempGuessCode.set(i, "checked");
                tempGenCode.set(i, "checked");
            }
        }

        for (int i = 0; i < genCode.size(); i++) {
            if (tempGenCode.get(i).equals("checked")) {
                continue;
            }
            for (int j = 0; j < genCode.size(); j++) {

                if (tempGuessCode.get(j).equals("checked")) {
                    continue;
                }
                if (tempGenCode.get(i).equals(tempGuessCode.get(j))) {
                    whiteIndex.add(1);
                    tempGenCode.set(i, "checked");
                    tempGuessCode.set(j, "checked");
                }
            }
        }

        blackIndex.forEach(b -> blackWhites.add("black"));
        whiteIndex.forEach(w -> blackWhites.add("white"));

        return blackWhites;
    }
}
