package rps;

public class Main {
    private static final int DEFAULT_ROUNDS = 20;

    public static void main(String[] args) {
        Rules rules = new StandardRules();
        ChoiceProvider choiceProvider = new ConsoleChoiceProvider();
        ComputerStrategy strategy = selectStrategy(args);

        Player human = new Human("Human", choiceProvider);
        Computer computer = new Computer("Computer", strategy);

        Game game = new Game(human, computer, rules, DEFAULT_ROUNDS);
        game.play();
    }

    
    
    private static ComputerStrategy selectStrategy(String[] args) {
        if (args.length == 0) {
            System.out.println("No strategy option supplied. Defaulting to random.");
            return new RandomComputerStrategy(); 
        }

        if (args[0].equals("-m")) {
            System.out.println("Using machine-learning strategy.");
            return new MachineLearningComputerStrategy(5, "rps-ml-data.txt");
        }

        if (args[0].equals("-r")) {
            System.out.println("Using random strategy.");
            return new RandomComputerStrategy();
        }

        // fallback if unknown flag
        System.out.println("Unknown strategy option. Defaulting to random.");
        return new RandomComputerStrategy();
    }
}
