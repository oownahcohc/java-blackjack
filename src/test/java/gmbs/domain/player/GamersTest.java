package gmbs.domain.player;

import gmbs.domain.card.vo.Card;
import gmbs.domain.game.result.Result;
import gmbs.domain.game.result.Results;
import gmbs.domain.player.name.vo.Name;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static gmbs.domain.card.Number.*;
import static gmbs.domain.card.Pattern.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class GamersTest {

    private static final List<Card> CARDS_HIT = List.of(Card.of(NINE, SPADE), Card.of(TEN, SPADE));
    private static final List<Card> CARDS_BLACK_JACK = List.of(Card.of(ACE, SPADE), Card.of(JACK, CLOVER));
    private static final Gamer GAMER_ONE = Gamer.of(Name.from("게이머1"), CARDS_HIT);
    private static final Gamer GAMER_TWO = Gamer.of(Name.from("게이머2"), CARDS_BLACK_JACK);

    @DisplayName("현재 턴 게이머를 가져온다")
    @Test
    void getCurrentTurnPlayer() {
        // given
        List<Gamer> twoGamers = new ArrayList<>(List.of(GAMER_ONE, GAMER_TWO));
        Gamers gamers = Gamers.from(twoGamers);

        // when
        Gamer currentTurnPlayer = gamers.getCurrentTurnPlayer();

        // then
        assertAll(
                () -> assertThat(currentTurnPlayer).isEqualTo(GAMER_ONE),
                () -> assertThat(currentTurnPlayer).isNotEqualTo(GAMER_TWO)
        );
    }

    @DisplayName("현재 턴을 다음 사람 턴으로 넘긴다")
    @Test
    void passTurnToNextPlayer() {
        // given
        List<Gamer> twoGamers = new ArrayList<>(List.of(GAMER_ONE, GAMER_TWO));
        Gamers gamers = Gamers.from(twoGamers);

        // when
        gamers.passTurnToNextPlayer();

        // then
        Gamer currentTurnPlayer = gamers.getCurrentTurnPlayer();
        assertAll(
                () -> assertThat(currentTurnPlayer).isEqualTo(GAMER_TWO),
                () -> assertThat(currentTurnPlayer).isNotEqualTo(GAMER_ONE)
        );
    }

    @DisplayName("현재 턴 플레이어가 카드를 뽑은 후 다시 현재 턴 게이머를 반환한다")
    @Test
    void drawCurrentPlayer() {
        // given
        List<Gamer> twoGamers = new ArrayList<>(List.of(GAMER_ONE, GAMER_TWO));
        Gamers gamers = Gamers.from(twoGamers);

        // given
        Card drawCard = Card.of(ACE, SPADE);

        // when
        Gamer currentPlayerAfterDraw = gamers.drawCurrentPlayer(drawCard);

        // then
        assertAll(
                () -> assertThat(currentPlayerAfterDraw).isEqualTo(GAMER_ONE),
                () -> assertThat(currentPlayerAfterDraw).isNotEqualTo(GAMER_TWO)
        );
    }

    @DisplayName("게이머와 딜러를 받아 게이머의 승,무,패 결과를 가져온다")
    @ParameterizedTest
    @MethodSource("provideStandGamersAndDealerAndExpectResults")
    void getGamerResults(Gamers gamers, Dealer dealer, Results expectResults) {
        // when
        Map<Name, Results> gamerResults = gamers.getGamerResults(dealer);

        // then
        assertThat(gamerResults).containsValue(expectResults);
    }

    private static Stream<Arguments> provideStandGamersAndDealerAndExpectResults() {
        return Stream.of(
                Arguments.of(
                        Gamers.from(List.of(GAMER_ONE.stand())),
                        Dealer.from(CARDS_BLACK_JACK),
                        Results.from(List.of(Result.LOSE))),
                Arguments.of(
                        Gamers.from(List.of(GAMER_TWO.stand())),
                        Dealer.from(CARDS_BLACK_JACK),
                        Results.from(List.of(Result.DRAW))),
                Arguments.of(
                        Gamers.from(List.of(GAMER_TWO.stand())),
                        Dealer.from(CARDS_HIT),
                        Results.from(List.of(Result.WIN)))
        );
    }
}