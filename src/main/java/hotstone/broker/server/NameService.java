package hotstone.broker.server;

import hotstone.framework.card.Card;
import hotstone.framework.hero.Hero;

public interface NameService {
    void putCard(String cardID, Card card);

    Card getCard(String cardID);

    void putHero(String heroID, Hero hero);

    Hero getHero(String heroID);
}
