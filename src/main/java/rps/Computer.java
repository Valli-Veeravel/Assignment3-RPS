package rps;

public class Computer implements Player {
    private final String name;
    private final ComputerStrategy strategy;

    public Computer(String name, ComputerStrategy strategy) {
        this.name = name;
        this.strategy = strategy;
    }

    @Override
    public Move chooseMove(int roundNumber) {
        return strategy.chooseMove(roundNumber);
    }
     public void observeRound(Move humanMove, Move computerMove) {
        strategy.observeRound(humanMove, computerMove);
    }

    public void onGameOver() {
        strategy.onGameOver();
    }

    @Override
    public String name() {
        return name;
    }
}
