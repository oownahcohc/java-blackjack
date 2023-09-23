package gmbs.domain.participants;

import gmbs.domain.card.state.finish.Finish;
import gmbs.domain.card.vo.Card;
import gmbs.domain.participants.vo.Name;

import java.util.List;

public interface Participants {

    Participants drawCard(Card card);

    Participants turnEnd();

    Finish getFinalCardStateAfterGameEnd();

    Name notifyName();

    List<String> showCardHandNameValues();
}
