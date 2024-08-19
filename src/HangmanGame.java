import java.util.*;

public class HangmanGame {

    Scanner scan = new Scanner(System.in);
    Random random = new Random();

    private final User user = new User();
    private final GameConsoleOutput gameConsoleOutput = new GameConsoleOutput();
    private final ScoreHandler scoreHandler = new ScoreHandler(user);

    private static final int MAX_MISTAKES = 6;
    private final Map<String, String> words = new LinkedHashMap<>();
    private String currentWord;
    private String currentWordTheme;
    private String[] currentWordArray;
    private String[] outputWord;
    private final Set<String> usedLetters = new HashSet<>();
    private int mistakes = 0;

    public HangmanGame() {
        user.setUsername();
        scoreHandler.initializeScore();
        initializeWords();
    }

    private void initializeWords() {
        words.put("Фрукты", "Апельсин");
        words.put("Овощи", "Картофель");
        words.put("Животные", "Зебра");
        words.put("Города", "Москва");
        words.put("Профессии", "Доктор");
        words.put("Спорт", "Футбол");
        words.put("Техника", "Компьютер");
        words.put("Музыкальные инструменты", "Гитара");
        words.put("Птицы", "Воробей");
        words.put("Машины", "Камаз");
        words.put("Морепродукты", "Креветка");
        words.put("Космос", "Планета");
        words.put("Химия", "Кислород");
    }

    /**
     * Выбор случайного слова из Map<String, String> wordMap
     */
    public void selectWord() {
        usedLetters.clear();
        mistakes = 0;
        int randomWordIndex = random.nextInt(0, words.size());
        int counter = 0;

        for (Map.Entry<String, String> item : words.entrySet()) {
            if (randomWordIndex == counter) {
                currentWordTheme = item.getKey();
                currentWord = item.getValue();
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
            DefaultCommands.printMistakeStages(mistakes);
            gameConsoleOutput.printGameInfo(outputWord, currentWordTheme, mistakes, usedLetters);

            String guess = getUserInput();
            if (processPlayerGuess(guess)) {
                if (isWordGuessed()) {
                    scoreHandler.incrementWordScore();
                    gameConsoleOutput.printWinMessage(currentWord);
                    scoreHandler.updateCurrentUserScore();
                    return;
                }
            } else {
                mistakes++;
                scoreHandler.incrementMistakesScore();
                if (mistakes == MAX_MISTAKES) {
                    DefaultCommands.printMistakeStages(mistakes);
                    gameConsoleOutput.printLossMessage(currentWord);
                    scoreHandler.updateCurrentUserScore();
                    return;
                }
            }
        }
    }

    public String getUserInput() {
        System.out.print("\nВведите букву или слово: ");

        return scan.nextLine().toLowerCase();
    }

    private boolean processPlayerGuess(String guess) {
        if (guess.length() == currentWord.length()) {
            return checkFullWordGuess(guess);
        } else if (guess.length() == 1) {
            validateUserInput(guess);
            return checkSingleLetterGuess(guess);
        } else {
            validateUserInput(guess);
            return false;
        }
    }

    private void validateUserInput(String guess) {
        if (guess.isBlank() && guess.isEmpty()) {
            System.out.println("\n\nВвод не может быть пустым.");
            play();
        } else if (!guess.matches("[а-яё]")) {
            System.out.println("\n\nМожно вводить только символы А-Я");
            play();
        } else if (usedLetters.contains(guess)) {
            System.out.println("\n\nТакая буква уже была.");
            play();
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

    private boolean checkSingleLetterGuess(String letter) {
        boolean isCorrect = false;
        for (int i = 0; i < currentWordArray.length; i++) {
            if (currentWordArray[i].equalsIgnoreCase(letter)) {
                if (i == 0) {
                    outputWord[i] = letter.toUpperCase(); // toUpper нужно в конце для проверки загаданного и угаданного слова
                } else {
                    outputWord[i] = letter;
                }
                scoreHandler.incrementLetterScore();
                isCorrect = true;
            }
        }

        usedLetters.add(letter);

        if (!isCorrect) {
            System.out.println("Неверная буква.");
        }
        return isCorrect;
    }

    private boolean isWordGuessed() {
        return Arrays.equals(outputWord, currentWordArray);
    }

    public void getLeaderboardTable() {
        user.getLeaderboardTable();
    }
}
