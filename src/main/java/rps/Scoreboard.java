package rps;

public class Scoreboard {
    private int humanWins = 0;
    private int computerWins = 0;
    private int draws = 0;

    public void apply(RoundResult result) {
        switch (result.outcome()) {
            case HUMAN_WIN -> humanWins++;
            case COMPUTER_WIN -> computerWins++;
            case DRAW -> draws++;
        }
    }

    public String format() {
        return String.format("Score: Human:%d Computer:%d Draws=%d",
                humanWins, computerWins, draws);
    }

    public String finalSummary() {
        if (humanWins > computerWins) 
            return "Final Result: Human wins the match!";
        if (computerWins > humanWins) 
            return "Final Result: Computer wins the match!";
        return "Final Result: The match is a draw!";
    }
}