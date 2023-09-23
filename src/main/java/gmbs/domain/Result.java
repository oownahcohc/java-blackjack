package gmbs.domain;

public enum Result {

    WIN("승"),
    TIE("무"),
    LOSE("패"),
    BLACK_JACK("블랙잭")
    ;

    private final String value;

    Result(String value) {
        this.value = value;
    }

    public boolean isWin() {
        return this == WIN;
    }

    public boolean isLose() {
        return this == LOSE;
    }

    public boolean isTie() {
        return this == TIE;
    }

    public String getValue() {
        return value;
    }
}
