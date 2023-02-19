package gmbs.domain.player;

import gmbs.domain.card.vo.Card;
import gmbs.domain.card.vo.TotalScore;
import gmbs.domain.game.result.Results;
import gmbs.domain.player.name.vo.Name;
import gmbs.domain.state.CardState;
import gmbs.domain.state.Essential;
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
import static gmbs.domain.card.Pattern.*;
import static org.assertj.core.api.Assertions.assertThat;

class DealerTest {

    private static final List<Card> CARDS_LESS_THAN_DEALER_LIMIT = List.of(Card.of(EIGHT, SPADE), Card.of(EIGHT, HEART));
    private static final List<Card> CARDS_OVER_THAN_DEALER_LIMIT = List.of(Card.of(EIGHT, SPADE), Card.of(NINE, CLOVER));
    private static final List<Card> CARDS_BLACK_JACK = List.of(Card.of(ACE, SPADE), Card.of(JACK, CLOVER));

    @DisplayName("초기 카드를 받아 딜러를 생성하면, 딜러 카드 패의 상태가 결정된다")
    @ParameterizedTest
    @MethodSource("provideDealerInitCardsAndExpectCardState")
    void dealerCardStateSettled(List<Card> dealerInitCards, Class<? extends CardState> expect) {
        // given
        BlackJackPlayer dealer = Dealer.from(dealerInitCards);

        // when
        CardState actual = dealer.cardState;

        // then
        assertThat(actual.getClass()).isEqualTo(expect);
    }

    private static Stream<Arguments> provideDealerInitCardsAndExpectCardState() {
        return Stream.of(
                Arguments.of(CARDS_LESS_THAN_DEALER_LIMIT, Essential.class),
                Arguments.of(CARDS_OVER_THAN_DEALER_LIMIT, Stand.class),
                Arguments.of(CARDS_BLACK_JACK, BlackJack.class)
        );
    }

    @DisplayName("딜러가 카드를 뽑으면 카드를 뽑은 후의 카드 상태를 가진 딜러를 반환한다")
    @ParameterizedTest
    @MethodSource("provideAdditionalCardAndExpect")
    void drawCard(Card drawCard, Class<? extends CardState> expectState) {
        // given
        BlackJackPlayer dealer = Dealer.from(CARDS_LESS_THAN_DEALER_LIMIT);

        // when
        BlackJackPlayer dealerAfterDraw = dealer.drawCard(drawCard);

        // then
        assertThat(dealerAfterDraw.cardState.getClass()).isEqualTo(expectState);
    }

    private static Stream<Arguments> provideAdditionalCardAndExpect() {
        return Stream.of(
                Arguments.of(Card.of(JACK, CLOVER), Bust.class),
                Arguments.of(Card.of(ACE, CLOVER), Stand.class),
                Arguments.of(Card.of(FIVE, CLOVER), BlackJack.class)
        );
    }

    @DisplayName("딜러는 자신의 카드 패를 한장만 보여준다")
    @Test
    void showCardHandNameValues() {
        // given
        BlackJackPlayer dealer = Dealer.from(CARDS_LESS_THAN_DEALER_LIMIT);

        // when
        List<String> showCardHandNames = dealer.showCardHandNameValues();

        // then
        assertThat(showCardHandNames).hasSize(1);
    }

    @DisplayName("게이머들을 받아 딜러와 비교를 통해 딜러의 승,무,패 결과를 가져온다")
    @ParameterizedTest
    @MethodSource("provideGamersAndExpectResults")
    void getDealerResults(List<Gamer> gamers, List<String> expectResults) {
        // given
        Dealer dealer = Dealer.from(CARDS_OVER_THAN_DEALER_LIMIT);

        // when
        Results dealerResults = dealer.getDealerResults(gamers);

        // then
        List<String> actualResults = dealerResults.getValues();
        assertThat(actualResults).isEqualTo(expectResults);
    }

    private static Stream<Arguments> provideGamersAndExpectResults() {
        return Stream.of(
                Arguments.of(
                        List.of(
                                Gamer.of(Name.from("게이머1"), CARDS_LESS_THAN_DEALER_LIMIT),
                                Gamer.of(Name.from("게이머2"), CARDS_OVER_THAN_DEALER_LIMIT),
                                Gamer.of(Name.from("게이머3"), CARDS_BLACK_JACK)),
                        List.of("승", "무", "패")
                )
        );
    }

    @DisplayName("초기 카드가 16이하이면 canDraw 가 true, 16 초과이면 false, 21이면 false 를 반환한다")
    @ParameterizedTest
    @MethodSource("provideDealerInitCardsAndCanDrawExpect")
    void canDraw(List<Card> dealerInitCards, boolean expect) {
        // given
        BlackJackPlayer dealer = Dealer.from(dealerInitCards);

        // when
        boolean actual = dealer.canDraw();

        // then
        assertThat(actual).isEqualTo(expect);
    }

    private static Stream<Arguments> provideDealerInitCardsAndCanDrawExpect() {
        return Stream.of(
                Arguments.of(CARDS_LESS_THAN_DEALER_LIMIT, true),
                Arguments.of(CARDS_OVER_THAN_DEALER_LIMIT, false),
                Arguments.of(CARDS_BLACK_JACK, false)
        );
    }

    @DisplayName("초기 카드 두장에 의한 카드 상태는 항상 Bust 가 아니다")
    @ParameterizedTest
    @MethodSource("provideDealerInitCards")
    void isBust(List<Card> dealerInitCards) {
        // given
        BlackJackPlayer dealer = Dealer.from(dealerInitCards);

        // when
        boolean actual = dealer.isBust();

        // then
        assertThat(actual).isFalse();
    }

    private static Stream<Arguments> provideDealerInitCards() {
        return Stream.of(
                Arguments.of(CARDS_LESS_THAN_DEALER_LIMIT),
                Arguments.of(CARDS_OVER_THAN_DEALER_LIMIT),
                Arguments.of(CARDS_BLACK_JACK)
        );
    }

    @DisplayName("딜러 카드 패의 숫자 합을 계산한다")
    @ParameterizedTest
    @MethodSource("provideDealerInitCardsAndExpectTotalScore")
    void getTotalScore(List<Card> dealerInitCards, TotalScore expect) {
        // given
        BlackJackPlayer dealer = Dealer.from(dealerInitCards);

        // when
        TotalScore actual = dealer.getTotalScore();

        // then
        assertThat(actual).isEqualTo(expect);
    }

    private static Stream<Arguments> provideDealerInitCardsAndExpectTotalScore() {
        return Stream.of(
                Arguments.of(CARDS_LESS_THAN_DEALER_LIMIT, TotalScore.from(16)),
                Arguments.of(CARDS_OVER_THAN_DEALER_LIMIT, TotalScore.from(17)),
                Arguments.of(CARDS_BLACK_JACK, TotalScore.from(21))
        );
    }
}