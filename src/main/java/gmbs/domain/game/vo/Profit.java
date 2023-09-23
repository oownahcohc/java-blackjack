package gmbs.domain.game.vo;

public final class Profit {

    private static final double ZERO_PROFIT = 0;
    private static final double BLACK_JACK_ODDS = 1.5;

    private final double value;

    private Profit(double value) {
        this.value = value;
    }

    public Profit calculateTieProfit() {
        return new Profit(ZERO_PROFIT);
    }

    public Profit calculateLoseProfit() {
        return new Profit(-value);
    }

    public Profit calculateWinProfit() {
        return new Profit(value);
    }

    public Profit calculateBlackJackProfit() {
        return new Profit(value * BLACK_JACK_ODDS);
    }

    public Profit add(Profit profit) {
        return new Profit(value + profit.value);
    }

    public double getValue() {
        return value;
    }

    public static Profit initByBettingAmount(double bettingAmount) {
        return new Profit(bettingAmount);
    }
}
