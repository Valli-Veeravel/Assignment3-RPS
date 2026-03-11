package rps;

import java.util.Scanner;

public class ConsoleChoiceProvider implements ChoiceProvider {

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public Move getHumanMove(int roundNumber) {
        while (true) {
            System.out.printf("Round %d - Choose (1=rock, 2=paper, 3=scissors): ", roundNumber);
            String input = scanner.nextLine().trim();

            try {
                int choice = Integer.parseInt(input);

                switch (choice) {
                    case 1:
                        return Move.ROCK;
                    case 2:
                        return Move.PAPER;
                    case 3:
                        return Move.SCISSORS;
                    default:
                        System.out.println("Invalid choice. Please enter 1, 2, or 3.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter 1, 2, or 3.");
            }
        }
    }
}
