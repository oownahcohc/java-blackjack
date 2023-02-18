package gmbs.domain.card;

import gmbs.domain.card.vo.Card;

import java.util.*;
import java.util.stream.Collectors;

public final class CardDeck {

    private final Deque<Card> deck;

    private CardDeck(final Deque<Card> deck) {
        this.deck = deck;
    }

    public static CardDeck createShuffled() {
        List<Card> deck = generateDeck();
        Collections.shuffle(deck);
        return new CardDeck(new ArrayDeque<>(deck));
    }

    private static List<Card> generateDeck() {
        return Pattern.getAllPatterns()
                .stream()
                .map(CardDeck::getCards)
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    private static List<Card> getCards(Pattern pattern) {
        return Number.getAllNumbers()
                .stream()
                .map(number -> Card.of(number, pattern))
                .collect(Collectors.toList());
    }

    public List<Card> drawTwo() {
        return List.of(deck.pop(), deck.pop());
    }

    public Card drawOne() {
        validateDeckEmpty();
        return deck.pop();
    }

    private void validateDeckEmpty() {
        if (deck.isEmpty()) {
            throw new IllegalStateException("[ERROR] 더이상 카드가 존재하지 않습니다");
        }
    }

    public Deque<Card> getDeck() {
        return deck;
    }
}
