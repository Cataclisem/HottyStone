package hotstone.standard.heroStrategy;

import hotstone.framework.*;
import hotstone.framework.hero.HeroStrategy;
import hotstone.standard.GameConstants;
import hotstone.standard.StandardHero;
import hotstone.standard.effect.EffectDoDamageToHero;
import hotstone.standard.effect.EffectDrawCardSovs;

public class HeroStrategyThaiDanish implements HeroStrategy {
    private final Effect powerThai;
    private final Effect powerDanish;

    public HeroStrategyThaiDanish() {
        powerThai = new EffectDoDamageToHero(2);
        powerDanish = new EffectDrawCardSovs();
    }

    @Override
    public StandardHero computeHero(Player player) {
        if (player == Player.FINDUS)
            return new StandardHero(GameConstants.THAI_CHEF_HERO_TYPE, player, powerThai);
        else
            return new StandardHero(GameConstants.DANISH_CHEF_HERO_TYPE, player, powerDanish);
    }
}
