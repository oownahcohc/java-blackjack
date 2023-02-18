package gmbs.domain.game.result;

import gmbs.domain.card.vo.TotalScore;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class ResultTest {

    private static final TotalScore STANDARD_TOTAL_SCORE = TotalScore.from(20);;

    @DisplayName("숫자 총합을 비교해 승,무,패 결과를 가져온다")
    @ParameterizedTest
    @MethodSource("provideStandardTotalScoreAndCompareTotalScoreAndExpect")
    void compareGameResult(TotalScore standardTotalScore, TotalScore compareTotalScore, Result expect) {
        // when
        Result result = Result.compareGameResult(standardTotalScore, compareTotalScore);

        // then
        assertThat(result).isEqualTo(expect);
    }

    private static Stream<Arguments> provideStandardTotalScoreAndCompareTotalScoreAndExpect() {
        return Stream.of(
                Arguments.of(STANDARD_TOTAL_SCORE, TotalScore.from(21), Result.LOSE),
                Arguments.of(STANDARD_TOTAL_SCORE, TotalScore.from(20), Result.DRAW),
                Arguments.of(STANDARD_TOTAL_SCORE, TotalScore.from(19), Result.WIN)
        );
    }
}