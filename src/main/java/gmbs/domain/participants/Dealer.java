package gmbs.domain.participants;

import gmbs.domain.card.CardHand;
import gmbs.domain.card.state.finish.Finish;
import gmbs.domain.card.vo.Card;
import gmbs.domain.dto.CardStateType;
import gmbs.domain.participants.vo.Name;

import java.util.List;
import java.util.stream.Collectors;

public class Dealer implements Participants {

    private static final Name DEALER_NAME = Name.from("딜러");

    private final Name name;
    private final CardHand cardHand;

    private Dealer(Name name, CardHand cardHand) {
        this.name = name;
        this.cardHand = cardHand;
    }

    @Override
    public Participants drawCard(Card card) {
        if (cardHand.isDrawable()) {
            return new Dealer(name, cardHand.add(card));
        }
        return this;
    }

    @Override
    public Participants turnEnd() {
        if (cardHand.isDrawable()) {
            return new Dealer(name, cardHand.stand());
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
        List<String> cardHandNames = cardHand.getCardHandNames();
        return showJustOneCard(cardHandNames);
    }

    private List<String> showJustOneCard(List<String> cardHandNames) {
        return cardHandNames.stream()
                .limit(1)
                .collect(Collectors.toUnmodifiableList());
    }

    public boolean isMustDraw() {
        return cardHand.isDrawable();
    }

    public Name getName() {
        return name;
    }

    public static Dealer create(List<Card> initCards) {
        return new Dealer(
                DEALER_NAME,
                CardHand.initFrom(initCards, CardStateType.DEALER)
        );
    }
}
