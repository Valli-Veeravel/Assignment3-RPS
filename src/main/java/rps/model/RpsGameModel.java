package rps.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import rps.Computer;
import rps.ComputerStrategy;
import rps.MachineLearningComputerStrategy;
import rps.Move;
import rps.RoundResult;
import rps.Rules;
import rps.StandardRules;

public class RpsGameModel {
    private static final int SEQUENCE_LENGTH = 5;
    private static final Path DATA_FILE = Path.of("data", "rps-ml-data.txt");

    private final Rules rules = new StandardRules();
    private final Computer computer;

    private int totalRounds;
    private int currentRound = 1;
    private int humanWins = 0;
    private int computerWins = 0;
    private int ties = 0;

    public RpsGameModel(int totalRounds) {
        this.totalRounds = totalRounds;
        ensureDataFolder();
        ComputerStrategy strategy =
                new MachineLearningComputerStrategy(SEQUENCE_LENGTH, DATA_FILE.toString());
        this.computer = new Computer("Computer", strategy);
    }

    public RoundSnapshot playRound(Move humanMove) {
        if (isGameOver()) {
            throw new IllegalStateException("Game is over.");
        }

        
        Move computerMove = computer.chooseMove(currentRound);
        Move predictedHumanMove = inferPredictedHumanMove(computerMove);
        RoundResult result = rules.decide(humanMove, computerMove);
        computer.observeRound(humanMove, computerMove);

        switch (result.outcome()) {
            case HUMAN_WIN -> humanWins++;
            case COMPUTER_WIN -> computerWins++;
            case DRAW -> ties++;
        }

        int roundPlayed = currentRound;
        currentRound++;

        boolean gameOver = isGameOver();
        if (gameOver) {
            computer.onGameOver();
        }

        return new RoundSnapshot(
                roundPlayed, totalRounds, humanMove, predictedHumanMove, computerMove,
                result.outcome(), humanWins, computerWins, ties, gameOver
        );
    }

    public void startNewGame(int newTotalRounds) {
        computer.onGameOver();
        totalRounds = newTotalRounds;
        currentRound = 1;
        humanWins = 0;
        computerWins = 0;
        ties = 0;
    }

    public void saveAndClose() {
        computer.onGameOver();
    }

    public int getCurrentRound() { return currentRound; }
    public int getTotalRounds() { return totalRounds; }
    public boolean isGameOver() { return currentRound > totalRounds; }

    private Move inferPredictedHumanMove(Move computerMove) {
        return switch (computerMove) {
            case ROCK -> Move.SCISSORS;
            case PAPER -> Move.ROCK;
            case SCISSORS -> Move.PAPER;
        };
    }

    private void ensureDataFolder() {
        try {
            Files.createDirectories(DATA_FILE.getParent());
        } catch (IOException e) {
            throw new RuntimeException("Cannot create data folder.", e);
        }
    }
}
