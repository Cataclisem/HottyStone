package hotstone.standard.effect;

import hotstone.framework.Effect;
import hotstone.framework.game.MutableGame;
import hotstone.framework.Player;

public class EffectDrawRandomCard implements Effect {
    @Override
    public void useEffect(Player player, MutableGame game) {
        game.drawCard();
    }

    @Override
    public String getDescription() {
        return "Draw-Card";
    }
}
