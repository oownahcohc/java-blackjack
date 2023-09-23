package gmbs.domain.card.state.finish;

import gmbs.domain.Result;
import gmbs.domain.card.state.vo.TotalScore;

public class Bust extends Finish {

    public Bust(TotalScore totalScore) {
        super(totalScore);
    }

    @Override
    public Result produceResult(Finish another) {
        if (another.isSameStateWith(Bust.class)) {
            return Result.TIE;
        }
        return Result.LOSE;
    }
}
