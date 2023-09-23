package gmbs.domain.card.vo;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class Card {

    private final Number number;
    private final Pattern pattern;

    private Card(Number number, Pattern pattern) {
        this.number = number;
        this.pattern = pattern;
    }

    public static List<Card> generateCards() {
        return Pattern.getAllPatterns()
                .stream()
                .map(Card::createCards)
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    private static List<Card> createCards(Pattern pattern) {
        return Number.getAllNumbers()
                .stream()
                .map(num -> new Card(num, pattern))
                .collect(Collectors.toList());
    }

    public Number getNumber() {
        return number;
    }

    public String getCardName() {
        return number.getName() + pattern.getValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return number == card.number && pattern == card.pattern;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, pattern);
    }
}
