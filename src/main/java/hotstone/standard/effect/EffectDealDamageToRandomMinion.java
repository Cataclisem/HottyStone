package hotstone.standard.effect;

import hotstone.framework.*;
import hotstone.framework.card.MutableCard;
import hotstone.framework.game.MutableGame;

public class EffectDealDamageToRandomMinion implements Effect {
    private final int damageInflicted;
    private final RandomSelector randomSelector;

    public EffectDealDamageToRandomMinion(RandomSelector randomSelector, int damageInflicted) {
        this.randomSelector = randomSelector;
        this.damageInflicted = damageInflicted;
    }

    @Override
    public void useEffect(Player player, MutableGame game) {
        Player targetPlayer = Utility.computeOpponent(player);

        if (game.getFieldSize(targetPlayer) == 0)
            return;
        int random_int = randomSelector.getRandomNumber(game.getFieldSize(targetPlayer));
        MutableCard minion = game.getCardInField(targetPlayer, random_int);
        game.inflictDamageOnCard(player, minion, damageInflicted);
    }

    @Override
    public String getDescription() {
        String res = "ATK_Opp_M(";
        res += -damageInflicted;
        res += ")";

        return res;
    }
}
