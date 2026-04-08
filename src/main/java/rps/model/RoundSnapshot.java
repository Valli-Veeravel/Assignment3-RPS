package rps.model;

import rps.Move;
import rps.RoundResult;

public record RoundSnapshot(
        int roundPlayed,
        int totalRounds,
        Move humanMove,
        Move predictedHumanMove,
        Move computerMove,
        RoundResult.Outcome outcome,
        int humanWins,
        int computerWins,
        int ties,
        boolean gameOver
) {}
