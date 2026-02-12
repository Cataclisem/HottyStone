package hotstone.framework.game;

import hotstone.framework.card.Card;
import hotstone.framework.card.MutableCard;
import hotstone.framework.Player;
import hotstone.framework.hero.MutableHero;

import java.util.ArrayList;

public interface MutableGame extends Game {
    ArrayList<Card> getHand(Player who);

    ArrayList<Card> getField(Player who);

    ArrayList<Card> getDeck(Player who);

    void inflictDamageOnCard(Player playerAttacking, MutableCard defendingCard, int damage);

    void setCardAttack(MutableCard card, int newDamage);

    void healHero(Player player, int heal);

    void inflictDamageOnOpponentHero(Player playerAttacking, int damage);

    void drawCard();

    MutableCard getCardInField(Player who, int indexInField);

    MutableHero getHero(Player who);
}
