package gmbs;

import gmbs.controller.BlackJackGameController;
import gmbs.view.input.InputConsole;
import gmbs.view.input.InputConsoleImpl;
import gmbs.view.output.OutputConsole;
import gmbs.view.output.OutputConsoleImpl;

public class Main {

    public static void main(String[] args) {
        InputConsole inputConsole = new InputConsoleImpl();
        OutputConsole outputConsole = new OutputConsoleImpl();
        BlackJackGameController blackJackGameController = new BlackJackGameController(inputConsole, outputConsole);
        blackJackGameController.run();
    }
}