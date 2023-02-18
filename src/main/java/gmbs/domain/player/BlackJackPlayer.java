package gmbs.domain.player;

import gmbs.domain.card.vo.TotalScore;
import gmbs.domain.game.result.Result;
import gmbs.domain.player.name.vo.Name;
import gmbs.domain.state.CardState;

import java.util.Objects;

public abstract class BlackJackPlayer implements Player {

    protected final Name name;
    protected final CardState cardState;

    protected BlackJackPlayer(final Name name, final CardState cardState) {
        this.name = name;
        this.cardState = cardState;
    }

    @Override
    public boolean canDraw() {
        return cardState.canHit();
    }

    @Override
    public boolean isBust() {
        return cardState.isBust();
    }

    @Override
    public TotalScore getTotalScore() {
        return cardState.getTotalScore();
    }

    @Override
    public Result getPlayerResult(CardState another) {
        return cardState.getResult(another);
    }

    @Override
    public String getNameValue() {
        return name.getValue();
    }

    public Name getName() {
        return name;
    }

    public CardState getCardState() {
        return cardState;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final BlackJackPlayer that = (BlackJackPlayer) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
