package gmbs.domain.state;

import gmbs.domain.card.CardHand;
import gmbs.domain.card.vo.Card;
import gmbs.domain.card.vo.TotalScore;
import gmbs.domain.game.result.Result;
import gmbs.domain.state.finish.BlackJack;
import gmbs.domain.state.finish.Bust;

public final class Hit extends CardState {

    private Hit(final CardHand cardHand) {
        super(cardHand);
    }

    public static Hit from(final CardHand cardHand) {
        return new Hit(cardHand);
    }

    @Override
    public boolean canHit() {
        return true;
    }

    @Override
    public boolean isBust() {
        return false;
    }

    @Override
    public CardState draw(Card card) {
        CardHand addCardHand = cardHand.add(card);
        TotalScore totalScoreAfterDraw = addCardHand.calculateTotalScore();
        if (totalScoreAfterDraw.isBlackJack()) {
            return BlackJack.from(addCardHand);
        }
        if (totalScoreAfterDraw.isOverThanBlackJackScore()) {
            return Bust.from(addCardHand);
        }
        return new Hit(addCardHand);
    }

    @Override
    public Result getResult(CardState cardState) {
        throw new IllegalStateException("[ERROR] 아직 결과를 집계할 수 없습니다");
    }
}
