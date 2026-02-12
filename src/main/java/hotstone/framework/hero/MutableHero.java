package hotstone.framework.hero;

import hotstone.framework.Effect;

public interface MutableHero extends Hero{
    void setMana(int newMana);

    void setHealth(int newHealth);

    void setPowerActive(boolean powerActive);
    Effect getPower();


}
