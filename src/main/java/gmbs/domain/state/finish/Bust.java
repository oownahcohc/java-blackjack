package gmbs.domain.state.finish;

import gmbs.domain.card.CardHand;
import gmbs.domain.game.result.Result;
import gmbs.domain.state.CardState;

public final class Bust extends Finish {

    private Bust(final CardHand cardHand) {
        super(cardHand);
    }

    public static Bust from(final CardHand cardHand) {
        return new Bust(cardHand);
    }

    @Override
    public boolean isBust() {
        return true;
    }

    @Override
    public Result getCompareResult(CardState compareCardState) {
        if (compareCardState.isBust()) {
            return Result.DRAW;
        }
        return Result.LOSE;
    }
}
