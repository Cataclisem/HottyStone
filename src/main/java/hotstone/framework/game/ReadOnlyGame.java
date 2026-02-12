package hotstone.framework.game;

import hotstone.framework.Player;
import hotstone.framework.card.Card;
import hotstone.framework.hero.Hero;

public interface ReadOnlyGame {
    Player getPlayerInTurn();

    Hero getHero(Player who);

    Player getWinner();

    int getTurnNumber();

    int getDeckSize(Player who);

    Card getCardInHand(Player who, int indexInHand);

    Iterable<? extends Card> getHand(Player who);

    int getHandSize(Player who);

    Card getCardInField(Player who, int indexInField);

    Iterable<? extends Card> getField(Player who);

    int getFieldSize(Player who);
}
