package hotstone.standard.effect;

import hotstone.framework.*;
import hotstone.framework.card.MutableCard;
import hotstone.framework.game.MutableGame;

public class EffectGiveAttackToRandomMinion implements Effect {
    private final Player playingPlayer;
    private final int attack;
    private final Player targetPlayer;
    private final RandomSelector randomSelector;


    public EffectGiveAttackToRandomMinion(RandomSelector randomSelector, int attack, Player playingPlayer, Player targetPlayer){
        this.randomSelector = randomSelector;
        this.attack = attack;
        this.playingPlayer = playingPlayer;
        this.targetPlayer = targetPlayer;
    }
    @Override
    public void useEffect(Player player, MutableGame game) {
        if (game.getFieldSize(targetPlayer) == 0)
            return;
        int random_int = randomSelector.getRandomNumber(game.getFieldSize(targetPlayer));
        MutableCard minion = game.getCardInField(targetPlayer, random_int);
        game.setCardAttack(minion, minion.getAttack() + attack);
    }

    @Override
    public String getDescription() {
        if (playingPlayer == targetPlayer)
            return "ATK_UP_M("+attack+")";
        else
            return "ATK_UP_Opp_M("+attack+")";
    }
}
