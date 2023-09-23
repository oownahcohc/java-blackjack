package gmbs.domain.participants;

import gmbs.domain.card.state.finish.Finish;
import gmbs.domain.card.vo.Card;
import gmbs.domain.participants.vo.Name;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Players {

    private final List<Player> values;
    private int currentTurnPlayerIndex;

    public Players(List<Player> values) {
        this.values = values;
        this.currentTurnPlayerIndex = 0;
    }

    public boolean isAllPlayerFinish() {
        return currentTurnPlayerIndex >= values.size();
    }

    public Player currentPlayerDraw(Card card) {
        Player player = getCurrentPlayer();
        Player playerAfterDraw = (Player) player.drawCard(card);
        values.set(currentTurnPlayerIndex, playerAfterDraw);
        return playerAfterDraw;
    }

    public void passTurnToNextPlayer() {
        Player player = getCurrentPlayer();
        Player stopPlayer = (Player) player.turnEnd();
        values.set(currentTurnPlayerIndex, stopPlayer);
        currentTurnPlayerIndex++;
    }

    public Player getCurrentPlayer() {
        return values.get(currentTurnPlayerIndex);
    }

    public Map<Name, Finish> getAllPlayersFinalCardStateAfterGameEnd() {
        return values.stream()
                .collect(Collectors.toUnmodifiableMap(
                        Player::notifyName,
                        Player::getFinalCardStateAfterGameEnd
                ));
    }

    public List<Player> getValues() {
        return values;
    }
}
