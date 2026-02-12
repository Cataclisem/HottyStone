package hotstone.broker.server;

import hotstone.framework.card.Card;
import hotstone.framework.hero.Hero;

import java.util.HashMap;

public class StandardNameService implements NameService {
    private final HashMap<String, Card> cardMap;
    private final HashMap<String, Hero> heroMap;

    public StandardNameService() {
        cardMap = new HashMap<>();
        heroMap = new HashMap<>();
    }

    @Override
    public void putCard(String cardID, Card card) {
        cardMap.put(cardID, card);
    }

    @Override
    public Card getCard(String cardID) {
        return cardMap.get(cardID);
    }

    @Override
    public void putHero(String heroID, Hero hero) {
        heroMap.put(heroID, hero);
    }

    @Override
    public Hero getHero(String heroID) {
        return heroMap.get(heroID);
    }
}
