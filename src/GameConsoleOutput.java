import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class GameConsoleOutput {

    public void printGameInfo(String[] outputWord, String currentWordTheme,
                              int mistakes, Set<String> usedLetters) {
        System.out.println("\nСлово: " + String.join(" ", outputWord));
        System.out.println("Тема слова: " + currentWordTheme);
        System.out.println("Ошибки: " + mistakes);
        printUsedLetters(usedLetters);
    }

    private void printUsedLetters(Set<String> usedLetters) {
        List<String> usedLettersSort = new ArrayList<>(usedLetters);
        Collections.sort(usedLettersSort);

        System.out.print("Использованные буквы: " + usedLettersSort);
        System.out.println();
    }

    public void printWinMessage(String currentWord) {
        System.out.println("\n\n\n\n\n");
        System.out.println("Победа! Вы угадали слово: " + currentWord);
        DefaultCommands.enter();
    }

    public void printLossMessage(String currentWord) {
        System.out.println();
        System.out.println("Поражение. Вы не угадали слово: " + currentWord);
        DefaultCommands.enter();
    }
}
