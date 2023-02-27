package gmbs.domain.state;

import gmbs.domain.card.CardHand;
import gmbs.domain.card.vo.Card;
import gmbs.domain.state.finish.BlackJack;
import gmbs.domain.state.finish.Finish;
import gmbs.domain.state.finish.Stand;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static gmbs.domain.card.Number.*;
import static gmbs.domain.card.Pattern.SPADE;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class CardStateTest {

    private static final List<Card> BLACK_JACK_CARDS = List.of(Card.of(ACE, SPADE), Card.of(JACK, SPADE));
    private static final List<Card> ESSENTIAL_CARDS = List.of(Card.of(ACE, SPADE), Card.of(TWO, SPADE));
    private static final List<Card> STAND_CARDS = List.of(Card.of(JACK, SPADE), Card.of(SEVEN, SPADE));
    private static final List<Card> HIT_CARDS = List.of(Card.of(NINE, SPADE), Card.of(TEN, SPADE));

    @DisplayName("초기 지급 카드를 받아 create 하면 카드의 상태가 결정된다")
    @Test
    void createFor() {
        // given, when
        CardState dealerBlackJackState = State.createForDealer(CardHand.initFrom(BLACK_JACK_CARDS));
        CardState dealerEssentialState = State.createForDealer(CardHand.initFrom(ESSENTIAL_CARDS));
        CardState dealerStandState = State.createForDealer(CardHand.initFrom(STAND_CARDS));
        CardState gamerBlackJackState = State.createForGamer(CardHand.initFrom(BLACK_JACK_CARDS));
        CardState gamerHitState = State.createForGamer(CardHand.initFrom(HIT_CARDS));

        // then
        assertAll(
                () -> assertThat(dealerBlackJackState.getClass()).isEqualTo(BlackJack.class),
                () -> assertThat(dealerEssentialState.getClass()).isEqualTo(Essential.class),
                () -> assertThat(dealerStandState.getClass()).isEqualTo(Stand.class),
                () -> assertThat(gamerBlackJackState.getClass()).isEqualTo(BlackJack.class),
                () -> assertThat(gamerHitState.getClass()).isEqualTo(Hit.class)
        );
    }

    @DisplayName("카드의 상태가 Bust 이면 true, Bust 가 아니라면 false 를 반환한다")
    @Test
    void isBust() {
        // given
        CardState hitState = State.createForGamer(CardHand.initFrom(HIT_CARDS));
        CardState essentialState = State.createForDealer(CardHand.initFrom(ESSENTIAL_CARDS));
        CardState bustState = State.createForGamer(CardHand.initFrom(HIT_CARDS)).draw(Card.of(THREE, SPADE));

        // when
        boolean isHitStateBust = hitState.isBust();
        boolean isEssentialStateBust = essentialState.isBust();
        boolean isBustStateBust = bustState.isBust();

        // then
        assertAll(
                () -> assertThat(isHitStateBust).isFalse(),
                () -> assertThat(isEssentialStateBust).isFalse(),
                () -> assertThat(isBustStateBust).isTrue()
        );
    }

    @DisplayName("stand 를 호출하면 카드 상태가 Stand 로 바뀐다")
    @Test
    void stand() {
        // given
        CardState gamerState = State.createForGamer(CardHand.initFrom(HIT_CARDS));
        CardState dealerState = State.createForDealer(CardHand.initFrom(ESSENTIAL_CARDS));

        // when
        Finish gamerStand = gamerState.stand();
        Finish dealerStand = dealerState.stand();

        // then
        assertAll(
                () -> assertThat(gamerStand.getClass()).isEqualTo(Stand.class),
                () -> assertThat(dealerStand.getClass()).isEqualTo(Stand.class)
        );
    }
}