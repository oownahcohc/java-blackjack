package gmbs.domain.card.vo;

import java.util.*;

public final class CardDeck {

    private final Deque<Card> values;

    private CardDeck(Deque<Card> values) {
        this.values = values;
    }

    public static CardDeck shuffle(List<Card> cards) {
        Collections.shuffle(cards);
        return new CardDeck(new ArrayDeque<>(cards));
    }

    public List<Card> initialDraw() {
        return new ArrayList<>(List.of(values.pop(), values.pop()));
    }

    public Card drawOne() {
        validateDeckEmpty();
        return values.pop();
    }

    private void validateDeckEmpty() {
        if (values.isEmpty()) {
            throw new IllegalStateException("[ERROR] 더이상 카드가 존재하지 않습니다");
        }
    }
}
