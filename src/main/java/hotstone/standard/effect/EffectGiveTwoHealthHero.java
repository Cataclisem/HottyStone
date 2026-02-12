package hotstone.standard.effect;

import hotstone.framework.Effect;
import hotstone.framework.game.MutableGame;
import hotstone.framework.Player;

public class EffectGiveTwoHealthHero implements Effect {
    @Override
    public void useEffect(Player player, MutableGame game) {
        game.healHero(player, 2);
    }

    @Override
    public String getDescription() {
        return "HP_H(2)";
    }
}
