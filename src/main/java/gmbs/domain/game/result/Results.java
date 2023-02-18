package gmbs.domain.game.result;

import java.util.List;
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
}
