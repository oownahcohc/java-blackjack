package gmbs.domain.player;

import gmbs.domain.card.CardHand;
import gmbs.domain.card.vo.Card;
import gmbs.domain.game.result.Result;
import gmbs.domain.game.result.Results;
import gmbs.domain.player.name.vo.Name;
import gmbs.domain.state.CardState;
import gmbs.domain.state.State;

import java.util.List;
import java.util.stream.Collectors;

public final class Dealer extends BlackJackPlayer {

    private static final int NEED_TO_DELETE_INDEX = 0;
    private static final Name DEALER_NAME = Name.from("딜러");

    private Dealer(final Name name, final CardState cardState) {
        super(name, cardState);
    }

    public static Dealer from(final List<Card> initCards) {
        return new Dealer(DEALER_NAME, State.createForDealer(CardHand.initFrom(initCards)));
    }

    @Override
    public Dealer drawCard(Card card) {
        CardState state = cardState.draw(card);
        return new Dealer(DEALER_NAME, state);
    }

    @Override
    public Dealer stand() {
        CardState state = cardState.stand();
        return new Dealer(DEALER_NAME, state);
    }

    @Override
    public List<String> showCardHandNameValues() {
        List<String> cardHandNames = cardState.getCardHandNames();
        hideOneCard(cardHandNames);
        return cardHandNames;
    }

    private void hideOneCard(final List<String> cardHandNames) {
        cardHandNames.remove(NEED_TO_DELETE_INDEX);
    }

    public Results getDealerResults(List<Gamer> gamers) {
        List<Result> dealerResults = gamers.stream()
                .map(gamer -> this.getPlayerResult(gamer.cardState))
                .collect(Collectors.toUnmodifiableList());
        return Results.from(dealerResults);
    }
}
