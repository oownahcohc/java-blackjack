package gmbs.controller;

import gmbs.domain.DrawStatus;
import gmbs.domain.game.BlackJackGame;
import gmbs.domain.player.Gamer;
import gmbs.domain.player.name.Names;
import gmbs.view.input.InputConsole;
import gmbs.view.output.OutputConsole;

public class BlackJackGameController {

    private final InputConsole inputConsole;
    private final OutputConsole outputConsole;

    public BlackJackGameController(final InputConsole inputConsole, final OutputConsole outputConsole) {
        this.inputConsole = inputConsole;
        this.outputConsole = outputConsole;
    }

    public void run() {
        outputConsole.printInputGamerNames();
        Names names = Names.from(inputConsole.readNames());
        BlackJackGame blackJackGame = BlackJackGame.create(names);
        outputConsole.printPlayerInitInfo(blackJackGame.getAllPlayerInfo());

        startGamersTurn(blackJackGame);
        startDealerTurn(blackJackGame);

        outputConsole.printPlayerInfoAndTotalScore(blackJackGame.getPlayerInfoAndTotalScore());
        outputConsole.printGameResults(blackJackGame.getAllResults());
    }

    private void startGamersTurn(BlackJackGame blackJackGame) {
        while (!blackJackGame.isAllGamerTurnEnd()) {
            outputConsole.printQuestionForDraw(blackJackGame.getCurrentTurnGamer());
            DrawStatus drawStatus = DrawStatus.from(inputConsole.readPossibilityOfDraw());

            Gamer currentTurnGamer = updateCurrentGamerCardState(blackJackGame, drawStatus);
            outputConsole.printPlayerInfo(currentTurnGamer);
        }
    }

    private Gamer updateCurrentGamerCardState(BlackJackGame blackJackGame, DrawStatus drawStatus) {
        if (drawStatus.isYes()) {
            return blackJackGame.keepTurnAndDraw();
        }
        return blackJackGame.passTurnToNextTurnGamer();
    }

    private void startDealerTurn(BlackJackGame blackJackGame) {
        while (!blackJackGame.isDealerTurnEnd()) {
            blackJackGame.dealerDraw();
            outputConsole.printDealerInfo();
        }
        blackJackGame.finishDealerTurn();
    }
}
