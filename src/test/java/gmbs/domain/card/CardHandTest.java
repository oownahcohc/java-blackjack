package gmbs.domain.card;

import gmbs.domain.card.vo.Card;
import gmbs.domain.card.vo.TotalScore;
import gmbs.domain.game.result.Result;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static gmbs.domain.card.Number.*;
import static gmbs.domain.card.Pattern.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class CardHandTest {

    private static final Card FIRST_INIT_CARD = Card.of(ACE, SPADE);
    private static final Card SECOND_INIT_CARD = Card.of(JACK, CLOVER);
    private static final Card ADDITIONAL_CARD = Card.of(TWO, HEART);

    @DisplayName("초기 카드의 개수가 두장이 아니라면 예외가 발생한다")
    @ParameterizedTest
    @MethodSource("provideInvalidSizeTwoCards")
    void fail_createCardHandSizeIsNotTwo(List<Card> wrongInitCards) {
        // when, then
        assertThatThrownBy(() -> CardHand.initFrom(wrongInitCards))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 초기 패는 반드시 두장이어야 합니다");
    }

    private static Stream<Arguments> provideInvalidSizeTwoCards() {
        return Stream.of(
                Arguments.of(List.of(FIRST_INIT_CARD)),
                Arguments.of(List.of(FIRST_INIT_CARD, Card.of(TWO, CLOVER), Card.of(THREE, HEART)))
        );
    }

    @DisplayName("패의 카드 숫자 합을 계산한다")
    @ParameterizedTest
    @MethodSource("provideInitCardsAndExpectTotalScore")
    void calculateTotalScore(List<Card> initCards, TotalScore expect) {
        // given
        CardHand cardHand = CardHand.initFrom(initCards);

        // when
        TotalScore actual = cardHand.calculateTotalScore();

        // then
        assertThat(actual).isEqualTo(expect);
    }

    private static Stream<Arguments> provideInitCardsAndExpectTotalScore() {
        return Stream.of(
                Arguments.of(
                        List.of(Card.of(ACE, SPADE), Card.of(JACK, CLOVER)),
                        TotalScore.from(21)),
                Arguments.of(
                        List.of(Card.of(ACE, SPADE), Card.of(ACE, CLOVER)),
                        TotalScore.from(12)),
                Arguments.of(
                        List.of(Card.of(ACE, SPADE), Card.of(TWO, CLOVER)),
                        TotalScore.from(13)),
                Arguments.of(
                        List.of(Card.of(KING, SPADE), Card.of(QUEEN, CLOVER)),
                        TotalScore.from(20))
        );
    }

    @DisplayName("현재 패에 한장의 카드를 추가하고, 추가된 패와 추가 이전의 패는 같은 패이다")
    @Test
    void add() {
        // given
        List<Card> initCards = List.of(FIRST_INIT_CARD, SECOND_INIT_CARD);
        CardHand cardHand = CardHand.initFrom(initCards);

        // when
        CardHand addCardHand = cardHand.add(ADDITIONAL_CARD);

        // then
        assertAll(
                () -> assertThat(addCardHand.getValues()).hasSize(3),
                () -> assertThat(addCardHand).isEqualTo(cardHand)
        );
    }

    @DisplayName("기준 패 숫자 합과 다른 패 숫자 합을 비교해 결과를 가져온다")
    @ParameterizedTest
    @MethodSource("provideStandardCardsAndAnotherCardsAndExpectResult")
    void getCompareResult(List<Card> standardCards, List<Card> anotherCards, Result expect) {
        // given
        CardHand cardHand = CardHand.initFrom(standardCards);
        CardHand another = CardHand.initFrom(anotherCards);

        // when
        Result actual = cardHand.getCompareResult(another);

        // then
        assertThat(actual).isEqualTo(expect);
    }

    private static Stream<Arguments> provideStandardCardsAndAnotherCardsAndExpectResult() {
        return Stream.of(
                Arguments.of(
                        List.of(Card.of(ACE, SPADE), Card.of(TWO, CLOVER)),
                        List.of(FIRST_INIT_CARD, SECOND_INIT_CARD),
                        Result.LOSE),
                Arguments.of(
                        List.of(Card.of(ACE, SPADE), Card.of(TWO, CLOVER)),
                        List.of(Card.of(ACE, SPADE), Card.of(TWO, CLOVER)),
                        Result.DRAW),
                Arguments.of(
                        List.of(Card.of(ACE, SPADE), Card.of(TWO, CLOVER)),
                        List.of(Card.of(ACE, SPADE), Card.of(ACE, CLOVER)),
                        Result.WIN)
        );
    }
}