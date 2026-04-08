package rps;

public class Game {
    private final Player human;
    private final Computer computer;
    private final Rules rules;
    private final int totalRounds;

    public Game(Player human, Computer computer, Rules rules, int totalRounds) {
        this.human = human;
        this.computer = computer;
        this.rules = rules;
        this.totalRounds = totalRounds;
    }

    public void play() {
        Scoreboard scoreboard = new Scoreboard();

        for (int round = 1; round <= totalRounds; round++) {
            Move computerMove = computer.chooseMove(round);
            Move humanMove = human.chooseMove(round);

            RoundResult result = rules.decide(humanMove, computerMove);
            computer.observeRound(humanMove, computerMove);

            System.out.printf("You chose %s. The computer chose %s. %s%n",
                    humanMove.displayName(),
                    computerMove.displayName(),
                    result.message()
            );

            scoreboard.apply(result);

            System.out.println(scoreboard.format());
            System.out.println("-----------------------------");
        }

        computer.onGameOver();

        System.out.println("Game over!");
        System.out.println(scoreboard.finalSummary());
    }
}
