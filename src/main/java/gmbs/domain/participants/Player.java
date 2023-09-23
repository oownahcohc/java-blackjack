package gmbs.domain.participants;

import gmbs.domain.card.CardHand;
import gmbs.domain.card.state.finish.Finish;
import gmbs.domain.card.vo.Card;
import gmbs.domain.dto.CardStateType;
import gmbs.domain.participants.vo.Name;

import java.util.List;

public class Player implements Participants {

    private final Name name;
    private final CardHand cardHand;

    private Player(Name name, CardHand cardHand) {
        this.name = name;
        this.cardHand = cardHand;
    }

    @Override
    public Participants drawCard(Card card) {
        if (cardHand.isDrawable()) {
            return new Player(name, cardHand.add(card));
        }
        return this;
    }

    @Override
    public Participants turnEnd() {
        if (cardHand.isDrawable()) {
            return new Player(name, cardHand.stand());
        }
        return this;
    }

    @Override
    public Finish getFinalCardStateAfterGameEnd() {
        return cardHand.getFinishCardState();
    }

    @Override
    public Name notifyName() {
        return name;
    }

    @Override
    public List<String> showCardHandNameValues() {
        return cardHand.getCardHandNames();
    }

    public static Player create(Name name, List<Card> initCards) {
        return new Player(name, CardHand.initFrom(initCards, CardStateType.PLAYER));
    }
}
