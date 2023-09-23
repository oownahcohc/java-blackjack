package gmbs.domain.card;

import gmbs.domain.card.state.CardState;
import gmbs.domain.card.state.finish.Finish;
import gmbs.domain.card.state.vo.TotalScore;
import gmbs.domain.card.vo.Card;
import gmbs.domain.card.vo.Number;
import gmbs.domain.dto.CardStateType;

import java.util.List;
import java.util.stream.Collectors;

public class CardHand {

    private static final int INITIAL_CARD_HAND_SIZE = 2;

    private final List<Card> values;
    private final CardState cardState;

    private CardHand(List<Card> values, CardState cardState) {
        this.values = values;
        this.cardState = cardState;
    }

    public CardHand add(Card card) {
        values.add(card);
        return CardHand.resetFrom(values);
    }

    public boolean isDrawable() {
        return cardState.canDraw();
    }

    public CardHand stand() {
        return new CardHand(values, cardState.stand(calculateScore(values)));
    }

    public Finish getFinishCardState() {
        validateIsCardStateFinished();
        return (Finish) cardState;
    }

    private void validateIsCardStateFinished() {
        if (!(cardState instanceof Finish)) {
            throw new IllegalStateException("[ERROR]");
        }
    }

    public List<String> getCardHandNames() {
        return values.stream()
                .map(Card::getCardName)
                .collect(Collectors.toUnmodifiableList());
    }

    public static CardHand resetFrom(List<Card> cards) {
        TotalScore score = calculateScore(cards);
        return new CardHand(cards, CardState.resetState(score));
    }

    public static CardHand initFrom(List<Card> cards, CardStateType type) {
        validateInitCardsSize(cards);
        TotalScore score = calculateScore(cards);
        return new CardHand(cards, CardState.initState(score, type));
    }

    private static void validateInitCardsSize(List<Card> initCards) {
        if (initCards.size() != INITIAL_CARD_HAND_SIZE) {
            throw new IllegalArgumentException("[ERROR] 초기 패는 반드시 두장이어야 합니다");
        }
    }

    private static TotalScore calculateScore(List<Card> cards) {
        List<Number> numbers = cards.stream()
                .map(Card::getNumber)
                .collect(Collectors.toUnmodifiableList());
        return Number.calculateTotalScore(numbers);
    }
}
