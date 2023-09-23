package gmbs.domain.card.state.vo;

import java.util.Objects;

public final class TotalScore {

    private static final int BLACK_JACK_SCORE = 21;
    private static final int DEALER_LIMIT_SCORE = 16;

    private final int value;

    public TotalScore(int value) {
        this.value = value;
    }

    public boolean isBlackJackScore() {
        return value == BLACK_JACK_SCORE;
    }

    public boolean isOverThanBlackJackScore() {
        return value > BLACK_JACK_SCORE;
    }

    public boolean isOverThanDealerLimitScore() {
        return value > DEALER_LIMIT_SCORE;
    }

    public TotalScore add(int additional) {
        return new TotalScore(value + additional);
    }

    public boolean isOverThan(TotalScore another) {
        return value > another.value;
    }

    public boolean isUnderThan(TotalScore another) {
        return value < another.value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TotalScore that = (TotalScore) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
