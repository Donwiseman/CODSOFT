package numberGame.src;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("WELCOME TO NUMBER GUESSING GAME");
        String instructions = """
                INSTRUCTIONS:
                - For each round, a random whole number would be chosen from 1 and 100(inclusive) by the computer.
                - A player has 5 attempts to guess the correct number.
                - After each wrong guess, yu would be given a hint if your guess is less or greater than the number.
                - If a player fails to guess the number in 5 attempts, the round is lost.
                - Final score is calculated based on rounds won.
                - Press enter to start
                - Type 'quit' to end game.
                """;
        System.out.println(instructions);
        Scanner scanner = new Scanner(System.in);
        int round = 1;
        int totalRound = 0;
        int won = 0;
        int guesses;
        int chosenNumber;
        int playerGuess;

        while (true) {
            boolean roundWon = false;
            System.out.printf("Press Enter to start round %d or type 'quit' to end game %n", round);
            String resp = scanner.nextLine();
            if (resp.equalsIgnoreCase("quit")) {
                int score = getScore(totalRound, won);
                String summary = """
                        GAME RESULTS:
                        Total rounds played: %d
                        Total rounds won: %d
                        Score: %d
                        Remarks: %s""".formatted(totalRound, won, score, getRemarks(score));
                System.out.println(summary);
                break;
            }
            System.out.println("A whole number has been chosen from 1 and 100");
            guesses = 5;
            chosenNumber = getNumber();
            while (guesses > 0) {
                System.out.printf("You have %d attempts %n", guesses);
                playerGuess = readPlayerGuess();
                if (playerGuess == chosenNumber) {
                    won++;
                    roundWon = true;
                    guesses--;
                    System.out.println("Congratulations! You guessed right!");
                    break;
                } else if(playerGuess < chosenNumber) {
                    System.out.println("Wrong Guess! Your guess is lesser than the number");
                } else {
                    System.out.println("Wrong Guess! Your guess is higher than the number");
                }
                guesses--;
            }
            totalRound++;
            String roundSummary = """
                    ROUND SUMMARY
                    Round %d: %s
                    Attempts: %d
                    Chosen Number: %d""".formatted(round, (roundWon ? "Won" : "Lost"), (5 - guesses), chosenNumber);
            System.out.println(roundSummary);
            round++;
        }
    }

    public static int getNumber() {
        Random rand = new Random();
        int num = rand.nextInt(101);
        return num == 0 ? rand.nextInt(101) : num;
    }

    public static int getScore(int totalRounds, int won) {
        if (totalRounds == 0) return 0;
        double score = ((double) won / totalRounds) * 100;
        return (int) Math.round(score);
    }

    public static String getRemarks(int score) {
        if (score > 74) {
            return "Legendary!!";
        } else if (score > 49) {
            return "Good attempt";
        } else if (score > 24) {
            return "Keep trying!";
        }
        return "Better luck next time!";
    }

    public static int readPlayerGuess() {
        /**
         * Reads player guess and return it as int
         */
        int guess;
        while(true) {
            try {
                System.out.print("Make your guess: ");
                Scanner scanner = new Scanner(System.in);
                guess = scanner.nextInt();
                scanner.nextLine();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Guess must be a number");
            }
        }
        return guess;
    }
}
