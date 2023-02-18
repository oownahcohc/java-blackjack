package gmbs.domain.game.result;

import gmbs.domain.card.vo.TotalScore;

public enum Result {

    WIN("승"),
    DRAW("무"),
    LOSE("패"),
    ;

    private final String value;

    Result(final String value) {
        this.value = value;
    }

    public static Result compareGameResult(TotalScore standardTotalScore, TotalScore compareTotalScore) {
        if (standardTotalScore.isOverThan(compareTotalScore)) {
            return WIN;
        }
        if (standardTotalScore.isUnderThan(compareTotalScore)) {
            return LOSE;
        }
        return DRAW;
    }

    public String getValue() {
        return value;
    }
}
