package gmbs.view.output;

import gmbs.domain.card.state.vo.TotalScore;
import gmbs.domain.game.vo.Profit;
import gmbs.domain.participants.Participants;
import gmbs.domain.participants.Player;
import gmbs.domain.participants.vo.Name;

import java.util.List;
import java.util.Map;

public class OutputConsoleImpl implements OutputConsole {

    private static final String CARD_SYMBOL = "카드 : ";
    private static final String RESULT_SYMBOL = " - 결과 : ";
    private static final String LINE_BREAK = "\n";
    private static final String FINAL_PROFIT = "### 최종 수익 ###";

    @Override
    public void printInputGamerNames() {
        System.out.println("게임에 참여할 사람의 이름을 입력하세요");
    }

    @Override
    public void printInputBettingAmount(Player player) {
        System.out.printf("%s의 배팅 금액을 입력하세요", player.notifyName().getValue());
    }

    @Override
    public void printPlayerInitInfo(List<Participants> allParticipants) {
        StringBuilder stringBuilder = new StringBuilder();
        allParticipants.forEach(participants ->
                stringBuilder.append(participants.notifyName().getValue())
                        .append(": ")
                        .append(participants.showCardHandNameValues())
                        .append(LINE_BREAK));
        System.out.println(stringBuilder);
    }

    @Override
    public void printQuestionForDraw(Player player) {
        System.out.printf("%s는 한장의 카드를 더 받겠습니까?(예는 y, 아니오는 n)%n", player.notifyName().getValue());
    }

    @Override
    public void printPlayerInfo(Participants participants) {
        String stringBuilder = participants.notifyName().getValue() +
                CARD_SYMBOL +
                participants.showCardHandNameValues() +
                LINE_BREAK;
        System.out.println(stringBuilder);
    }

    @Override
    public void printDealerInfo() {
        System.out.println("딜러는 16이하라 한장의 카드를 더 받았습니다\n");
    }

    @Override
    public void printPlayerInfoAndTotalScore(Map<Participants, TotalScore> participantAndTotalScore) {
        StringBuilder stringBuilder = new StringBuilder();
        participantAndTotalScore.forEach((participants, totalScore) ->
                stringBuilder.append(participants.notifyName().getValue())
                        .append(CARD_SYMBOL)
                        .append(participants.showCardHandNameValues())
                        .append(RESULT_SYMBOL)
                        .append(totalScore.getValue())
                        .append(LINE_BREAK)
        );
        System.out.println(stringBuilder);
    }

    @Override
    public void printGameResults(Map<Name, Profit> playerNameAndProfits) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(FINAL_PROFIT);
        playerNameAndProfits.forEach((playerName, profit) ->
                stringBuilder.append(LINE_BREAK)
                        .append(playerName.getValue())
                        .append(": ")
                        .append(profit.getValue())
        );
        System.out.println(stringBuilder);
    }
}
