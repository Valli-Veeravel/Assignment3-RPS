package rps;

public interface ComputerStrategy {
    Move chooseMove(int roundNumber);
}