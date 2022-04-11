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
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        importData();
        titleScreen();
        getUserName();
        getSecretWord();
        System.out.println(secretWord);
        //game loop
        while (playing) {
            printGameState();
            System.out.println("Please take a guess:");
            takeGuess();
//            updateGameState();
        }



    }

    public static ArrayList<String> gameState = new ArrayList<>();
    public static ArrayList<String> gameWords = new ArrayList<>();
    public static ArrayList<String> scoreBoard = new ArrayList<>();
    public static Scanner scanner = new Scanner(System.in);
    public static String userName;
    public static int score = 0;
    public static int currentState = 0;
    public static String secretWord;
    public static String guess;
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
        return userName;
    }

    private static String getSecretWord() {
        secretWord = gameWords.get((int) (Math.random() * (gameWords.size() + 1)));
        return secretWord;
    }

    private static void printGameState() {
        Arrays.stream(gameState.get(currentState).split(",")).forEach(System.out::println);
    }

    private static void takeGuess() {
        guess = scanner.nextLine();
    }
}

