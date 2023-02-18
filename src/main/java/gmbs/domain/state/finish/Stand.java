package gmbs.domain.state.finish;

import gmbs.domain.card.CardHand;
import gmbs.domain.game.result.Result;
import gmbs.domain.state.CardState;

public final class Stand extends Finish {

    private Stand(final CardHand cardHand) {
        super(cardHand);
    }

    public static Stand from(final CardHand cardHand) {
        return new Stand(cardHand);
    }

    @Override
    public Result getCompareResult(CardState anotherCardState) {
        if (anotherCardState.isSameCardStateWith(Bust.class)) {
            return Result.WIN;
        }
        if (anotherCardState.isSameCardStateWith(BlackJack.class)) {
            return Result.LOSE;
        }
        return cardHand.getCompareResult(anotherCardState.getCardHand());
    }
}
