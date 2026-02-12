package hotstone.standard;

import hotstone.framework.Effect;
import hotstone.framework.Player;
import hotstone.framework.card.Card;
import hotstone.framework.card.CardAttribute;
import hotstone.framework.card.MutableCard;
import hotstone.standard.effect.EffectNone;

import java.util.UUID;

public class StandardCard implements Card, MutableCard {
    private final String id;

    private final String name;
    private final int manaCost;
    private final Player owner;
    private final Effect effect;
    private final CardAttribute attribute;

    private int attack;
    private int health;
    private boolean activeness;

    public StandardCard(String name, int manaCost, int attack, int health, Player owner) {
        this.name = name;
        this.manaCost = manaCost;
        this.attack = attack;
        this.health = health;
        this.owner = owner;
        activeness = false;
        effect = new EffectNone();
        attribute = CardAttribute.NONE;

        id = UUID.randomUUID().toString();
    }

    public StandardCard(String name, int manaCost, int attack, int health, Player owner, Effect effect) {
        this.name = name;
        this.manaCost = manaCost;
        this.attack = attack;
        this.health = health;
        this.owner = owner;
        activeness = false;
        this.effect = effect;
        attribute = CardAttribute.NONE;

        id = UUID.randomUUID().toString();
    }

    public StandardCard(String name, int manaCost, int attack, int health, Player owner, CardAttribute attribute) {
        this.name = name;
        this.manaCost = manaCost;
        this.attack = attack;
        this.health = health;
        this.owner = owner;
        activeness = false;
        this.effect = new EffectNone();
        this.attribute = attribute;

        id = UUID.randomUUID().toString();
    }

    public StandardCard(String name, int manaCost, int attack, int health, Player owner, Effect effect, CardAttribute attribute) {
        this.name = name;
        this.manaCost = manaCost;
        this.attack = attack;
        this.health = health;
        this.owner = owner;
        activeness = false;
        this.effect = effect;
        this.attribute = attribute;

        id = UUID.randomUUID().toString();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getManaCost() {
        return manaCost;
    }

    @Override
    public int getAttack() {
        return attack;
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public void setHealth(int newHealth ) {
        health = newHealth;
    }

    @Override
    public boolean isActive() {return activeness;}

    @Override
    public void setActive(Boolean active){activeness = active;}

    @Override
    public Player getOwner() {
        return owner;
    }

    @Override
    public String getCardEffectDescription() {
        return effect.getDescription();
    }

    @Override
    public Effect getCardEffect() {
        return effect;
    }

    @Override
    public CardAttribute getAttribute() {
        return attribute;
    }

    @Override
    public void setAttack(int newAttack) {
        attack = newAttack;
    }

    @Override
    public String getID() {
        return id;
    }
}
