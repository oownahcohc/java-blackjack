package gmbs.domain.card;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CardDeckTest {

    private static final int BLACK_JACK_CARD_DECK_SIZE = 52;

    @DisplayName("블랙잭 카드 52장이 생성된다")
    @Test
    void createShuffled() {
        // when
        CardDeck cardDeck = CardDeck.createShuffled();

        // then
        assertThat(cardDeck.getDeck()).hasSize(BLACK_JACK_CARD_DECK_SIZE);
    }

    @DisplayName("빈 카드 덱에서 한장을 뽑으면 예외가 발생한다")
    @Test
    void fail_drawOneInEmptyDeck() {
        // given
        CardDeck emptyCardDeck = provideEmptyCardDeck();

        // when, then
        assertThatThrownBy(emptyCardDeck::drawOne)
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("[ERROR] 더이상 카드가 존재하지 않습니다");
    }

    private CardDeck provideEmptyCardDeck() {
        CardDeck cardDeck = CardDeck.createShuffled();
        for (int i = 0; i < BLACK_JACK_CARD_DECK_SIZE; i++) {
            cardDeck.drawOne();
        }
        return cardDeck;
    }
}