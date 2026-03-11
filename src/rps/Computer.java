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

    @Override
    public String name() {
        return name;
    }
}