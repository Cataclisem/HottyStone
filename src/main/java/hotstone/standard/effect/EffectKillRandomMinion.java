package hotstone.standard.effect;

import hotstone.framework.*;
import hotstone.framework.card.MutableCard;
import hotstone.framework.game.MutableGame;

public class EffectKillRandomMinion implements Effect {
    private final RandomSelector randomSelector;
    public EffectKillRandomMinion(RandomSelector randomSelector){
        this.randomSelector = randomSelector;
    }
    @Override
    public void useEffect(Player player, MutableGame game) {
        Player opponent = Utility.computeOpponent(player);
        int random_int = randomSelector.getRandomNumber(game.getFieldSize(opponent));
        MutableCard enemyMinion = game.getCardInField(opponent,random_int);
        game.inflictDamageOnCard(player,enemyMinion,enemyMinion.getHealth());
    }

    @Override
    public String getDescription() {
        return "Kill-opp-M";
    }
}
