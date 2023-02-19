package gmbs.domain.player;

import gmbs.domain.card.vo.Card;
import gmbs.domain.game.result.Results;
import gmbs.domain.player.name.vo.Name;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    public Map<Name, Results> getGamerResults(Dealer dealer) {
        return getValues().stream()
                .collect(Collectors.toUnmodifiableMap(
                        Gamer::getName,
                        gamer -> Results.from(List.of(
                                gamer.getPlayerResult(dealer.getCardState())
                        ))
                ));
    }

    public List<Gamer> getValues() {
        return Collections.unmodifiableList(values);
    }
}
