package gmbs.view.output;

import gmbs.domain.card.state.vo.TotalScore;
import gmbs.domain.game.vo.Profit;
import gmbs.domain.participants.Participants;
import gmbs.domain.participants.Player;
import gmbs.domain.participants.vo.Name;

import java.util.List;
import java.util.Map;

public interface OutputConsole {

    void printInputGamerNames();

    void printInputBettingAmount(Player player);

    void printPlayerInitInfo(List<Participants> allParticipants);

    void printQuestionForDraw(Player player);

    void printPlayerInfo(Participants player);

    void printDealerInfo();

    void printPlayerInfoAndTotalScore(Map<Participants, TotalScore> playerAndTotalScore);

    void printGameResults(Map<Name, Profit> playerNameAndProfits);
}
