package hotstone.standard;

import hotstone.framework.Effect;
import hotstone.framework.Player;
import hotstone.framework.hero.Hero;
import hotstone.framework.hero.MutableHero;

import java.util.UUID;

public class StandardHero implements Hero, MutableHero {
    private final String id;

    private final String type;
    private final Player owner;
    private final Effect power;

    private int mana = 2;
    private int health = 21;
    private boolean powerActive = true;

    public StandardHero(String type, Player owner, Effect heroPower) {
        this.type = type;
        this.owner = owner;
        this.power = heroPower;

        id = UUID.randomUUID().toString();
    }

    @Override
    public int getMana() {
        return mana;
    }

    @Override
    public void setMana(int newMana) {
        mana = newMana;
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public void setHealth(int newHealth) {
        health = newHealth;
    }

    @Override
    public boolean canUsePower() {
        return powerActive;
    }

    @Override
    public void setPowerActive(boolean powerActive) {
        this.powerActive = powerActive;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public Player getOwner() {
        return owner;
    }

    @Override
    public String getPowerDesc() {
        return power.getDescription();
    }

    @Override
    public Effect getPower() {
        return power;
    }

    @Override
    public String getID() {
        return id;
    }
}
