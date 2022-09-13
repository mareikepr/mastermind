package domain;

import ui.MasterMindUI;

import java.util.*;

public class GameLogic {

    private final MasterMindUI masterMindUI;
    private final ColorCodeInterface genColorCode;
    private final List<String> genCode;
    private List<String> guessCode;
    private List<String> tempGuessCode;
    private List<String> tempGenCode;

    public GameLogic (MasterMindUI masterMindUI) {

        this.masterMindUI = masterMindUI;
        genColorCode = new GeneratedColorCode();
        genCode = genColorCode.get();
        gameLoop();
    }

    public void gameLoop () {

        boolean correctGuess;
        int guessCount = 0;

         do {
            guessCount += 1;
            getValidCode(guessCount);
            correctGuess = genCode.equals(guessCode);

            if (!correctGuess) {
                List<String> blackWhiteStringArray = checkBlackWhite();
                masterMindUI.printResponse(blackWhiteStringArray);
            }
         } while (!correctGuess);

        masterMindUI.printEnd(guessCode, guessCount);

    }

    private void getValidCode(int guessCount) {

        Optional<List<String>> optCode;

        do {
            Optional<String> optGuess = masterMindUI.makeAGuess(guessCount);

            if (optGuess.isPresent()) {
                optCode = getOptCode(optGuess.get());
            } else {
                masterMindUI.printBreak(genCode, guessCount);
                System.exit(0);
                optCode = Optional.empty();
            }
        } while (optCode.isEmpty());

        ColorCodeInterface guessedColorCode = new GuessedColorCode(optCode.get());
        guessCode = guessedColorCode.get();
    }

    private Optional<List<String>> getOptCode (String guess) {

        List<String> splitCode = List.of(guess.split("\s"));

        if ( splitCode.size() == genColorCode.numberColors && genColorCode.availableColors.containsAll(splitCode)) {

            return Optional.of(new ArrayList<>(Arrays.asList(guess.split("\s")).subList(0, genColorCode.numberColors)));
        } else {
            masterMindUI.printInvalidCodeMessage();
            return Optional.empty();
        }
    }

    private List<String> checkBlackWhite() {

        List<String> blackWhites = new ArrayList<>();

        tempGuessCode = new ArrayList<>(guessCode);
        tempGenCode = new ArrayList<>(genCode);

        List<Integer> blackIndex = checkBlack();
        List<Integer> whiteIndex = checkWhite();

        blackIndex.forEach(b -> blackWhites.add("black"));
        whiteIndex.forEach(w -> blackWhites.add("white"));

        return blackWhites;
    }

    private List<Integer> checkBlack() {

        List<Integer> blackIndex = new ArrayList<>();

        for (int i = 0; i < genCode.size(); i++) {
            if (guessCode.get(i).equals(genCode.get(i))) {
                blackIndex.add(1);
                tempGuessCode.set(i, "checked");
                tempGenCode.set(i, "checked");
            }
        }
        return blackIndex;
    }

    private List<Integer> checkWhite() {

        List<Integer> whiteIndex = new ArrayList<>();

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
        return whiteIndex;
    }
}
