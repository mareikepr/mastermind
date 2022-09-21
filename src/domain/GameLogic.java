package domain;

import ui.MasterMindUI;

import java.util.*;

public class GameLogic {

    private final MasterMindUI masterMindUI;
    private final ColorCode genCode;
    private ColorCode guessCode;
    private List<String> tempGuessCode;
    private List<String> tempGenCode;

    public GameLogic (MasterMindUI masterMindUI) {

        this.masterMindUI = masterMindUI;
        genCode = new GeneratedColorCode(masterMindUI.mode);
        gameLoop();
    }

    public void gameLoop () {

        boolean correctGuess;
        int guessCount = 0;

         do {
            guessCount += 1;
            getValidCode(guessCount);
            correctGuess = genCode.get().equals(guessCode.get());

            if (!correctGuess) {
                List<String> blackWhiteStringArray = checkBlackWhite();
                masterMindUI.printResponse(blackWhiteStringArray);
            }
         } while (!correctGuess);

        masterMindUI.printEnd(guessCode.get(), guessCount);
    }

    private void getValidCode(int guessCount) {

        Optional<List<String>> optCode;

        do {
            Optional<String> optGuess = masterMindUI.makeAGuess(guessCount);

            if (optGuess.isPresent()) {
                optCode = getOptCode(optGuess.get());
            } else {
                masterMindUI.printBreak(genCode.get(), guessCount);
                System.exit(0);
                optCode = Optional.empty();
            }
        } while (optCode.isEmpty());

        guessCode = new GuessedColorCode(optCode.get());
    }

    private Optional<List<String>> getOptCode (String guess) {

        List<String> splitCode = List.of(guess.split("\s"));

        if ( splitCode.size() == genCode.numberColors && splitCode.stream().allMatch(genCode.availableColors::contains)) {

            return Optional.of(new ArrayList<>(Arrays.asList(guess.split("\s")).subList(0, genCode.numberColors)));
        } else {
            masterMindUI.printInvalidCodeMessage();
            return Optional.empty();
        }
    }

    private List<String> checkBlackWhite() {

        List<String> blackWhites = new ArrayList<>();

        tempGuessCode = new ArrayList<>(guessCode.get());
        tempGenCode = new ArrayList<>(genCode.get());

        List<Integer> blackIndex = checkBlack();
        List<Integer> whiteIndex = checkWhite();

        blackIndex.forEach(b -> blackWhites.add("black"));
        whiteIndex.forEach(w -> blackWhites.add("white"));

        return blackWhites;
    }

    private List<Integer> checkBlack() {

        List<Integer> blackIndex = new ArrayList<>();

        for (int i = 0; i < genCode.numberColors; i++) {
            if (guessCode.getColor(i).equals(genCode.getColor(i))) {
                blackIndex.add(1);
                tempGuessCode.set(i, "checked");
                tempGenCode.set(i, "checked");
            }
        }
        return blackIndex;
    }

    private List<Integer> checkWhite() {

        List<Integer> whiteIndex = new ArrayList<>();

        for (int i = 0; i < genCode.numberColors; i++) {
            if (tempGenCode.get(i).equals("checked")) {
                continue;
            }
            for (int j = 0; j < genCode.numberColors; j++) {

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
