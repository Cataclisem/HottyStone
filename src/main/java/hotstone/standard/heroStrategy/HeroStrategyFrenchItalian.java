package hotstone.standard.heroStrategy;

import hotstone.framework.*;
import hotstone.framework.hero.HeroStrategy;
import hotstone.standard.GameConstants;
import hotstone.standard.StandardHero;
import hotstone.standard.effect.EffectDealDamageToRandomMinion;
import hotstone.standard.effect.EffectGiveAttackToRandomMinion;

public class HeroStrategyFrenchItalian implements HeroStrategy {
    private final Effect powerFrench;
    private final Effect powerItalian;

    public HeroStrategyFrenchItalian(RandomSelector randomSelector) {
        powerFrench = new EffectDealDamageToRandomMinion(randomSelector, 2);
        powerItalian = new EffectGiveAttackToRandomMinion(randomSelector, 2, Player.PEDDERSEN, Player.PEDDERSEN);
    }

    @Override
    public StandardHero computeHero(Player player) {
        if (player == Player.FINDUS)
            return new StandardHero(GameConstants.FRENCH_CHEF_HERO_TYPE, player, powerFrench);
        else
            return new StandardHero(GameConstants.ITALIAN_CHEF_HERO_TYPE, player, powerItalian);
    }
}
