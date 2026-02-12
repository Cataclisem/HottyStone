package hotstone.standard.winnerStrategy;

import hotstone.framework.*;
import hotstone.framework.card.Card;
import hotstone.framework.game.ReadOnlyGame;

public class WinnerStrategyHeroDeathFirstThenAttackSum implements WinnerStrategy {
    private final WinnerStrategy firstWinnerStrategy;
    private final WinnerStrategy lastWinnerStrategy;
    private WinnerStrategy strategyState;

    public WinnerStrategyHeroDeathFirstThenAttackSum() {
        this.firstWinnerStrategy = new WinnerStrategyHeroDeath();
        this.lastWinnerStrategy = new WinnerStrategyAttackSum();
        this.strategyState = null;
    }

    @Override
    public Player computeWinner(ReadOnlyGame game, Player currentWinner) {
        updateStrategy(game);
        return strategyState.computeWinner(game, currentWinner);
    }

    @Override
    public void onMinionAttack(ReadOnlyGame game, Player attackingPlayer, Card attackingCard) {
        updateStrategy(game);
        if (strategyState == lastWinnerStrategy)
            strategyState.onMinionAttack(game, attackingPlayer, attackingCard);
    }

    private void updateStrategy(ReadOnlyGame game) {
        if (game.getTurnNumber() < 12)
            strategyState = firstWinnerStrategy;
        else
            strategyState = lastWinnerStrategy;
    }
}