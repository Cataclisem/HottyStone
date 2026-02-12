package hotstone.standard.deckStrategy;

import hotstone.framework.*;
import hotstone.framework.card.Card;
import hotstone.standard.GameConstants;
import hotstone.standard.StandardCard;

import java.util.ArrayList;

public class DeckStrategySpanish implements DeckStrategy {
    @Override
    public ArrayList<Card> giveDeck(Player player) {
        ArrayList<Card> playerDeck = new ArrayList<>();

        playerDeck.add(new StandardCard(GameConstants.UNO_CARD, 1, 1, 1, player));
        playerDeck.add(new StandardCard(GameConstants.DOS_CARD, 2, 2, 2, player));
        playerDeck.add(new StandardCard(GameConstants.TRES_CARD, 3, 3, 3, player));
        playerDeck.add(new StandardCard(GameConstants.CUATRO_CARD, 2, 3, 1, player));
        playerDeck.add(new StandardCard(GameConstants.CINCO_CARD, 3, 5, 1, player));
        playerDeck.add(new StandardCard(GameConstants.SEIS_CARD, 2, 1, 3, player));
        playerDeck.add(new StandardCard(GameConstants.SIETE_CARD, 3, 2, 4, player));
        return playerDeck;
    }

    @Override
    public int computeStartingHandSize(Player player) {
        return 3;
    }
}
