package hotstone.standard.effect;

import hotstone.framework.*;
import hotstone.framework.card.Card;
import hotstone.framework.game.MutableGame;
import hotstone.standard.GameConstants;
import hotstone.standard.StandardCard;

public class EffectDrawCardSovs implements Effect {
    @Override
    public void useEffect(Player player, MutableGame game) {
        //game.getField(player).add(new StandardCard(GameConstants.SOVS_CARD, 0, 1, 1, player));
        Card sovs = new StandardCard(GameConstants.SOVS_CARD, 0, 1, 1, player);
        game.playCard(player, sovs);
    }

    @Override
    public String getDescription() {
        return "Field-Sovs";
    }
}
