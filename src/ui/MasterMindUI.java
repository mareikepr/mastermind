package ui;

import domain.GameLogic;

import java.util.List;
import java.util.Scanner;
public class MasterMindUI {

    Scanner scan = new Scanner(System.in);

    public MasterMindUI () {

        startGame();
    }

    public void startGame() {

        boolean playGame = true;

        while (playGame) {

            System.out.println(" \n##### Mastermind #####\n");
            System.out.println(" Start game ? ");
            String startGame = scan.nextLine();

            if (startGame.equals("yes")) {
                System.out.println(" The computer picked four colors for you. Guess the colors in the correct order." +
                                    "\n Available colors: red, green, brown, yellow, blue, orange" +
                                    "\n Format: \"red green blue brown\"" +
                                    "\n Evaluation of computer: " +
                                    "\n  Black: Correct color and position, White: Correct color, wrong position)");
                new GameLogic(this);
            } else {
                System.out.println(" No game. ");
                playGame = false;
            }
        }
    }

    public String makeAGuess(int guessCount) {
        System.out.println(" \nMake guess " + guessCount + ": ");
        return scan.nextLine();
    }

    public void printResponse(List<String> blackWhiteStringArray) {
        // get string from game logic class
        System.out.println("Evaluation: " + blackWhiteStringArray);
    }

    public void printEnd(List<String> guessedCode) {
        System.out.println(" You've guessed correctly: " + guessedCode);
    }

    public void printInvalidCodeMessage() {
        System.out.println(" Given code is not valid. Please, enter valid code: ");
    }
}