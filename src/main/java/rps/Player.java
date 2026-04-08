package rps;

public interface Player {
    Move chooseMove(int roundNumber);
    String name();
}