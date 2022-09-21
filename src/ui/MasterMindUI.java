package ui;

import domain.GameLogic;

import java.time.*;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class MasterMindUI {

    private final Scanner scan = new Scanner(System.in);
    private LocalTime startTime;
    public String mode = "";

    public MasterMindUI (String[] args) {

        if (args.length==1) {
            mode = args[0];
        }
        startGame();

    }

    public void startGame() {

        boolean playGame = true;

        while (playGame) {

            LocalDateTime now = LocalDateTime.now();

            System.out.println(" \n" + now.getDayOfWeek() + ", "+  now.getDayOfMonth() +" "+now.getMonth()) ;
            System.out.println(" \n##### Mastermind #####\n");
            System.out.println(" Start game ? ");
            String startGame = scan.nextLine();

            if (startGame.contains("yes")) {
                startTime = LocalTime.now();
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

    public Optional<String> makeAGuess(int guessCount) {

        System.out.println(" \nMake guess " + guessCount + ": ");

        Optional<String> input = Optional.of(scan.nextLine());

        if (!input.get().equals("exit")) {
            return input;
        } else {
            return Optional.empty();
        }
    }

    public void printResponse(List<String> blackWhiteStringArray) {
        // get string from game logic class
        System.out.println("Evaluation: " + blackWhiteStringArray);
    }

    public void printInvalidCodeMessage() {
        System.out.println(" Given code is not valid. Please, enter valid code: ");
    }

    public void printEnd(List<String> guessedCode, int guessCount) {

        System.out.println(" You've guessed correctly: " + guessedCode);
        System.out.println(" It took you " + guessTime()
                + " minutes and " + guessCount + " guesses to find the code.");
    }

    public void printBreak(List<String> genCode, int guessCount) {
        System.out.println(" The code was " + genCode
                + "\nIt took you " + guessTime()
                + " minutes and " + (guessCount-1) + " guesses to not find the code."
                + "\n Exit game.");
    }

    private double guessTime() {
        return Duration.between(startTime, LocalTime.now()).toSeconds()/60.0;
    }
}