package rps;

public class RoundResult {
    public enum Outcome { HUMAN_WIN, COMPUTER_WIN, DRAW }

    private final Outcome outcome;

    public RoundResult(Outcome outcome) {
        this.outcome = outcome;
    }

    public Outcome outcome() {
        return outcome;
    }

    public String message() {
        return switch (outcome) {
            case HUMAN_WIN -> "Human Wins!";
            case COMPUTER_WIN -> "Computer Wins!";
            case DRAW -> "Draw!";
        };
    }
}