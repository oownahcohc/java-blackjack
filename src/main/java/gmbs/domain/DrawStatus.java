package gmbs.domain;

import java.util.Arrays;

public enum DrawStatus {

    YES("y"),
    NO("n"),
    ;

    private final String value;

    DrawStatus(final String value) {
        this.value = value;
    }

    public static DrawStatus from(final String input) {
        if (input.isBlank()) {
            throw new IllegalArgumentException("[ERROR] 입력값은 공백일 수 없습니다");
        }
        return Arrays.stream(values())
                .filter(drawStatus -> drawStatus.value.equals(input))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 존재하지 않는 DrawStatus 입니다"));
    }

    public boolean isYes() {
        return this == YES;
    }
}
