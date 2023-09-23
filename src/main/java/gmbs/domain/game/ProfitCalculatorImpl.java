package gmbs.domain.game;

import gmbs.domain.game.vo.Profit;
import gmbs.domain.participants.vo.Name;

import java.util.Collections;
import java.util.Map;

public class ProfitCalculatorImpl implements ProfitCalculator {

    private static final Name DEALER_NAME = Name.from("딜러");

    private final Map<Name, Profit> values;

    public ProfitCalculatorImpl(Map<Name, Profit> values) {
        this.values = values;
        setDefaultDealerBettingAmount();
    }

    private void setDefaultDealerBettingAmount() {
        this.values.put(DEALER_NAME, Profit.initByBettingAmount(0));
    }

    @Override
    public Map<Name, Profit> getFinalProfit() {
        return Collections.unmodifiableMap(values);
    }

    @Override
    public void calculateTie(Name playerName) {
        Profit bettingAmount = getPlayerProfit(playerName);
        Profit tieProfit = bettingAmount.calculateTieProfit();
        values.replace(playerName, tieProfit);
    }

    @Override
    public void calculateLose(Name playerName) {
        Profit bettingAmount = getPlayerProfit(playerName);
        Profit playerLoseProfit = bettingAmount.calculateLoseProfit();
        values.replace(playerName, playerLoseProfit);

        Profit dealerWinProfit = bettingAmount.calculateWinProfit();
        resetDealerProfit(dealerWinProfit);
    }

    @Override
    public void calculateWin(Name playerName) {
        Profit bettingAmount = getPlayerProfit(playerName);
        Profit playerWinProfit = bettingAmount.calculateWinProfit();
        values.replace(playerName, playerWinProfit);

        Profit dealerLoseProfit = bettingAmount.calculateLoseProfit();
        resetDealerProfit(dealerLoseProfit);
    }

    @Override
    public void calculateBlackJack(Name playerName) {
        Profit bettingAmount = getPlayerProfit(playerName);
        Profit playerBlackJackProfit = bettingAmount.calculateBlackJackProfit();
        values.replace(playerName, playerBlackJackProfit);

        Profit dealerLoseProfit = bettingAmount.calculateLoseProfit();
        resetDealerProfit(dealerLoseProfit);
    }

    private Profit getPlayerProfit(Name name) {
        return values.get(name);
    }

    private void resetDealerProfit(Profit dealerWinProfit) {
        Profit currentDealerProfit = getDealerProfit();
        Profit afterDealerProfit = currentDealerProfit.add(dealerWinProfit);
        values.replace(DEALER_NAME, afterDealerProfit);
    }

    private Profit getDealerProfit() {
        return values.get(DEALER_NAME);
    }
}
