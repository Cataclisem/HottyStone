package hotstone.standard.heroStrategy;

import hotstone.framework.*;
import hotstone.framework.hero.HeroStrategy;
import hotstone.standard.GameConstants;
import hotstone.standard.StandardHero;
import hotstone.standard.effect.EffectNone;

public class HeroStrategyBaby implements HeroStrategy {
    private final Effect power;

    public HeroStrategyBaby() {
        power = new EffectNone();
    }


    @Override
    public StandardHero computeHero(Player player) {
        return new StandardHero(GameConstants.BABY_HERO_TYPE, player, power);
    }
}
