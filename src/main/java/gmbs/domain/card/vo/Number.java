package gmbs.domain.card.vo;

import gmbs.domain.card.state.vo.TotalScore;

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

    Number(String name, int defaultScore) {
        this.name = name;
        this.defaultScore = defaultScore;
    }

    public static TotalScore calculateTotalScore(List<Number> numbers) {
        int defaultSum = calculateDefaultSum(numbers);
        TotalScore defaultTotalScore = new TotalScore(defaultSum);
        if (hasAce(numbers)) {
            return addAdditionalScore(defaultTotalScore);
        }
        return defaultTotalScore;
    }

    private static int calculateDefaultSum(List<Number> numbers) {
        return numbers.stream()
                .mapToInt(number -> number.defaultScore)
                .sum();
    }

    private static boolean hasAce(List<Number> numbers) {
        return numbers.stream()
                .anyMatch(number -> number.name.equals("A"));
    }

    private static TotalScore addAdditionalScore(TotalScore defaultScore) {
        TotalScore totalScoreAfterAdd = defaultScore.add(ADDITIONAL_ACE_SCORE);
        if (totalScoreAfterAdd.isOverThanBlackJackScore()) {
            return defaultScore;
        }
        return totalScoreAfterAdd;
    }

    public String getName() {
        return name;
    }

    public static List<Number> getAllNumbers() {
        return List.of(values());
    }
}
