public class ScoreHandler {

    private Integer[] userScore;
    private final User user;

    public ScoreHandler(User user) {
        this.user = user;
    }

    public void initializeScore() {
        this.userScore = user.getUserScore();
    }

    public void updateCurrentUserScore() {
        user.updateCurrentUserScore(userScore);
    }

    public void incrementWordScore() {
        userScore[0]++;
    }

    public void incrementLetterScore() {
        userScore[1]++;
    }

    public void incrementMistakesScore() {
        userScore[2]++;
    }
}
