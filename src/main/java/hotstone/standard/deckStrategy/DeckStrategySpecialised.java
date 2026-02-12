package hotstone.standard.deckStrategy;

import demo.DemoDeck;
import hotstone.framework.Player;
import hotstone.framework.card.Card;
import hotstone.standard.GameConstants;
import hotstone.standard.StandardCard;

import java.util.ArrayList;

import hotstone.framework.*;
import hotstone.framework.card.Card;
import hotstone.standard.GameConstants;
import hotstone.standard.StandardCard;
import thirdparty.CardPODO;
import thirdparty.PersonalDeckReader;

import java.util.ArrayList;

public class DeckStrategySpecialised implements DeckStrategy  {

    @Override
    public ArrayList<Card> giveDeck(Player player) {
        String deckType;
        if (player == Player.PEDDERSEN) { deckType = "Animals"; }
        else { deckType = "Norse"; }

        ArrayList<Card> playerDeck = new ArrayList<>();
        PersonalDeckReader demoCards = DemoDeck.main(deckType);
        for (CardPODO card : demoCards) {
            playerDeck.add(new StandardCard(card.name(),card.mana(),card.attack(),card.health(), player));
            playerDeck.add(new StandardCard(card.name(),card.mana(),card.attack(),card.health(), player));
        }
        return playerDeck;
    }

    @Override
    public int computeStartingHandSize(Player player) { return 3; }
}

