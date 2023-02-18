package gmbs.domain.card;

import gmbs.domain.card.vo.TotalScore;

import java.util.List;

public enum Number {

    ACE("A", 1),
    TWO("2", 2),
    THREE("3", 3),
    FOUR("4", 4),
    FIVE("5", 5),
    SIX("6", 6),
    SEVEN("7", 7),
    EIGHT("8", 8),
    NINE("9", 9),
    TEN("10", 10),
    JACK("J", 10),
    QUEEN("Q", 10),
    KING("K", 10),
    ;

    private static final int ADDITIONAL_ACE_SCORE = 10;

    private final String name;
    private final int defaultScore;

    Number(final String name, final int defaultScore) {
        this.name = name;
        this.defaultScore = defaultScore;
    }

    public static TotalScore calculateTotalScoreByCardHandNumbers(List<Number> cardHandNumbers) {
        TotalScore totalScore = TotalScore.from(calculateDefaultScore(cardHandNumbers));
        if (hasAce(cardHandNumbers)) {
            return addAdditionalScore(totalScore);
        }
        return totalScore;
    }

    private static int calculateDefaultScore(List<Number> cardHandNumbers) {
        return cardHandNumbers.stream()
                .mapToInt(number -> number.defaultScore)
                .sum();
    }

    private static boolean hasAce(List<Number> cardHandNumbers) {
        return cardHandNumbers.stream()
                .anyMatch(number -> number.name.equals("A"));
    }

    private static TotalScore addAdditionalScore(TotalScore defaultScore) {
        TotalScore additionalTotalScore = defaultScore.add(ADDITIONAL_ACE_SCORE);
        if (additionalTotalScore.isLessThanBlackJackScore()) {
            return additionalTotalScore;
        }
        return defaultScore;
    }

    public String getName() {
        return name;
    }

    public static List<Number> getAllNumbers() {
        return List.of(values());
    }
}
