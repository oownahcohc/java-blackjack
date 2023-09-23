package gmbs.domain.game;

import gmbs.domain.card.state.vo.TotalScore;
import gmbs.domain.card.vo.CardDeck;
import gmbs.domain.dto.GameResultDto;
import gmbs.domain.participants.Dealer;
import gmbs.domain.participants.Participants;
import gmbs.domain.participants.Player;
import gmbs.domain.participants.Players;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BlackJackGame {

    private final CardDeck cardDeck;
    private final Players players;
    private Dealer dealer;

    public BlackJackGame(CardDeck cardDeck, Dealer dealer, Players players) {
        this.cardDeck = cardDeck;
        this.dealer = dealer;
        this.players = players;
    }

    public Player showCurrentPlayer() {
        return players.getCurrentPlayer();
    }

    public boolean isAllPlayersTurnEnd() {
        return players.isAllPlayerFinish();
    }

    public Player keepTurnAndDraw() {
        return players.currentPlayerDraw(cardDeck.drawOne());
    }

    public Player finishTurnAndPassToNextPlayer() {
        Player stopPlayer = players.getCurrentPlayer();
        players.passTurnToNextPlayer();
        return stopPlayer;
    }

    public boolean isDealerTurnEnd() {
        return !dealer.isMustDraw();
    }

    public void dealerDraw() {
        dealer = (Dealer) dealer.drawCard(cardDeck.drawOne());
    }

    public void finishDealerTurn() {
        dealer.turnEnd();
    }

    // 게임 결과 -> player 한테 딜러 정보 주면서 물어봄 -> 만 알면 되지 않나?
    // -> 근데 CardHand 가 의존하고 있는 CardState 가 Finish 가 아니기 때문에 문제
    // -> Finish 상태여야 비교가 가능하기 때문에, 상위 인터페이스인 CardState 로는 비교가 불가능
    // -> 설계를 다 바꿔야하는 문제
    public GameResultDto showGameResult() {
        return new GameResultDto(
                dealer.getFinalCardStateAfterGameEnd(),
                players.getAllPlayersFinalCardStateAfterGameEnd()
        );
    }

    public Map<Participants, TotalScore> getPlayerInfoAndTotalScore() {
        return getAllPlayerInfo().stream()
                .collect(Collectors.toUnmodifiableMap(
                        participants -> participants,
                        participants -> participants.getFinalCardStateAfterGameEnd().getTotalScore()
                ));
    }

    public List<Participants> getAllPlayerInfo() {
        List<Participants> allParticipants = new ArrayList<>();
        allParticipants.add(dealer);
        allParticipants.addAll(players.getValues());
        return allParticipants;
    }
}
