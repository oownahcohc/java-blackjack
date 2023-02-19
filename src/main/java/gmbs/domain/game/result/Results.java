package gmbs.domain.game.result;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class Results {

    private final List<Result> values;

    private Results(final List<Result> values) {
        this.values = values;
    }

    public static Results from(final List<Result> results) {
        return new Results(results);
    }

    public List<String> getValues() {
        return values.stream()
                .map(Result::getValue)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Results results = (Results) o;
        return Objects.equals(values, results.values);
    }

    @Override
    public int hashCode() {
        return Objects.hash(values);
    }
}
