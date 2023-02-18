package gmbs.view.output;

import gmbs.domain.card.vo.TotalScore;
import gmbs.domain.game.result.Results;
import gmbs.domain.player.Gamer;
import gmbs.domain.player.Player;
import gmbs.domain.player.name.vo.Name;

import java.util.List;
import java.util.Map;

public interface OutputConsole {

    void printInputGamerNames();

    void printPlayerInitInfo(List<Player> allPlayer);

    void printQuestionForDraw(Gamer gamer);

    void printPlayerInfo(Player player);

    void printDealerInfo();

    void printPlayerInfoAndTotalScore(Map<Player, TotalScore> playerAndTotalScore);

    void printGameResults(Map<Name, Results> playerNameAndResults);
}
