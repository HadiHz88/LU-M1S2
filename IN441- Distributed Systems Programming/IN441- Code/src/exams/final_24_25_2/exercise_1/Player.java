package exams.final_24_25_2.exercise_1;

public class Player {
    private final String username;
    private int score;
    private String challengeQuestion;
    private String challengeAnswer;

    Player(String username, String challengeQuestion, String challengeAnswer) {
        this.username = username;
        this.score = 0;
        this.challengeQuestion = challengeQuestion;
        this.challengeAnswer = challengeAnswer;
    }

    String username() {
        return username;
    }

    int score() {
        return score;
    }

    String challengeQuestion() {
        return challengeQuestion;
    }

    String challengeAnswer() {
        return challengeAnswer;
    }

    void setChallenge(String challengeQuestion, String challengeAnswer) {
        this.challengeQuestion = challengeQuestion;
        this.challengeAnswer = challengeAnswer;
    }

    void increaseScore() {
        score++;
    }
}
