package gmbs.domain.player;

import gmbs.domain.card.vo.Card;
import gmbs.domain.player.name.vo.Name;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static gmbs.domain.card.Number.*;
import static gmbs.domain.card.Pattern.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class GamersTest {

    private static final List<Card> CARDS_HIT = List.of(Card.of(NINE, SPADE), Card.of(TEN, SPADE));
    private static final List<Card> CARDS_BLACK_JACK = List.of(Card.of(ACE, SPADE), Card.of(JACK, CLOVER));
    private static final Gamer GAMER_ONE = Gamer.of(Name.from("게이머1"), CARDS_HIT);
    private static final Gamer GAMER_TWO = Gamer.of(Name.from("게이머2"), CARDS_BLACK_JACK);

    private Gamers GAMERS;

    @BeforeEach
    void setUp() {
        List<Gamer> gamers = new ArrayList<>(List.of(GAMER_ONE, GAMER_TWO));
        GAMERS = Gamers.from(gamers);
    }

    @DisplayName("현재 턴 게이머를 가져온다")
    @Test
    void getCurrentTurnPlayer() {
        // when
        Gamer currentTurnPlayer = GAMERS.getCurrentTurnPlayer();

        // then
        assertAll(
                () -> assertThat(currentTurnPlayer).isEqualTo(GAMER_ONE),
                () -> assertThat(currentTurnPlayer).isNotEqualTo(GAMER_TWO)
        );
    }

    @DisplayName("현재 턴을 다음 사람 턴으로 넘긴다")
    @Test
    void passTurnToNextPlayer() {
        // when
        GAMERS.passTurnToNextPlayer();

        // then
        Gamer currentTurnPlayer = GAMERS.getCurrentTurnPlayer();
        assertAll(
                () -> assertThat(currentTurnPlayer).isEqualTo(GAMER_TWO),
                () -> assertThat(currentTurnPlayer).isNotEqualTo(GAMER_ONE)
        );
    }

    @DisplayName("현재 턴 플레이어가 카드를 뽑은 후 다시 현재 턴 게이머를 반환한다")
    @Test
    void drawCurrentPlayer() {
        // given
        Card DRAW_CARD = Card.of(ACE, SPADE);

        // when
        Gamer currentPlayerAfterDraw = GAMERS.drawCurrentPlayer(DRAW_CARD);

        // then
        assertAll(
                () -> assertThat(currentPlayerAfterDraw).isEqualTo(GAMER_ONE),
                () -> assertThat(currentPlayerAfterDraw).isNotEqualTo(GAMER_TWO)
        );
    }
}