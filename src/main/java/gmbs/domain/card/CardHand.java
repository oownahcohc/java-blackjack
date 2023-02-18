package gmbs.domain.card;

import gmbs.domain.card.vo.Card;
import gmbs.domain.card.vo.TotalScore;
import gmbs.domain.game.result.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CardHand {

    private static final int INITIAL_CARD_HAND_SIZE = 2;

    private final List<Card> values;

    private CardHand(final List<Card> values) {
        this.values = values;
    }

    public static CardHand from(final List<Card> initCards) {
        validateInitCardsSize(initCards);
        return new CardHand(new ArrayList<>(initCards));
    }

    private static void validateInitCardsSize(final List<Card> initCards) {
        if (initCards.size() != INITIAL_CARD_HAND_SIZE) {
            throw new IllegalArgumentException("[ERROR] 초기 패는 반드시 두장이어야 합니다");
        }
    }

    public TotalScore calculateTotalScore() {
        List<Number> cardHandNumbers = values.stream()
                .map(Card::getNumber)
                .collect(Collectors.toUnmodifiableList());
        return Number.calculateTotalScoreByCardHandNumbers(cardHandNumbers);
    }

    public CardHand add(Card card) {
        values.add(card);
        return this;
    }

    public Result getCompareResult(CardHand another) {
        return Result.compareGameResult(calculateTotalScore(), another.calculateTotalScore());
    }

    public List<String> getCardNames() {
        return values.stream()
                .map(Card::getCardName)
                .collect(Collectors.toList());
    }

    public List<Card> getValues() {
        return values;
    }
}
