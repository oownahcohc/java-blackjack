package gmbs.domain.state.finish;

import gmbs.domain.card.CardHand;
import gmbs.domain.game.result.Result;
import gmbs.domain.state.CardState;

public final class BlackJack extends Finish {

    private BlackJack(final CardHand cardHand) {
        super(cardHand);
    }

    public static BlackJack from(final CardHand cardHand) {
        return new BlackJack(cardHand);
    }

    @Override
    public boolean isBust() {
        return false;
    }

    @Override
    public Result getCompareResult(CardState compareCardState) {
        if (compareCardState.isSameCardStateWith(BlackJack.class)) {
            return Result.DRAW;
        }
        return Result.WIN;
    }
}
