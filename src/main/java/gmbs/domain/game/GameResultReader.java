package gmbs.domain.game;

import gmbs.domain.Result;
import gmbs.domain.card.state.finish.Finish;
import gmbs.domain.dto.GameResultDto;
import gmbs.domain.game.vo.Profit;
import gmbs.domain.participants.vo.Name;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GameResultReader {

    private final List<Name> winPlayers = new ArrayList<>();
    private final List<Name> tiePlayers = new ArrayList<>();
    private final List<Name> losePlayers = new ArrayList<>();
    private final List<Name> blackJackPlayers = new ArrayList<>();

    private final Finish dealerFinal;
    private final Map<Name, Finish> playersFinal;
    private final ProfitCalculator profitCalculator;

    private GameResultReader(Finish dealerFinal, Map<Name, Finish> playersFinal, ProfitCalculator profitCalculator) {
        this.dealerFinal = dealerFinal;
        this.playersFinal = playersFinal;
        this.profitCalculator = profitCalculator;
        setPlayerResultCompareWithDealer();
    }

    private void setPlayerResultCompareWithDealer() {
        for (Map.Entry<Name, Finish> nameFinish : playersFinal.entrySet()) {
            Name name = nameFinish.getKey();
            Finish playerFinal = nameFinish.getValue();
            Result playerResult = playerFinal.produceResult(dealerFinal);
            if (playerResult.isWin()) {
                this.winPlayers.add(name);
            } else if (playerResult.isTie()) {
                this.tiePlayers.add(name);
            } else if (playerResult.isLose()) {
                this.losePlayers.add(name);
            } else {
                this.blackJackPlayers.add(name);
            }
        }
    }

    public void calculateFinalProfit() {
        if (dealerFinal.isBlackJack()) {
            tiePlayers.forEach(profitCalculator::calculateTie);
            losePlayers.forEach(profitCalculator::calculateLose);
        }
        winPlayers.forEach(profitCalculator::calculateWin);
        tiePlayers.forEach(profitCalculator::calculateTie);
        losePlayers.forEach(profitCalculator::calculateLose);
        blackJackPlayers.forEach(profitCalculator::calculateBlackJack);
    }

    public Map<Name, Profit> readFinalGameResult() {
        return profitCalculator.getFinalProfit();
    }

    public static GameResultReader create(GameResultDto gameResultDto, ProfitCalculator profitCalculator) {
        return new GameResultReader(gameResultDto.getDealerFinal(), gameResultDto.getPlayersFinal(), profitCalculator);
    }
}
