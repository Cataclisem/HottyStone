package hotstone.standard.deckStrategy;

import hotstone.framework.*;
import hotstone.framework.card.Card;
import hotstone.standard.GameConstants;
import hotstone.standard.StandardCard;

import java.util.ArrayList;

public class DeckStrategyCincoOnly implements DeckStrategy {
    @Override
    public ArrayList<Card> giveDeck(Player player) {
        ArrayList<Card> playerDeck = new ArrayList<>();

        for (int i = 0; i < 7; i++)
            playerDeck.add(new StandardCard(GameConstants.CINCO_CARD, 3, 5, 1, player));

        return playerDeck;
    }

    @Override
    public int computeStartingHandSize(Player player) {
        return 3;
    }
}
