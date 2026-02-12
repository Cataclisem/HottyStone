package hotstone.standard.heroStrategy;

import hotstone.framework.*;
import hotstone.framework.hero.HeroStrategy;
import hotstone.standard.GameConstants;
import hotstone.standard.StandardHero;
import hotstone.standard.effect.EffectDealDamageToRandomMinion;
import hotstone.standard.effect.EffectDoDamageToHero;
import hotstone.standard.effect.EffectDrawCardSovs;
import hotstone.standard.effect.EffectGiveAttackToRandomMinion;

public class HeroStrategyFourRandom implements HeroStrategy {


    private final RandomSelector ran;

    public HeroStrategyFourRandom(RandomSelector randomSelector) {
        ran = randomSelector;
    }

    @Override
    public StandardHero computeHero(Player player) {
        int r = ran.getRandomNumber(4);

        return switch (r) {
            case 0 -> {
                Effect powerThai = new EffectDoDamageToHero(2);
                yield new StandardHero(GameConstants.THAI_CHEF_HERO_TYPE, player, powerThai);
            }
            case 1 -> {
                Effect powerDanish = new EffectDrawCardSovs();
                yield new StandardHero(GameConstants.DANISH_CHEF_HERO_TYPE, player, powerDanish);
            }
            case 2 -> {
                Effect powerFrench = new EffectDealDamageToRandomMinion(ran, 2);
                yield new StandardHero(GameConstants.FRENCH_CHEF_HERO_TYPE, player, powerFrench);
            }
            case 3 -> {
                Effect powerItalian = new EffectGiveAttackToRandomMinion(ran, 2, player, player);
                yield new StandardHero(GameConstants.ITALIAN_CHEF_HERO_TYPE, player, powerItalian);
            }
            default -> null;
        };
    }
}
