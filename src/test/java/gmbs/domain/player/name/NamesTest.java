package gmbs.domain.player.name;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class NamesTest {

    @DisplayName("중복된 이름이 들어오면 예외가 발생한다")
    @Test
    void fail_createNamesWithDuplicateNames() {
        // given
        String[] duplicateNames = new String[] {"중복이름", "중복이름"};

        // when, then
        assertThatThrownBy(() -> Names.from(duplicateNames))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 이름에 중복이 있습니다");
    }
}