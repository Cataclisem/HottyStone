package hotstone.standard.effect;

import hotstone.framework.game.MutableGame;
import hotstone.framework.Player;
import hotstone.framework.Effect;

public class EffectDoDamageToHero implements Effect {
    private final int damage;

    public EffectDoDamageToHero(int damage) {
        this.damage = damage;
    }

    @Override
    public void useEffect(Player player, MutableGame game) {
        game.inflictDamageOnOpponentHero(player, damage);
    }

    @Override
    public String getDescription() {
        String res = "ATK_Opp_H(";
        res += -damage;
        res += ")";
        return res;
    }
}
