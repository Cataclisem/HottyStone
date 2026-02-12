package hotstone.standard.winnerStrategy;

import hotstone.framework.*;
import hotstone.framework.card.Card;
import hotstone.framework.game.ReadOnlyGame;

public class WinnerStrategyHeroDeath implements WinnerStrategy {
    @Override
    public Player computeWinner(ReadOnlyGame game, Player currentWinner) {
        if (currentWinner != null)
            return currentWinner;

        if (game.getHero(Player.FINDUS).getHealth() <= 0 && game.getHero(Player.PEDDERSEN).getHealth() > 0)
            return Player.PEDDERSEN;
        else if (game.getHero(Player.FINDUS).getHealth() > 0 && game.getHero(Player.PEDDERSEN).getHealth() <= 0)
            return Player.FINDUS;
        else
            return null;
    }

    @Override
    public void onMinionAttack(ReadOnlyGame Game, Player attackingPlayer, Card attackingCard) { }
}
