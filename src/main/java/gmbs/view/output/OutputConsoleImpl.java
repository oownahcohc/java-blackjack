package gmbs.view.output;

import gmbs.domain.card.vo.TotalScore;
import gmbs.domain.game.result.Results;
import gmbs.domain.player.Gamer;
import gmbs.domain.player.Player;
import gmbs.domain.player.name.vo.Name;

import java.util.List;
import java.util.Map;

public class OutputConsoleImpl implements OutputConsole {

    private static final String CARD_SYMBOL = "카드 : ";
    private static final String RESULT_SYMBOL = " - 결과 : ";
    private static final String LINE_BREAK = "\n";
    private static final String FINAL_RESULT = "### 최종 승패 ###";

    @Override
    public void printInputGamerNames() {
        System.out.println("게임에 참여할 사람의 이름을 입력하세요");
    }

    @Override
    public void printPlayerInitInfo(List<Player> allPlayer) {
        StringBuilder stringBuilder = new StringBuilder();
        allPlayer.forEach(player ->
                stringBuilder.append(player.getNameValue())
                        .append(": ")
                        .append(player.showCardHandNameValues())
                        .append(LINE_BREAK));
        System.out.println(stringBuilder);
    }

    @Override
    public void printQuestionForDraw(Gamer gamer) {
        System.out.printf("%s는 한장의 카드를 더 받겠습니까?(예는 y, 아니오는 n)%n", gamer.getNameValue());
    }

    @Override
    public void printPlayerInfo(Player player) {
        String stringBuilder = player.getNameValue() +
                CARD_SYMBOL +
                player.showCardHandNameValues() +
                LINE_BREAK;
        System.out.println(stringBuilder);
    }

    @Override
    public void printDealerInfo() {
        System.out.println("딜러는 16이하라 한장의 카드를 더 받았습니다\n");
    }

    @Override
    public void printPlayerInfoAndTotalScore(Map<Player, TotalScore> playerAndTotalScore) {
        StringBuilder stringBuilder = new StringBuilder();
        playerAndTotalScore.forEach((player, totalScore) ->
                stringBuilder.append(player.getNameValue())
                        .append(CARD_SYMBOL)
                        .append(player.showCardHandNameValues())
                        .append(RESULT_SYMBOL)
                        .append(totalScore.getValue())
                        .append(LINE_BREAK)
        );
        System.out.println(stringBuilder);
    }

    @Override
    public void printGameResults(Map<Name, Results> playerNameAndResults) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(FINAL_RESULT);
        playerNameAndResults.forEach((playerName, results) ->
                stringBuilder.append(LINE_BREAK)
                        .append(playerName.getValue())
                        .append(": ")
                        .append(results.getValues())
        );
        System.out.println(stringBuilder);
    }
}
