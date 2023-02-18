package gmbs.domain.state;

import gmbs.domain.card.CardHand;
import gmbs.domain.card.vo.TotalScore;
import gmbs.domain.state.finish.Bust;
import gmbs.domain.state.finish.Finish;
import gmbs.domain.state.finish.Stand;

import java.util.List;

public abstract class CardState implements State {

    protected final CardHand cardHand;

    protected CardState(final CardHand cardHand) {
        this.cardHand = cardHand;
    }

    @Override
    public boolean isBust() {
        return isSameCardStateWith(Bust.class);
    }

    @Override
    public boolean isSameCardStateWith(Class<? extends State> cardState) {
        return this.getClass().equals(cardState);
    }

    @Override
    public Finish stand() {
        return Stand.from(cardHand);
    }

    public CardHand getCardHand() {
        return cardHand;
    }

    public List<String> getCardHandNames() {
        return cardHand.getCardNames();
    }

    public TotalScore getTotalScore() {
        return cardHand.calculateTotalScore();
    }
}
