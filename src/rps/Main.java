package rps;

import java.util.Scanner;

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

        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose the computer strategy before the game starts:");
        System.out.println("Enter r for random strategy or m for machine learning strategy.");
        while (true) {
           
            String input = scanner.nextLine().trim().toLowerCase();

            if ("r".equals(input)) {
                return new RandomComputerStrategy();
            }
            if ("m".equals(input)) {
                return new MachineLearningComputerStrategy(5, "rps-ml-data.txt");
            }

            System.out.println("Invalid choice. Please enter r or m.");
            
        }
        
    }
}
