/*
Limitations:

-No loops are allowed except for the main game loop, which is a single while loop.
 All looping must be subsumed through map, filter, and reduce.

-The user should be able to enter their name, where their name and score will be recorded to a file.

 -The game will tell the user if they have the high score or not.

 -No exceptions allowed, all exceptions must be caught by the program.

 Add unit testing to the project1
 */

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        importData();
        System.out.println(scoreBoard);
        titleScreen();
        getUserName();
        getSecretWord();
        //game loop
        while (playing) {
            updateGameState();
            updateWrongGuesses();
            updateWordState();
            System.out.println("Score: " + score);
            takeGuess();
            checkGuess();
            if (winner()) {
                score+=50;
                updateGameState();
                updateWordState();
                System.out.println("Score: " + score);
                System.out.println("You win!");
                playAgain();
            }
            if (loser()) {
                updateGameState();
                updateWordState();
                System.out.println("Score: " + score);
                System.out.println("You Lose!");
                playAgain();
            }
        }



    }

    public static ArrayList<String> gameState = new ArrayList<>();
    public static ArrayList<String> gameWords = new ArrayList<>();
    public static ArrayList<String> scoreBoard = new ArrayList<>();
    public static HashSet<String> incorrectList = new HashSet<>();
    public static HashSet<String> correctList = new HashSet<>();
    public static Scanner scanner = new Scanner(System.in);
    public static String userName;
    public static int score = 0;
    public static int currentState = 0;
    public static String secretWord;
    public static String guess = "";
    public static boolean playing = true;



    /*This function will import all the needed from the resources
      (game state, word dictionary, and scoreboard) */
    private static void importData() {
        try {
            gameState = (ArrayList<String>) Files.readAllLines(Path.of("src/Resources/state.txt"));
            gameWords = (ArrayList<String>) Files.lines(Path.of("src/Resources/words.txt")).collect(Collectors.toList());
            scoreBoard = (ArrayList<String>) Files.lines(Path.of("src/Resources/scoreboard.txt")).collect(Collectors.toList());
        }
        catch (IOException e) {
            System.out.println("File not found");
        }
    }

    //Displays the title screen and asks for the user's name
    private static void titleScreen() {
        System.out.println("******************");
        System.out.println("*     Hangman    *");
        System.out.println("******************");
        System.out.println();
        System.out.println("Please enter your name:");
    }

    //Gets the user's name
    private static String getUserName() {
        userName = scanner.nextLine();
        System.out.println("Hello " + userName + "!");
        return userName;
    }

    //Chooses a secret word from the words list
    private static String getSecretWord() {
        secretWord = gameWords.get((int) (Math.random() * (gameWords.size() + 1)));
        return secretWord;
    }

    //prints the state of the hangman game
    private static void updateGameState() {
        Arrays.stream(gameState.get(currentState).split(",")).forEach(System.out::println);
    }

    //prints the state of the secret word
    private static void updateWordState() {
        Arrays.stream(secretWord.split("")).forEach(ch -> {
            if (correctList.contains(ch)) {
                System.out.print(ch);
            }
            else {
                System.out.print("_");
            }
        });
        System.out.println();
    }

    //Takes a guess from user
    private static String takeGuess() {
        System.out.println("Please make a guess:");
        try {
            guess = String.valueOf(scanner.nextLine().charAt(0));
        }
        catch (Exception e) {
            System.out.println("Single character only. No empty space allowed");
            takeGuess();
        }
        return guess;
    }

    //Checks the guess and adds to correct or incorrect list
    private static boolean checkGuess() {
        if (secretWord.contains(guess)) {
            correctList.add(guess);
            score += 10;
            return true;
        }
        else {
            incorrectList.add(guess);
            currentState++;
            return false;
        }
    }

    //Updates the list of wrong guesses
    private static void updateWrongGuesses() {
        System.out.print("Incorrect guesses: ");
        incorrectList.stream().forEach(ch -> System.out.print(ch + " "));
        System.out.println();
    }

    //Checks to see if the player won
    private static boolean winner() {
        int correctGuesses = (int) Arrays.stream(secretWord.split("")).filter(ch -> correctList.contains(ch)).count();
        return correctGuesses == secretWord.length();
    }


    private static boolean loser() {
        return currentState == 7;
    }

    private static void playAgain() {
        System.out.println("Would you like to play again?");
        if (scanner.nextLine().equals("y")) {
            correctList.clear();
            incorrectList.clear();
            if (loser()) score = 0;
            currentState = 0;
            getSecretWord();
        }
        else {
            if (checkHighScore()) {
                System.out.println("Congratulations you got the high score!");
            }
            writeScoreToFile();
            System.out.println("Thank you for playing! Goodbye!");
            playing = false;
        }
    }

    private static void writeScoreToFile() {
        String entry;
        try {
            Path path = Path.of("src/Resources/scoreboard.txt");
            if (Files.size(path) == 0) {
                entry = String.format("%s:%s", userName, score);
            }
            else {
                entry = String.format("%s%s:%s", System.lineSeparator(), userName, score);
            }
            Files.write(path, entry.getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
        }
        catch (Exception e) {
            System.out.println("File not found");
        }
    }

    private static boolean checkHighScore() {
        if (scoreBoard.size() == 0) return true;

        List<Integer> scoreList = scoreBoard.stream()
                .map(str -> str.split(":"))
                .map(arr -> Integer.parseInt(arr[1]))
                .collect(Collectors.toList());
        int maxNumber = scoreList.stream()
                .max(Integer::compareTo)
                .get();
        return score > maxNumber;
    }


}

