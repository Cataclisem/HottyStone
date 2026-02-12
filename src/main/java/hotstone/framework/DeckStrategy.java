package hotstone.framework;

import hotstone.framework.card.Card;

import java.util.ArrayList;

public interface DeckStrategy {
    ArrayList<Card> giveDeck(Player player);
    int computeStartingHandSize(Player player);
}
