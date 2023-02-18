package gmbs.domain.player;

import gmbs.domain.card.vo.Card;

import java.util.Collections;
import java.util.List;

public class Gamers {

    private final List<Gamer> values;
    private int currentTurnPlayerIndex;

    private Gamers(final List<Gamer> values) {
        this.values = values;
    }

    public static Gamers from(final List<Gamer> gamers) {
        return new Gamers(gamers);
    }

    public boolean isAllTurnEnd() {
        return values.size() == currentTurnPlayerIndex;
    }

    public Gamer drawCurrentPlayer(Card card) {
        validateAllTurnIsOver();
        Gamer currentTurnPlayer = getCurrentTurnPlayer();
        if (currentTurnPlayer.canDraw()) {
            Gamer currentTurnPlayerAfterDraw = currentTurnPlayer.drawCard(card);
            values.set(currentTurnPlayerIndex, currentTurnPlayerAfterDraw);
            return currentTurnPlayerAfterDraw;
        }
        return currentTurnPlayer;
    }

    public void passTurnToNextPlayer() {
        validateAllTurnIsOver();
        Gamer currentTurnPlayer = getCurrentTurnPlayer();
        if (!currentTurnPlayer.isBust()) {
            values.set(currentTurnPlayerIndex, currentTurnPlayer.stand());
        }
        currentTurnPlayerIndex++;
    }

    private void validateAllTurnIsOver() {
        if (values.size() < currentTurnPlayerIndex) {
            throw new IllegalStateException("[ERROR] 모든 턴이 종료되었습니다");
        }
    }

    public Gamer getCurrentTurnPlayer() {
        return values.get(currentTurnPlayerIndex);
    }

    public List<Gamer> getValues() {
        return Collections.unmodifiableList(values);
    }
}
