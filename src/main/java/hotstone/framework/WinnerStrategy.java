package hotstone.framework;

import hotstone.framework.card.Card;
import hotstone.framework.game.ReadOnlyGame;

public interface WinnerStrategy {
    Player computeWinner(ReadOnlyGame game, Player currentWinner);

    void onMinionAttack(ReadOnlyGame game, Player attackingPlayer, Card attackingCard);
}
