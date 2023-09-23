package gmbs.domain.participants.vo;

import java.util.Objects;

public final class Name {

    private final String value;

    private Name(String value) {
        this.value = value;
    }

    public static Name from(String value) {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Name name = (Name) o;
        return value.equals(name.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
