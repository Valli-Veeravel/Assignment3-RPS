package rps.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import rps.Move;
import rps.RoundResult;
import rps.model.RoundSnapshot;
import rps.model.RpsGameModel;

public class RpsGameController {
    private static final int DEFAULT_ROUNDS = 20;

    private final RpsGameModel model = new RpsGameModel(DEFAULT_ROUNDS);

    @FXML private Label roundLabel;
    @FXML private Label humanChoiceValue;
    @FXML private Label predictedHumanValue;
    @FXML private Label computerChoiceValue;
    @FXML private Label winnerValue;
    @FXML private Label humanWinsValue;
    @FXML private Label computerWinsValue;
    @FXML private Label tiesValue;

    @FXML private Spinner<Integer> roundsSpinner;

    @FXML private Button rockButton;
    @FXML private Button paperButton;
    @FXML private Button scissorsButton;

    @FXML
    private void initialize() {
        roundsSpinner.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 200, DEFAULT_ROUNDS)
        );
        roundsSpinner.setEditable(true);
        renderInitial();
    }

    @FXML private void onRock() { play(Move.ROCK); }
    @FXML private void onPaper() { play(Move.PAPER); }
    @FXML private void onScissors() { play(Move.SCISSORS); }

    @FXML
    private void onStartNewGame() {
        model.startNewGame(roundsSpinner.getValue());
        renderInitial();
    }

    @FXML
    private void onExit() {
        model.saveAndClose();
        Platform.exit();
    }

    public void onWindowClose() {
        model.saveAndClose();
    }

    private void play(Move humanMove) {
        if (model.isGameOver()) return;

        RoundSnapshot snapshot = model.playRound(humanMove);

        humanChoiceValue.setText(snapshot.humanMove().displayName());
        predictedHumanValue.setText(snapshot.predictedHumanMove().displayName());
        computerChoiceValue.setText(snapshot.computerMove().displayName());
        winnerValue.setText(winnerText(snapshot.outcome()));

        humanWinsValue.setText(String.valueOf(snapshot.humanWins()));
        computerWinsValue.setText(String.valueOf(snapshot.computerWins()));
        tiesValue.setText(String.valueOf(snapshot.ties()));

        refreshRoundAndButtons();
    }

    private String winnerText(RoundResult.Outcome outcome) {
        return switch (outcome) {
            case HUMAN_WIN -> "Human";
            case COMPUTER_WIN -> "Computer";
            case DRAW -> "Tie";
        };
    }

    private void renderInitial() {
        humanChoiceValue.setText("-");
        predictedHumanValue.setText("-");
        computerChoiceValue.setText("-");
        winnerValue.setText("-");

        humanWinsValue.setText("0");
        computerWinsValue.setText("0");
        tiesValue.setText("0");

        refreshRoundAndButtons();
    }

    private void refreshRoundAndButtons() {
        if (model.isGameOver()) {
            roundLabel.setText("Round: " + model.getTotalRounds() + " (Game Over)");
        } else {
            roundLabel.setText("Round: " + model.getCurrentRound());
        }

        boolean gameOver = model.isGameOver();
        rockButton.setDisable(gameOver);
        paperButton.setDisable(gameOver);
        scissorsButton.setDisable(gameOver);
    }
}
