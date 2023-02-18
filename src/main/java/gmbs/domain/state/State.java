package gmbs.domain.state;

import gmbs.domain.card.CardHand;
import gmbs.domain.card.vo.Card;
import gmbs.domain.card.vo.TotalScore;
import gmbs.domain.game.result.Result;
import gmbs.domain.state.finish.BlackJack;
import gmbs.domain.state.finish.Finish;
import gmbs.domain.state.finish.Stand;

public interface State {

    boolean canHit();

    boolean isBust();

    boolean isSameCardStateWith(Class<? extends State> cardState);

    CardState draw(Card card);

    Finish stand();

    Result getResult(CardState cardState);

    static CardState createForGamer(CardHand cardHand) {
        TotalScore totalScore = cardHand.calculateTotalScore();
        if (totalScore.isBlackJack()) {
            return BlackJack.from(cardHand);
        }
        return Hit.from(cardHand);
    }

    static CardState createForDealer(CardHand cardHand) {
        TotalScore totalScore = cardHand.calculateTotalScore();
        if (totalScore.isBlackJack()) {
            return BlackJack.from(cardHand);
        }
        if (!totalScore.isLessThanDealerLimitScore()) {
            return Stand.from(cardHand);
        }
        return Essential.from(cardHand);
    }
}
