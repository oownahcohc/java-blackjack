package gmbs.domain.card;

import java.util.List;

public enum Pattern {

    SPADE("스페이드"),
    DIAMOND("다이아몬드"),
    HEART("하트"),
    CLOVER("클로버"),
    ;

    private final String value;

    Pattern(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static List<Pattern> getAllPatterns() {
        return List.of(values());
    }
}
