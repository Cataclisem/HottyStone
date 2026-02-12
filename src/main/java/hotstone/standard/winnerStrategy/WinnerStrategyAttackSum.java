package hotstone.standard.winnerStrategy;

import hotstone.framework.*;
import hotstone.framework.card.Card;
import hotstone.framework.game.ReadOnlyGame;

public class WinnerStrategyAttackSum implements WinnerStrategy {
    private int attackSumFindus;
    private int attackSumPeddersen;

    public WinnerStrategyAttackSum() {
        attackSumFindus = 0;
        attackSumPeddersen = 0;
    }

    @Override
    public Player computeWinner(ReadOnlyGame game, Player currentWinner) {
        if (currentWinner != null)
            return currentWinner;

        if (attackSumFindus > 7)
            return Player.FINDUS;
        else if (attackSumPeddersen > 7)
            return Player.PEDDERSEN;
        else
            return null;
    }

    @Override
    public void onMinionAttack(ReadOnlyGame game, Player attackingPlayer, Card attackingCard) {
        if (attackingPlayer == Player.FINDUS)
            attackSumFindus += attackingCard.getAttack();
        else
            attackSumPeddersen += attackingCard.getAttack();
    }
}
