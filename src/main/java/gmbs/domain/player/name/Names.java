package gmbs.domain.player.name;

import gmbs.domain.player.name.vo.Name;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class Names {

    private final List<Name> values;

    private Names(final List<Name> values) {
        this.values = values;
    }

    public static Names from(final String[] playerNames) {
        List<Name> names = getNames(playerNames);
        validateDuplicateNames(playerNames, names);
        return new Names(names);
    }

    private static List<Name> getNames(final String[] playerNames) {
        return Stream.of(playerNames)
                .map(Name::from)
                .collect(Collectors.toUnmodifiableList());
    }

    private static void validateDuplicateNames(final String[] playerNames, final List<Name> names) {
        if (names.stream().distinct().count() != playerNames.length) {
            throw new IllegalArgumentException("[ERROR] 이름에 중복이 있습니다");
        }
    }

    public List<Name> getValues() {
        return Collections.unmodifiableList(values);
    }
}
