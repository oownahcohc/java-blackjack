package gmbs.domain.card.vo;

import java.util.Objects;

public final class TotalScore {

    private static final int BLACK_JACK_SCORE = 21;
    private static final int DEALER_LIMIT_SCORE = 16;

    private final int value;

    private TotalScore(final int value) {
        this.value = value;
    }

    public static TotalScore from(final int value) {
        return new TotalScore(value);
    }

    public TotalScore add(int score) {
        return new TotalScore(value + score);
    }

    public boolean isBlackJack() {
        return value == BLACK_JACK_SCORE;
    }

    public boolean isOverThanBlackJackScore() {
        return value > BLACK_JACK_SCORE;
    }

    public boolean isLessThanBlackJackScore() {
        return value <= BLACK_JACK_SCORE;
    }

    public boolean isLessThanDealerLimitScore() {
        return value <= DEALER_LIMIT_SCORE;
    }

    public boolean isUnderThan(TotalScore totalScore) {
        return value < totalScore.value;
    }

    public boolean isOverThan(TotalScore totalScore) {
        return value > totalScore.value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final TotalScore that = (TotalScore) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
