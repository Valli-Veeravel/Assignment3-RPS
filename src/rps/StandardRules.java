package rps;

public class StandardRules implements Rules {

    @Override
    public RoundResult decide(Move humanMove, Move computerMove) {
        if (humanMove == computerMove) {
            return new RoundResult(RoundResult.Outcome.DRAW);
        }
        if(beats(humanMove, computerMove)) {
            return new RoundResult(RoundResult.Outcome.HUMAN_WIN);
        }
        return new RoundResult(RoundResult.Outcome.COMPUTER_WIN);


    }
    
     //Returns true if 'a' beats 'b' under standard rps rules.
     
    private boolean beats(Move a, Move b) {
        return (a == Move.ROCK && b == Move.SCISSORS) || (a == Move.SCISSORS && b == Move.PAPER)|| (a == Move.PAPER && b == Move.ROCK);
    }

    
}