package rps;

public class Main {
    public static void main(String[] args) {
        Rules rules = new StandardRules();
        ChoiceProvider choiceProvider = new ConsoleChoiceProvider();

        Player human = new Human("Human", choiceProvider);
        Player computer = new Computer("Computer", new RandomComputerStrategy());

        Game game = new Game(human, computer, rules, 20);
        game.play();
    }
}
