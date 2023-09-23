package gmbs.domain.game;

import gmbs.domain.game.vo.Profit;
import gmbs.domain.participants.vo.Name;

import java.util.Map;

public interface ProfitCalculator {

    Map<Name, Profit> getFinalProfit();

    void calculateTie(Name name);

    void calculateLose(Name name);

    void calculateWin(Name name);

    void calculateBlackJack(Name name);
}
