import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class User {
    Scanner scan = new Scanner(System.in);

    private Map<String, Integer[]> leaderboard = new LinkedHashMap<>();
    private String currentUsername;

    public User() {
        leaderboard.put("GooGooDolls", new Integer[] {5, 8, 6}); // Тестовое заполнение таблицы лидеров
        leaderboard.put("Bon Jovi", new Integer[] {7, 4, 0});
        leaderboard.put("Journey", new Integer[] {1, 5, 20});
    }

    public void setUsername() {
        System.out.println("Введите Ваш ник");
        String uncheckedName = scan.nextLine();

        if (!uncheckedName.isBlank()) {
            if(!leaderboard.containsKey(uncheckedName)) {
                addNewUser(uncheckedName, new Integer[]{0, 0, 0});
            }
            currentUsername = uncheckedName;
        } else {
            System.out.println("\n\nМожно вводить только символы A-Z, А-Я и цифры 0-9");
            setUsername();
        }
    }

    private void addNewUser(String name, Integer[] score) {
        leaderboard.put(name, score);
    }

    public void updateCurrentUserScore(Integer[] updatedScore) {
        leaderboard.replace(currentUsername, updatedScore);
    }

    public Integer[] getUserScore() {
        return leaderboard.get(currentUsername);
    }

    public void getLeaderboardTable() {
        System.out.println("\n\n\n");
        System.out.println("               Таблица лидеров");

        for (Map.Entry<String, Integer[]> item : leaderboard.entrySet()) {
            String name = item.getKey();
            Integer[] score = item.getValue();

            System.out.println("––––––––––––––––––––––––––––––––––––––––––");
            System.out.println("Угадано слов: " + score[0]);
            if(name.equalsIgnoreCase(currentUsername)) {
                System.out.print("Угадано букв всего: " + score[1] + "         " + name + "           <–-– Это вы");
            } else {
                System.out.print("Угадано букв всего: " + score[1] + "         " + name);
            }
            System.out.println();
            System.out.print("Ошибок: " + score[2]);
            System.out.println();
        }
    }
}
