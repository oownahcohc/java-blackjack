package gmbs.domain.player.name.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class NameTest {

    @DisplayName("이름에 공백이 들어오면 예외가 발생한다")
    @ParameterizedTest
    @ValueSource(strings = " ")
    void fail_createNameWithBlank(String blank) {
        // when, then
        assertThatThrownBy(() -> Name.from(blank))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 이름에 공백이 들어올 수 없습니다");
    }
}