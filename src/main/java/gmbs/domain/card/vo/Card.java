package gmbs.domain.card.vo;

import gmbs.domain.card.Number;
import gmbs.domain.card.Pattern;

import java.util.*;

public final class Card {

    private final Number number;
    private final Pattern pattern;

    private Card(final Number number, final Pattern pattern) {
        this.number = number;
        this.pattern = pattern;
    }

    public static Card of(final Number number, final Pattern pattern) {
        return new Card(number, pattern);
    }

    public Number getNumber() {
        return number;
    }

    public String getCardName() {
        return number.getName() + pattern.getValue();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Card card = (Card) o;
        return number == card.number && pattern == card.pattern;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, pattern);
    }
}
