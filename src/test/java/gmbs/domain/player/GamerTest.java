package gmbs.domain.player;

import gmbs.domain.card.vo.Card;
import gmbs.domain.card.vo.TotalScore;
import gmbs.domain.player.name.vo.Name;
import gmbs.domain.state.CardState;
import gmbs.domain.state.Hit;
import gmbs.domain.state.finish.BlackJack;
import gmbs.domain.state.finish.Bust;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static gmbs.domain.card.Number.*;
import static gmbs.domain.card.Pattern.*;
import static org.assertj.core.api.Assertions.assertThat;

class GamerTest {

    private static final List<Card> CARDS_HIT = List.of(Card.of(NINE, SPADE), Card.of(TEN, SPADE));
    private static final List<Card> CARDS_BLACK_JACK = List.of(Card.of(ACE, SPADE), Card.of(JACK, CLOVER));
    private static final Name GAMER_NAME = Name.from("게이머");

    @DisplayName("초기 카드를 받아 딜러를 생성하면, 딜러 카드 패의 상태가 결정된다")
    @ParameterizedTest
    @MethodSource("provideGamerInitCardsAndExpectCardState")
    void gamerCardStateSettled(List<Card> gamerInitCards, Class<? extends CardState> expect) {
        // given
        BlackJackPlayer gamer = Gamer.of(GAMER_NAME, gamerInitCards);

        // when
        CardState actual = gamer.cardState;

        // then
        assertThat(actual.getClass()).isEqualTo(expect);
    }

    private static Stream<Arguments> provideGamerInitCardsAndExpectCardState() {
        return Stream.of(
                Arguments.of(CARDS_HIT, Hit.class),
                Arguments.of(CARDS_BLACK_JACK, BlackJack.class)
        );
    }

    @DisplayName("게이머가 카드를 뽑으면 카드를 뽑은 후의 카드 상태를 가진 딜러를 반환한다")
    @ParameterizedTest
    @MethodSource("provideAdditionalCardAndExpect")
    void drawCard(Card drawCard, Class<? extends CardState> expectState) {
        // given
        BlackJackPlayer gamer = Gamer.of(GAMER_NAME, CARDS_HIT);

        // when
        BlackJackPlayer gamerAfterDraw = gamer.drawCard(drawCard);

        // then
        assertThat(gamerAfterDraw.cardState.getClass()).isEqualTo(expectState);
    }

    private static Stream<Arguments> provideAdditionalCardAndExpect() {
        return Stream.of(
                Arguments.of(Card.of(JACK, CLOVER), Bust.class),
                Arguments.of(Card.of(ACE, CLOVER), Hit.class),
                Arguments.of(Card.of(TWO, CLOVER), BlackJack.class)
        );
    }

    @DisplayName("게이머는 자신의 카드 패 두장 모두 보여준다")
    @Test
    void showCardHandNameValues() {
        // given
        BlackJackPlayer gamer = Gamer.of(GAMER_NAME, CARDS_HIT);

        // when
        List<String> showCardHandNames = gamer.showCardHandNameValues();

        // then
        assertThat(showCardHandNames).hasSize(2);
    }

    @DisplayName("초기 카드가 21이하이면 canDraw 가 true, 21이면 false 를 반환한다")
    @ParameterizedTest
    @MethodSource("provideGamerInitCardsAndCanDrawExpect")
    void canDraw(List<Card> gamerInitCards, boolean expect) {
        // given
        BlackJackPlayer gamer = Gamer.of(GAMER_NAME, gamerInitCards);

        // when
        boolean actual = gamer.canDraw();

        // then
        assertThat(actual).isEqualTo(expect);
    }

    private static Stream<Arguments> provideGamerInitCardsAndCanDrawExpect() {
        return Stream.of(
                Arguments.of(CARDS_HIT, true),
                Arguments.of(CARDS_BLACK_JACK, false)
        );
    }

    @DisplayName("초기 카드 두장에 의한 카드 상태는 항상 Bust 가 아니다")
    @ParameterizedTest
    @MethodSource("provideGamerInitCards")
    void isBust(List<Card> gamerInitCards) {
        // given
        BlackJackPlayer gamer = Gamer.of(GAMER_NAME, gamerInitCards);

        // when
        boolean actual = gamer.isBust();

        // then
        assertThat(actual).isFalse();
    }

    private static Stream<Arguments> provideGamerInitCards() {
        return Stream.of(
                Arguments.of(CARDS_HIT),
                Arguments.of(CARDS_BLACK_JACK)
        );
    }

    @DisplayName("딜러 카드 패의 숫자 합을 계산한다")
    @ParameterizedTest
    @MethodSource("provideGamerInitCardsAndExpectTotalScore")
    void getTotalScore(List<Card> gamerInitCards, TotalScore expect) {
        // given
        BlackJackPlayer gamer = Gamer.of(GAMER_NAME, gamerInitCards);

        // when
        TotalScore actual = gamer.getTotalScore();

        // then
        assertThat(actual).isEqualTo(expect);
    }

    private static Stream<Arguments> provideGamerInitCardsAndExpectTotalScore() {
        return Stream.of(
                Arguments.of(CARDS_HIT, TotalScore.from(19)),
                Arguments.of(CARDS_BLACK_JACK, TotalScore.from(21))
        );
    }
}