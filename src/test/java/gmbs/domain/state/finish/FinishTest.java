package gmbs.domain.state.finish;

import gmbs.domain.card.CardHand;
import gmbs.domain.card.vo.Card;
import gmbs.domain.state.CardState;
import gmbs.domain.state.State;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static gmbs.domain.card.Number.*;
import static gmbs.domain.card.Pattern.SPADE;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FinishTest {

    private static final List<Card> BLACK_JACK_INIT_CARDS = List.of(Card.of(ACE, SPADE), Card.of(TEN, SPADE));
    private static final List<Card> DEALER_STAND_INIT_CARDS = List.of(Card.of(NINE, SPADE), Card.of(TEN, SPADE));
    private static final List<Card> GAMER_INIT_CARDS = List.of(Card.of(KING, SPADE), Card.of(TEN, SPADE));

    @DisplayName("Finish 상태의 카드가 draw 를 호출하면 에러가 발생한다")
    @ParameterizedTest
    @MethodSource("provideFinishCardStateAndDrawCard")
    void draw(CardState finishState, Card drawCard) {
        // when, then
        assertThatThrownBy(() -> finishState.draw(drawCard))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("[ERROR] 턴이 이미 종료된 상태입니다");
    }

    private static Stream<Arguments> provideFinishCardStateAndDrawCard() {
        return Stream.of(
                Arguments.of(
                        State.createForDealer(CardHand.from(BLACK_JACK_INIT_CARDS)),
                        Card.of(ACE, SPADE)),
                Arguments.of(
                        State.createForDealer(CardHand.from(DEALER_STAND_INIT_CARDS)),
                        Card.of(ACE, SPADE)),
                Arguments.of(
                        State.createForGamer(CardHand.from(GAMER_INIT_CARDS)).draw(Card.of(TWO, SPADE)),
                        Card.of(ACE, SPADE))
        );
    }
}