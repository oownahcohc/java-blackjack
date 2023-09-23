package gmbs.domain.dto;

import gmbs.domain.card.state.finish.Finish;
import gmbs.domain.participants.vo.Name;

import java.util.Map;

public class GameResultDto {

    private final Finish dealerFinal;
    private final Map<Name, Finish> playersFinal;

    public GameResultDto(Finish dealerFinal, Map<Name, Finish> playersFinal) {
        this.dealerFinal = dealerFinal;
        this.playersFinal = playersFinal;
    }

    public Finish getDealerFinal() {
        return dealerFinal;
    }

    public Map<Name, Finish> getPlayersFinal() {
        return playersFinal;
    }
}
