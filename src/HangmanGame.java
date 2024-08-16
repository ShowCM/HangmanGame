import java.util.*;

public class HangmanGame {
    Scanner scan = new Scanner(System.in);
    Random random = new Random();

    private static final int MAX_MISTAKES = 6;
    private Map<String, String> wordMap = new LinkedHashMap<>();
    private String currentWord;
    private String currentWordTheme;
    private String[] currentWordArray;
    private String[] outputWord;
    int mistakes = 0;

    public HangmanGame() {
        initializeWords();
    }

    private void initializeWords() {
        wordMap.put("Фрукты", "Апельсин");
        wordMap.put("Овощи", "Картофель");
        wordMap.put("Животные", "Зебра");
        wordMap.put("Города", "Москва");
        wordMap.put("Профессии", "Доктор");
        wordMap.put("Спорт", "Футбол");
        wordMap.put("Техника", "Компьютер");
        wordMap.put("Музыкальные инструменты", "Гитара");
        wordMap.put("Птицы", "Воробей");
        wordMap.put("Машины", "Камаз");
        wordMap.put("Морепродукты", "Креветка");
        wordMap.put("Космос", "Планета");
        wordMap.put("Химия", "Кислород");
    }

    public void resetGame() {
        mistakes = 0;
    }

    /**
     * Выбор случайного слова из Map<String, String> wordMap
     */
    public void selectWord() {
        mistakes = 0;
        int randomWordIndex = random.nextInt(0, wordMap.size());
        int counter = 0;

        for (Map.Entry<String, String> item : wordMap.entrySet()) {
            if(randomWordIndex == counter) {
                currentWordTheme = item.getKey(); // Записываем тему слова
                currentWord = item.getValue(); //Записываем слово
                break;
            }
            counter++;
        }

        currentWordArray = currentWord.split("");
        outputWord = new String[currentWord.length()];
        Arrays.fill(outputWord, "__ ");
    }

    public void play() {
        while (mistakes < MAX_MISTAKES) {
            miss(mistakes);
            printGameInfo();
            String guess = getUserInput();
            if (processGuess(guess)) {
                if (isWordGuessed()) {
                    printWinMessage();
                    return;
                }
            } else {
                mistakes++;
                if (mistakes == MAX_MISTAKES) {
                    miss(mistakes);  // Отрисовываем последнюю стадию
                    printLossMessage();
                    return;
                }
            }
        }
    }


    private void printGameInfo() {
        System.out.println("\nСлово: " + String.join(" ", outputWord));
        System.out.println("Тема слова: " + currentWordTheme);
        System.out.println("Ошибки: " + mistakes);
    }

    private String getUserInput() {
        System.out.print("\nВведите букву или слово: ");
        return scan.nextLine();
    }

    private boolean processGuess(String guess) {
        if (guess.length() == currentWord.length()) {
            return checkFullWordGuess(guess);
        } else if (guess.length() == 1) {
            return checkSingleLetterGuess(guess.charAt(0));
        } else {
            System.out.println("Некорректный ввод.");
            return false;
        }
    }

    private boolean checkFullWordGuess(String guess) {
        if (currentWord.equalsIgnoreCase(guess)) {
            outputWord = currentWordArray;
            return true;
        } else {
            System.out.println("Неверное слово.");
            return false;
        }
    }

    private boolean checkSingleLetterGuess(char letter) {
        boolean isCorrect = false;
        for (int i = 0; i < currentWordArray.length; i++) {
            if (currentWordArray[i].equalsIgnoreCase(String.valueOf(letter))) {
                outputWord[i] = String.valueOf(letter);
                isCorrect = true;
            }
        }
        if (!isCorrect) {
            System.out.println("Неверная буква.");
        }
        return isCorrect;
    }

    private boolean isWordGuessed() {
        return Arrays.equals(outputWord, currentWordArray);
    }

    private void printWinMessage() {
        System.out.println();
        System.out.println("Победа! Вы угадали слово: " + currentWord);
        DefaultCommands.enter();
    }

    private void printLossMessage() {
        System.out.println();
        System.out.println("Поражение. Вы не угадали слово: " + currentWord);
        DefaultCommands.enter();
    }

    public static void miss(int mistakes) {
        switch (mistakes) {
            case 0 -> DefaultCommands.zeroMiss();
            case 1 -> DefaultCommands.oneMiss();
            case 2 -> DefaultCommands.twoMiss();
            case 3 -> DefaultCommands.threeMiss();
            case 4 -> DefaultCommands.fourMiss();
            case 5 -> DefaultCommands.fiveMiss();
            case 6 -> DefaultCommands.sixMiss();
        }
    }
}
