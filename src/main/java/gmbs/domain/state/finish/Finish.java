package gmbs.domain.state.finish;

import gmbs.domain.card.CardHand;
import gmbs.domain.card.vo.Card;
import gmbs.domain.game.result.Result;
import gmbs.domain.state.CardState;

public abstract class Finish extends CardState {

    protected Finish(final CardHand cardHand) {
        super(cardHand);
    }

    public abstract Result getCompareResult(CardState compareCardState);

    @Override
    public boolean canHit() {
        return false;
    }

    @Override
    public CardState draw(final Card card) {
        throw new IllegalStateException("[ERROR] 턴이 이미 종료된 상태입니다");
    }

    @Override
    public Result getResult(CardState cardState) {
        return getCompareResult(cardState);
    }
}
