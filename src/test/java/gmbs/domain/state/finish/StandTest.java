package gmbs.domain.state.finish;

import gmbs.domain.card.CardHand;
import gmbs.domain.card.vo.Card;
import gmbs.domain.game.result.Result;
import gmbs.domain.state.CardState;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static gmbs.domain.card.Number.*;
import static gmbs.domain.card.Pattern.SPADE;
import static org.assertj.core.api.Assertions.assertThat;

class StandTest {

    private static final List<Card> BLACK_JACK_INIT_CARDS = List.of(Card.of(ACE, SPADE), Card.of(TEN, SPADE));
    private static final List<Card> DUMMY_INIT_CARDS = List.of(Card.of(ACE, SPADE), Card.of(TWO, SPADE));

    @DisplayName("Stand 상태와 비교해 BlackJack 이면 LOSE, Bust 이면 WIN 을 반환한다")
    @ParameterizedTest
    @MethodSource("provideAllCardStates")
    void getCompareResult(CardState compareCardState) {
        // given
        Finish standStatus = Stand.from(CardHand.initFrom(BLACK_JACK_INIT_CARDS));

        // when
        Result result = standStatus.getCompareResult(compareCardState);

        // then
        if (compareCardState.getClass().equals(BlackJack.class)) {
            assertThat(result).isEqualTo(Result.LOSE);
        } else if (compareCardState.getClass().equals(Bust.class)) {
            assertThat(result).isEqualTo(Result.WIN);
        }
    }

    private static Stream<Arguments> provideAllCardStates() {
        return Stream.of(
                Arguments.of(BlackJack.from(CardHand.initFrom(DUMMY_INIT_CARDS))),
                Arguments.of(Bust.from(CardHand.initFrom(DUMMY_INIT_CARDS))),
                Arguments.of(Stand.from(CardHand.initFrom(DUMMY_INIT_CARDS)))
        );
    }
}