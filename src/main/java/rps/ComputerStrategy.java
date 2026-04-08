package rps;

public interface ComputerStrategy {
    Move chooseMove(int roundNumber);

    default void observeRound(Move humanMove, Move computerMove) {
        // Default no-op for strategies that do not learn.
    }

    default void onGameOver() {
        // Default no-op for strategies without persistent state.
    }
}
