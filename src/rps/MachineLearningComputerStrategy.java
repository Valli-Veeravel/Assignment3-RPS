package rps;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class MachineLearningComputerStrategy implements ComputerStrategy {
    private final int sequenceLength;
    private final Path dataFile;
    private final Map<String, Integer> frequencies;
    private final List<Move> history;

    public MachineLearningComputerStrategy(int sequenceLength, String dataFilename) {
        if (sequenceLength < 2) {
            throw new IllegalArgumentException("sequenceLength must be at least 2.");
        }
        this.sequenceLength = sequenceLength;
        this.dataFile = Path.of(dataFilename);
        this.frequencies = new HashMap<>();
        this.history = new ArrayList<>();
        loadFrequencies();
    }

    @Override
    public Move chooseMove(int roundNumber) {
        Move predictedHumanMove = predictHumanMove();
        if (predictedHumanMove == null) {
            return randomMove();
        }
        return counterMove(predictedHumanMove);
    }

    @Override
    public void observeRound(Move humanMove, Move computerMove) {
        history.add(humanMove);
        updateFrequencyForNewestHumanChoice();
        history.add(computerMove);
    }

    @Override
    public void onGameOver() {
        saveFrequencies();
    }

    private Move predictHumanMove() {
        if (history.size() < sequenceLength - 1) {
            return null;
        }

        String prefix = encode(history.subList(history.size() - (sequenceLength - 1), history.size()));
        Map<Move, Integer> candidateCounts = new EnumMap<>(Move.class);

        int bestCount = 0;
        for (Move move : Move.values()) {
            int count = frequencies.getOrDefault(prefix + encode(move), 0);
            candidateCounts.put(move, count);
            if (count > bestCount) {
                bestCount = count;
            }
        }

        if (bestCount == 0) {
            return null;
        }

        List<Move> bestMoves = new ArrayList<>();
        for (Map.Entry<Move, Integer> entry : candidateCounts.entrySet()) {
            if (entry.getValue() == bestCount) {
                bestMoves.add(entry.getKey());
            }
        }

        int index = ThreadLocalRandom.current().nextInt(bestMoves.size());
        return bestMoves.get(index);
    }

    private void updateFrequencyForNewestHumanChoice() {
        if (history.size() < sequenceLength) {
            return;
        }

        String sequence = encode(history.subList(history.size() - sequenceLength, history.size()));
        frequencies.merge(sequence, 1, Integer::sum);
    }

    private void loadFrequencies() {
        if (!Files.exists(dataFile)) {
            return;
        }

        try (BufferedReader reader = Files.newBufferedReader(dataFile)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String trimmed = line.trim();
                if (trimmed.isEmpty()) {
                    continue;
                }

                String[] parts = trimmed.split("\\s+");
                if (parts.length != 2) {
                    continue;
                }

                frequencies.put(parts[0], Integer.parseInt(parts[1]));
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Warning: unable to load ML data. Starting with empty frequencies.");
            frequencies.clear();
        }
    }

    private void saveFrequencies() {
        try (BufferedWriter writer = Files.newBufferedWriter(dataFile)) {
            for (Map.Entry<String, Integer> entry : frequencies.entrySet()) {
                writer.write(entry.getKey() + " " + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Warning: unable to save ML data.");
        }
    }

    private Move counterMove(Move predictedHumanMove) {
        return switch (predictedHumanMove) {
            case ROCK -> Move.PAPER;
            case PAPER -> Move.SCISSORS;
            case SCISSORS -> Move.ROCK;
        };
    }

    private Move randomMove() {
        return Move.values()[ThreadLocalRandom.current().nextInt(Move.values().length)];
    }

    private String encode(List<Move> moves) {
        StringBuilder builder = new StringBuilder();
        for (Move move : moves) {
            builder.append(encode(move));
        }
        return builder.toString();
    }

    private String encode(Move move) {
        return switch (move) {
            case ROCK -> "R";
            case PAPER -> "P";
            case SCISSORS -> "S";
        };
    }
}
