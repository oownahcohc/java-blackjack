package gmbs.domain.card.state.finish;

import gmbs.domain.Result;
import gmbs.domain.card.state.vo.TotalScore;

public class Stand extends Finish {

    public Stand(TotalScore totalScore) {
        super(totalScore);
    }

    @Override
    public Result produceResult(Finish another) {
        if (another.isSameStateWith(Bust.class)) {
            return Result.WIN;
        } else if (another.isSameStateWith(BlackJack.class)) {
            return Result.LOSE;
        }
        return compareAndProduceResult(another.totalScore);
    }

    private Result compareAndProduceResult(TotalScore another) {
        if (totalScore.isOverThan(another)) {
            return Result.WIN;
        } else if (totalScore.isUnderThan(another)) {
            return Result.LOSE;
        }
        return Result.TIE;
    }
}
