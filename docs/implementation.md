1. install maven
2. move code to main/java/rps
3. create pom.xml
4. run mvn clean 
5. run mvn test 
6. run mcn javafx: run 
7. create MainFx.java
8. create structure 
src/main/java/rps/MainFx.java
src/main/java/rps/model/RpsGameModel.java
src/main/java/rps/model/RoundSnapshot.java
src/main/java/rps/controller/RpsGameController.java
src/main/resources/RpsGameView.fxml

9. rpsgamemodel: playRound (Move humanMove): computer.chooseMove() -> rules.decide() -> computer.observeRound -> update result -> currentRound ++ -> computer.onGameOver()
=> return RoundSnapshot => render ui
Change: use humanMove as argument, because GUI and console different in the way of getting inputs. Game calls human.chooseMove since ChoiceProvider is read from keyboard, Game controls loop rounds
=> GUI gets input => send to controller => playRound (humanMove) => GUI is event-driven => model take care of rules, controller take care of UI event => cleaner

Change loop into this : 
        int roundPlayed = currentRound;
        currentRound++;

        boolean gameOver = isGameOver();
        if (gameOver) {
            computer.onGameOver();
        }

10. add lastPredictedHumanMove and getter in MachineLearningComputerStrategy.java
11. add view in RpsGameView.fxml
12. rpsgamecontroller: map onAction button to controller function (onrock,paper,scissor)
    receive events (onrock/paper/scissor) => call model.playRound() => update label/buttons =>  refreshRoundAndButtons()