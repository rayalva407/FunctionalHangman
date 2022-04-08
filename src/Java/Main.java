/*
Limitations:

-No loops are allowed except for the main game loop, which is a single while loop.
 All looping must be subsumed through map, filter, and reduce.

-The art for hangman should be read from a file, this art connotates the state of the game.

-The user should be able to enter their name, where their name and score will be recorded to a file.

 -The game will tell the user if they have the high score or not.

 -No exceptions allowed, all exceptions must be caught by the program.

 Add unit testing to the project1
 */

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Main {
    public static ArrayList<String> gameState = new ArrayList<>();
    public static ArrayList<String> gameWords = new ArrayList<>();
    public static ArrayList<String> scoreBoard = new ArrayList<>();

    public static void main(String[] args) {
        importData();


    }


    /*This function will import all the needed from the resources
      (game state, word dictionary, and scoreboard) */
    private static void importData() {
        try {
            gameState = (ArrayList<String>) Files.lines(Path.of("state.txt")).collect(Collectors.toList());
            gameWords = (ArrayList<String>) Files.lines(Path.of("words.txt")).collect(Collectors.toList());
            scoreBoard = (ArrayList<String>) Files.lines(Path.of("scoreboard.txt")).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
