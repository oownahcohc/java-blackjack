package gmbs.domain.card.state;

import gmbs.domain.card.state.drawable.Drawable;
import gmbs.domain.card.state.finish.BlackJack;
import gmbs.domain.card.state.finish.Bust;
import gmbs.domain.card.state.finish.Stand;
import gmbs.domain.card.state.vo.TotalScore;
import gmbs.domain.dto.CardStateType;

public interface CardState {

    boolean canDraw();

    default CardState stand(TotalScore totalScore) {
        return new Stand(totalScore);
    }

    static CardState initState(TotalScore totalScore, CardStateType type) {
        if (type.equals(CardStateType.DEALER)) {
            return initDealerCardState(totalScore);
        }
        return initPlayerCardState(totalScore);
    }

    private static CardState initDealerCardState(TotalScore totalScore) {
        if (totalScore.isBlackJackScore()) {
            return new BlackJack(totalScore);
        } else if (totalScore.isOverThanDealerLimitScore()) {
            return new Stand(totalScore);
        } else {
            return new Drawable();
        }
    }

    private static CardState initPlayerCardState(TotalScore totalScore) {
        if (totalScore.isBlackJackScore()) {
            return new BlackJack(totalScore);
        } else if (totalScore.isOverThanBlackJackScore()) {
            return new Bust(totalScore);
        } else {
            return new Drawable();
        }
    }

    static CardState resetState(TotalScore totalScore) {
        if (totalScore.isOverThanBlackJackScore()) {
            return new Bust(totalScore);
        }
        return new Stand(totalScore);
    }
}
