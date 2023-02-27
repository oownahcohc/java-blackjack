package gmbs.domain.player;

import gmbs.domain.card.CardHand;
import gmbs.domain.card.vo.Card;
import gmbs.domain.player.name.vo.Name;
import gmbs.domain.state.CardState;
import gmbs.domain.state.State;

import java.util.Collections;
import java.util.List;

public final class Gamer extends BlackJackPlayer {

    private Gamer(final Name name, final CardState cardState) {
        super(name, cardState);
    }

    public static Gamer of(final Name name, final List<Card> initCards) {
        return new Gamer(name, State.createForGamer(CardHand.initFrom(initCards)));
    }

    @Override
    public Gamer drawCard(Card card) {
        CardState state = cardState.draw(card);
        return new Gamer(name, state);
    }

    @Override
    public Gamer stand() {
        CardState state = cardState.stand();
        return new Gamer(name, state);
    }

    @Override
    public List<String> showCardHandNameValues() {
        return Collections.unmodifiableList(
                cardState.getCardHandNames());
    }
}
