package gmbs.domain.state;

import gmbs.domain.card.CardHand;
import gmbs.domain.card.vo.Card;
import gmbs.domain.state.finish.BlackJack;
import gmbs.domain.state.finish.Bust;
import gmbs.domain.state.finish.Stand;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static gmbs.domain.card.Number.*;
import static gmbs.domain.card.Pattern.HEART;
import static gmbs.domain.card.Pattern.SPADE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EssentialTest {

    private static final List<Card> INIT_CARDS = List.of(Card.of(EIGHT, SPADE), Card.of(EIGHT, HEART));

    @DisplayName("Essential 상태에서 draw 를 했을 때, 카드 결과가 21이 넘으면 Bust, 21이면 BlackJack, 21미만이면 Stand 상태를 반환한다")
    @ParameterizedTest
    @MethodSource("provideDrawCardAndExpectCardState")
    void draw(CardState essentialState, Card drawCard, Class<? extends CardState> expect) {
        // when
        CardState actual = essentialState.draw(drawCard);

        // then
        assertThat(actual.getClass()).isEqualTo(expect);
    }

    private static Stream<Arguments> provideDrawCardAndExpectCardState() {
        return Stream.of(
                Arguments.of(Essential.from(CardHand.from(INIT_CARDS)), Card.of(JACK, SPADE), Bust.class),
                Arguments.of(Essential.from(CardHand.from(INIT_CARDS)), Card.of(FIVE, SPADE), BlackJack.class),
                Arguments.of(Essential.from(CardHand.from(INIT_CARDS)), Card.of(ACE, SPADE), Stand.class)
        );
    }

    @DisplayName("Essential 상태에서 getResult 를 호출하면 에러가 발생한다")
    @Test
    void getResult() {
        // given
        CardState essentialState = Essential.from(CardHand.from(INIT_CARDS));
        CardState compareState = Essential.from(CardHand.from(INIT_CARDS));

        // when, then
        assertThatThrownBy(() -> essentialState.getResult(compareState))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("[ERROR] 아직 결과를 집계할 수 없습니다");
    }
}