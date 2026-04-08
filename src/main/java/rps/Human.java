package rps;

public class Human implements Player {
    private final String name;
    private final ChoiceProvider choiceProvider;

    public Human(String name, ChoiceProvider choiceProvider) {
        this.name = name;
        this.choiceProvider = choiceProvider;
    }

    @Override
    public Move chooseMove(int roundNumber) {
        return choiceProvider.getHumanMove(roundNumber);
    }

    @Override
    public String name() {
        return name;
    }
}