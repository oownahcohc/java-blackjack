package gmbs.domain.card.state.finish;

import gmbs.domain.Result;
import gmbs.domain.card.state.vo.TotalScore;

public class BlackJack extends Finish {

    public BlackJack(TotalScore totalScore) {
        super(totalScore);
    }

    @Override
    public Result produceResult(Finish another) {
        if (another.isSameStateWith(BlackJack.class)) {
            return Result.TIE;
        }
        return Result.BLACK_JACK;
    }
}
