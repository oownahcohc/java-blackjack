package gmbs.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;

class DrawStatusTest {

    @DisplayName("공백이 입력되면 예외가 발생한다")
    @ParameterizedTest
    @ValueSource(strings = " ")
    void fail_createDrawStatusBlank(String blankInput) {
        // when, then
        assertThatThrownBy(() -> DrawStatus.from(blankInput))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 입력값은 공백일 수 없습니다");
    }

    @DisplayName("y, n 이 아닌 값이 들어오면 예외가 발생한다")
    @ParameterizedTest
    @ValueSource(strings = {"a", "ㄱ", "1", "@"})
    void fail_createDrawStatusWrong(String wrongInput) {
        // when, then
        assertThatThrownBy(() -> DrawStatus.from(wrongInput))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 존재하지 않는 DrawStatus 입니다");
    }

    @DisplayName("입력값으로 y, n 이 들어오면 DrawStatus 를 생성한다")
    @ParameterizedTest
    @ValueSource(strings = {"y", "n"})
    void from(String input) {
        // when, then
        assertThatCode(() -> DrawStatus.from(input))
                .doesNotThrowAnyException();
    }
}