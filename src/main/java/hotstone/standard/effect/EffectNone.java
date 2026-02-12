package hotstone.standard.effect;

import hotstone.framework.game.MutableGame;
import hotstone.framework.Player;
import hotstone.framework.Effect;

public class EffectNone implements Effect {
    @Override
    public void useEffect(Player player, MutableGame game) {}

    @Override
    public String getDescription() {
        return "Does-nothing";
    }
}
