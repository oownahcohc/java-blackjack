package gmbs.domain.player;

import gmbs.domain.card.vo.Card;
import gmbs.domain.card.vo.TotalScore;
import gmbs.domain.game.result.Result;
import gmbs.domain.state.CardState;

import java.util.List;

public interface Player {

    boolean canDraw();

    BlackJackPlayer drawCard(Card card);

    BlackJackPlayer stand();

    boolean isBust();

    List<String> showCardHandNameValues();

    TotalScore getTotalScore();

    Result getPlayerResult(CardState another);

    String getNameValue();
}
