package rps;

public enum Move {
    ROCK("Rock"),
    PAPER("Paper"),
    SCISSORS("Scissors");

    private final String displayName;

    Move(String displayName) {
        this.displayName = displayName;
    }

    public String displayName() {
        return displayName;
    }

    public static Move fromMenuChoice(int choice) {
        return switch (choice) {
            case 1 -> ROCK;
            case 2 -> PAPER;
            case 3 -> SCISSORS;
            default -> throw new IllegalArgumentException("Choice must be 1, 2, or 3.");
        };
    }
}