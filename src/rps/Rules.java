package rps;

public interface Rules {
    RoundResult decide(Move humanMove, Move computerMove);
}
