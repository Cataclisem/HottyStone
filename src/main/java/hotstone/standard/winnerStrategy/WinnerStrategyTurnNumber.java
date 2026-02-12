package hotstone.standard.winnerStrategy;

import hotstone.framework.*;
import hotstone.framework.card.Card;
import hotstone.framework.game.ReadOnlyGame;

public class WinnerStrategyTurnNumber implements WinnerStrategy {
    @Override
    public Player computeWinner(ReadOnlyGame game, Player currentWinner) {
        if (game.getTurnNumber() >= 7)
            return Player.FINDUS;
        return null;
    }

    @Override
    public void onMinionAttack(ReadOnlyGame game, Player attackingPlayer, Card attackingCard) { }
}
