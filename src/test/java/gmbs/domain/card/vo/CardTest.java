package gmbs.domain.card.vo;

import gmbs.domain.card.Number;
import gmbs.domain.card.Pattern;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class CardTest {

    private static final Number CARD_NUMBER = Number.ACE;
    private static final Pattern CARD_PATTERN = Pattern.SPADE;

    @Test
    void getCardName() {
        // given
        Card card = Card.of(CARD_NUMBER, CARD_PATTERN);

        // when
        String cardName = card.getCardName();

        // then
        assertThat(cardName).isEqualTo(CARD_NUMBER.getName() + CARD_PATTERN.getValue());
    }
}