package gmbs.domain.state.finish;

import gmbs.domain.card.CardHand;
import gmbs.domain.card.vo.Card;
import gmbs.domain.game.result.Result;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static gmbs.domain.card.Number.*;
import static gmbs.domain.card.Pattern.SPADE;
import static org.assertj.core.api.Assertions.assertThat;

class BustTest {

    private static final List<Card> DUMMY_INIT_CARDS = List.of(Card.of(ACE, SPADE), Card.of(TWO, SPADE));

    @DisplayName("Bust 상태와 비교해 Bust 이면 DRAW, Bust 가 아니면 LOSE 를 반환한다")
    @ParameterizedTest
    @MethodSource("provideAllCardStates")
    void pTest(Finish compareCardState) {
        // given
        Finish bustStatus = Bust.from(CardHand.from(DUMMY_INIT_CARDS));

        // when
        Result result = bustStatus.getCompareResult(compareCardState);

        // then
        if (compareCardState.getClass().equals(Bust.class)) {
            assertThat(result).isEqualTo(Result.DRAW);
        } else {
            assertThat(result).isEqualTo(Result.LOSE);
        }
    }

    private static Stream<Arguments> provideAllCardStates() {
        return Stream.of(
                Arguments.of(Bust.from(CardHand.from(DUMMY_INIT_CARDS))),
                Arguments.of(BlackJack.from(CardHand.from(DUMMY_INIT_CARDS))),
                Arguments.of(Stand.from(CardHand.from(DUMMY_INIT_CARDS)))
        );
    }
}