package gmbs.domain.card;

import gmbs.domain.card.vo.TotalScore;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class NumberTest {

    @DisplayName("카드의 숫자를 받아 숫자의 총 합을 계산한다")
    @ParameterizedTest
    @MethodSource("provideNumbersAndExpectTotalScore")
    void calculateTotalScoreByCardHandNumbers(List<Number> numbers, TotalScore expect) {
        // when
        TotalScore actual = Number.calculateTotalScoreByCardHandNumbers(numbers);

        // then
        assertThat(actual).isEqualTo(expect);
    }

    private static Stream<Arguments> provideNumbersAndExpectTotalScore() {
        return Stream.of(
                Arguments.of(List.of(Number.ACE, Number.JACK), TotalScore.from(21)),
                Arguments.of(List.of(Number.ACE, Number.ACE), TotalScore.from(12)),
                Arguments.of(List.of(Number.ACE, Number.TWO), TotalScore.from(13)),
                Arguments.of(List.of(Number.KING, Number.QUEEN), TotalScore.from(20)),
                Arguments.of(List.of(Number.THREE, Number.NINE, Number.TEN), TotalScore.from(22))
        );
    }
}