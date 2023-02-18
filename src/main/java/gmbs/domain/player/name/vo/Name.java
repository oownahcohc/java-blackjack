package gmbs.domain.player.name.vo;

import java.util.Objects;

public final class Name {

    private final String value;

    private Name(final String value) {
        this.value = value;
    }

    public static Name from(final String value) {
        validateBlank(value);
        return new Name(value);
    }

    private static void validateBlank(String name) {
        if (name.isBlank()) {
            throw new IllegalArgumentException("[ERROR] 이름에 공백이 들어올 수 없습니다");
        }
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Name name = (Name) o;
        return Objects.equals(value, name.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
