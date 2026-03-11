package rps;

import org.junit.Test;
import static org.junit.Assert.*;

public class RpsTests {

   //STANDARD RULES

    @Test
    public void rockBeatsScissors() {
        Rules rules = new StandardRules();
        assertEquals(RoundResult.Outcome.HUMAN_WIN, rules.decide(Move.ROCK, Move.SCISSORS).outcome());
    }

    @Test
    public void scissorsBeatsPaper() {
        Rules rules = new StandardRules();
        assertEquals(RoundResult.Outcome.HUMAN_WIN, rules.decide(Move.SCISSORS, Move.PAPER).outcome());
    }

    @Test
    public void paperBeatsRock() {
        Rules rules = new StandardRules();
        assertEquals(RoundResult.Outcome.HUMAN_WIN, rules.decide(Move.PAPER, Move.ROCK).outcome());
    }

    @Test
    public void rockLosesToPaper() {
        Rules rules = new StandardRules();
        assertEquals(RoundResult.Outcome.COMPUTER_WIN, rules.decide(Move.ROCK, Move.PAPER).outcome());
    }

    @Test
    public void scissorsLoseToRock() {
        Rules rules = new StandardRules();
        assertEquals(RoundResult.Outcome.COMPUTER_WIN, rules.decide(Move.SCISSORS, Move.ROCK).outcome());
    }

    @Test
    public void paperLosesToScissors() {
        Rules rules = new StandardRules();
        assertEquals(RoundResult.Outcome.COMPUTER_WIN, rules.decide(Move.PAPER, Move.SCISSORS).outcome());
    }

    @Test
    public void rockVsRockIsDraw() {
        Rules rules = new StandardRules();
        assertEquals(RoundResult.Outcome.DRAW, rules.decide(Move.ROCK, Move.ROCK).outcome());
    }

    @Test
    public void paperVsPaperIsDraw() {
        Rules rules = new StandardRules();
        assertEquals(RoundResult.Outcome.DRAW, rules.decide(Move.PAPER, Move.PAPER).outcome());
    }

    @Test
    public void scissorsVsScissorsIsDraw() {
        Rules rules = new StandardRules();
        assertEquals(RoundResult.Outcome.DRAW, rules.decide(Move.SCISSORS, Move.SCISSORS).outcome());
    }

    //SCOREBOARD TESTS

    @Test
    public void scoreboardTracksHumanWin() {
        Scoreboard sb = new Scoreboard();
        sb.apply(new RoundResult(RoundResult.Outcome.HUMAN_WIN));
        assertTrue(sb.format().contains("Human:1"));
    }

    @Test
    public void scoreboardTracksComputerWin() {
        Scoreboard sb = new Scoreboard();
        sb.apply(new RoundResult(RoundResult.Outcome.COMPUTER_WIN));
        assertTrue(sb.format().contains("Computer:1"));
    }

    @Test
    public void scoreboardTracksDraw() {
        Scoreboard sb = new Scoreboard();
        sb.apply(new RoundResult(RoundResult.Outcome.DRAW));
        assertTrue(sb.format().contains("Draws=1"));
    }

    @Test
    public void finalSummaryHumanWins() {
        Scoreboard sb = new Scoreboard();
        sb.apply(new RoundResult(RoundResult.Outcome.HUMAN_WIN));
        assertTrue(sb.finalSummary().contains("Human wins"));
    }

    @Test
    public void finalSummaryComputerWins() {
        Scoreboard sb = new Scoreboard();
        sb.apply(new RoundResult(RoundResult.Outcome.COMPUTER_WIN));
        assertTrue(sb.finalSummary().contains("Computer wins"));
    }

    @Test
    public void finalSummaryDraw() {
        Scoreboard sb = new Scoreboard();
        sb.apply(new RoundResult(RoundResult.Outcome.HUMAN_WIN));
        sb.apply(new RoundResult(RoundResult.Outcome.COMPUTER_WIN));
        assertTrue(sb.finalSummary().contains("draw"));
    }

    //MENU CHOICE

    @Test
    public void fromMenuChoice1ReturnsRock() {
        assertEquals(Move.ROCK, Move.fromMenuChoice(1));
    }

    @Test
    public void fromMenuChoice2ReturnsPaper() {
        assertEquals(Move.PAPER, Move.fromMenuChoice(2));
    }

    @Test
    public void fromMenuChoice3ReturnsScissors() {
        assertEquals(Move.SCISSORS, Move.fromMenuChoice(3));
    }

    @Test(expected = IllegalArgumentException.class)
    public void fromMenuChoiceInvalidThrows() {
        Move.fromMenuChoice(99);
    }

    //HUMAN TESTS

    @Test
    public void humanReturnsWhateverProviderGives() {
        ChoiceProvider stub = roundNumber -> Move.PAPER;
        Human human = new Human("Tester", stub);
        assertEquals(Move.PAPER, human.chooseMove(1));
    }

    @Test
    public void humanNameIsCorrect() {
        Human human = new Human("Alice", roundNumber -> Move.ROCK);
        assertEquals("Alice", human.name());
    }

    //COMPUTER TESTS

    @Test
    public void computerDelegatesToStrategy() {
        ComputerStrategy fixedRock = roundNumber -> Move.ROCK;
        Computer computer = new Computer("Bot", fixedRock);
        assertEquals(Move.ROCK, computer.chooseMove(1));
    }

    @Test
    public void computerNameIsCorrect() {
        Computer computer = new Computer("HAL", roundNumber -> Move.SCISSORS);
        assertEquals("HAL", computer.name());
    }
}