package rps;

import java.util.concurrent.ThreadLocalRandom;

public class RandomComputerStrategy implements ComputerStrategy {

    @Override
    public Move chooseMove(int roundNumber) {
        int n = ThreadLocalRandom.current().nextInt(1, 4); // 1, 2, or 3
        return Move.fromMenuChoice(n);
    }
}