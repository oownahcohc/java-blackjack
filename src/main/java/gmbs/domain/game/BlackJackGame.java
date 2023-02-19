package gmbs.domain.game;

import gmbs.domain.card.CardDeck;
import gmbs.domain.card.vo.TotalScore;
import gmbs.domain.game.result.Results;
import gmbs.domain.player.*;
import gmbs.domain.player.name.Names;
import gmbs.domain.player.name.vo.Name;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BlackJackGame {

    private final CardDeck cardDeck;
    private final Gamers gamers;
    private Dealer dealer;

    public BlackJackGame(final CardDeck cardDeck, final Gamers gamers, final Dealer dealer) {
        this.cardDeck = cardDeck;
        this.gamers = gamers;
        this.dealer = dealer;
    }

    public static BlackJackGame create(final Names playerNames) {
        CardDeck cardDeck = CardDeck.createShuffled();
        List<Gamer> gamers = playerNames.getValues()
                .stream()
                .map(name -> Gamer.of(name, cardDeck.drawTwo()))
                .collect(Collectors.toList());
        return new BlackJackGame(cardDeck,
                Gamers.from(gamers),
                Dealer.from(cardDeck.drawTwo())
        );
    }

    public boolean isAllGamerTurnEnd() {
        return gamers.isAllTurnEnd();
    }

    public Gamer getCurrentTurnGamer() {
        return gamers.getCurrentTurnPlayer();
    }

    public Gamer keepTurnAndDraw() {
        return gamers.drawCurrentPlayer(cardDeck.drawOne());
    }

    public Gamer getNextTurnGamer() {
        Gamer currentTurnGamer = getCurrentTurnGamer();
        gamers.passTurnToNextPlayer();
        return currentTurnGamer;
    }

    public boolean isDealerTurnEnd() {
        return !dealer.canDraw();
    }

    public void dealerDraw() {
        dealer = dealer.drawCard(cardDeck.drawOne());
    }

    public void dealerStand() {
        if (!dealer.isBust()) {
            dealer = dealer.stand();
        }
    }

    public Map<Player, TotalScore> getPlayerInfoAndTotalScore() {
        return getAllPlayerInfo().stream()
                .collect(Collectors.toUnmodifiableMap(
                        player -> player,
                        Player::getTotalScore)
                );
    }

    public List<Player> getAllPlayerInfo() {
        List<Player> allPlayers = new ArrayList<>();
        allPlayers.add(dealer);
        allPlayers.addAll(gamers.getValues());
        return allPlayers;
    }

    public Map<Name, Results> getAllResults() {
        Map<Name, Results> playerNameAndGameResults = new LinkedHashMap<>();

        Results dealerResults = dealer.getDealerResults(gamers.getValues());
        Map<Name, Results> gamerResults = gamers.getGamerResults(dealer);

        playerNameAndGameResults.put(dealer.getName(), dealerResults);
        playerNameAndGameResults.putAll(gamerResults);

        return playerNameAndGameResults;
    }
}
