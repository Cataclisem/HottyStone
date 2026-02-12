package hotstone.framework.card;

import hotstone.framework.Effect;

public interface MutableCard extends Card {
    void setHealth(int newHealth);

    void setActive(Boolean active);

    void setAttack(int newAttack);

    /** Get the effect of this card
     *
     * @return card effect
     */
    Effect getCardEffect();
}
