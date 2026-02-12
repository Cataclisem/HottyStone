package hotstone.standard.effect;

import hotstone.framework.*;
import hotstone.framework.game.MutableGame;
import hotstone.framework.hero.MutableHero;

public class EffectPlusMana implements Effect {
    private final int manaAmount;

    public EffectPlusMana(int manaAmount) {
        this.manaAmount = manaAmount;
    }

    @Override
    public void useEffect(Player player, MutableGame game) {
        MutableHero hero = game.getHero(player);
        int lastMana = hero.getMana();
        hero.setMana(lastMana + manaAmount);
    }

    @Override
    public String getDescription() {
        return "+" + manaAmount + "_Mana";
    }
}
