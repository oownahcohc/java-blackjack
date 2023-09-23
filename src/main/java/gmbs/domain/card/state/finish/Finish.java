package gmbs.domain.card.state.finish;

import gmbs.domain.Result;
import gmbs.domain.card.state.vo.TotalScore;
import gmbs.domain.card.state.CardState;

public abstract class Finish implements CardState {

    protected final TotalScore totalScore;

    protected Finish(TotalScore totalScore) {
        this.totalScore = totalScore;
    }

    public boolean isBlackJack() {
        return this.isSameStateWith(BlackJack.class);
    }

    public TotalScore getTotalScore() {
        return totalScore;
    }

    public abstract Result produceResult(Finish another);

    protected boolean isSameStateWith(Class<? extends CardState> cardState) {
        return this.getClass().equals(cardState);
    }

    @Override
    public boolean canDraw() {
        return false;
    }
}
