import java.util.Scanner;

public class Hangman {

    static Scanner scan = new Scanner(System.in);
    static HangmanGame hangmanGame = new HangmanGame();

    public static void main(String[] args) {
        switches();
    }

    public static void switches() {
        DefaultCommands.gameHello();
        System.out.println("[1] - Начать игру");
        System.out.println("[2] - Выйти");

        int option = scan.nextInt();
        if(option == 1) {
            hangmanGame.selectWord();
            hangmanGame.play();
        } else {
            System.exit(1);
        }
    }

}