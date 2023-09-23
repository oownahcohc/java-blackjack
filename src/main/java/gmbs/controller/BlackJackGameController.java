package gmbs.controller;

import gmbs.domain.DrawStatus;
import gmbs.domain.card.vo.Card;
import gmbs.domain.card.vo.CardDeck;
import gmbs.domain.game.BlackJackGame;
import gmbs.domain.game.GameResultReader;
import gmbs.domain.game.ProfitCalculator;
import gmbs.domain.game.ProfitCalculatorImpl;
import gmbs.domain.game.vo.Profit;
import gmbs.domain.participants.Dealer;
import gmbs.domain.participants.Player;
import gmbs.domain.participants.Players;
import gmbs.domain.participants.vo.Name;
import gmbs.domain.participants.vo.Names;
import gmbs.view.input.InputConsole;
import gmbs.view.output.OutputConsole;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BlackJackGameController {

    private final InputConsole inputConsole;
    private final OutputConsole outputConsole;

    public BlackJackGameController(InputConsole inputConsole, OutputConsole outputConsole) {
        this.inputConsole = inputConsole;
        this.outputConsole = outputConsole;
    }

    public void run() {
        Names names = readBlackJackGamePlayerNames();
        CardDeck cardDeck = CardDeck.shuffle(Card.generateCards());
        Dealer dealer = Dealer.create(cardDeck.initialDraw());
        List<Player> players = names.getValues()
                .stream()
                .map(name -> Player.create(name, cardDeck.initialDraw()))
                .collect(Collectors.toList());
        Players allPlayer = new Players(players);

        BlackJackGame blackJackGame = new BlackJackGame(cardDeck, dealer, allPlayer);
        ProfitCalculator profitCalculator = setProfitCalculatorByPlayerNameWithBettingAmount(players);
        outputConsole.printPlayerInitInfo(blackJackGame.getAllPlayerInfo());

        startGamersTurn(blackJackGame);
        startDealerTurn(blackJackGame);

        GameResultReader gameResultReader = produceGameResultReader(blackJackGame, profitCalculator);
        outputConsole.printPlayerInfoAndTotalScore(blackJackGame.getPlayerInfoAndTotalScore());
        outputConsole.printGameResults(gameResultReader.readFinalGameResult());
    }

    private Names readBlackJackGamePlayerNames() {
        outputConsole.printInputGamerNames();
        return Names.from(inputConsole.readNames());
    }

    private ProfitCalculator setProfitCalculatorByPlayerNameWithBettingAmount(List<Player> players) {
        Map<Name, Profit> initialNameAndProfit = new HashMap<>();
        for (Player player : players) {
            outputConsole.printInputBettingAmount(player);
            initialNameAndProfit.put(
                    player.notifyName(),
                    Profit.initByBettingAmount(Double.parseDouble(inputConsole.readBettingAmount()))
            );
        }
        return new ProfitCalculatorImpl(initialNameAndProfit);
    }

    private void startGamersTurn(BlackJackGame blackJackGame) {
        while (!blackJackGame.isAllPlayersTurnEnd()) {
            outputConsole.printQuestionForDraw(blackJackGame.showCurrentPlayer());
            DrawStatus drawStatus = DrawStatus.from(inputConsole.readPossibilityOfDraw());

            Player currentTurnGamer = updateCurrentGamerCardState(blackJackGame, drawStatus);
            outputConsole.printPlayerInfo(currentTurnGamer);
        }
    }

    private Player updateCurrentGamerCardState(BlackJackGame blackJackGame, DrawStatus drawStatus) {
        if (drawStatus.isYes()) {
            return blackJackGame.keepTurnAndDraw();
        }
        return blackJackGame.finishTurnAndPassToNextPlayer();
    }

    private void startDealerTurn(BlackJackGame blackJackGame) {
        while (!blackJackGame.isDealerTurnEnd()) {
            blackJackGame.dealerDraw();
            outputConsole.printDealerInfo();
        }
        blackJackGame.finishDealerTurn();
    }

    private GameResultReader produceGameResultReader(BlackJackGame blackJackGame, ProfitCalculator profitCalculator) {
        GameResultReader gameResultReader = GameResultReader.create(blackJackGame.showGameResult(), profitCalculator);
        gameResultReader.calculateFinalProfit();
        return gameResultReader;
    }
}
