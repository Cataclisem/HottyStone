package hotstone.standard.factories;

import hotstone.framework.*;
import hotstone.framework.hero.HeroStrategy;
import hotstone.standard.deckStrategy.DeckStrategyFoodEffectsAndAttributes;
import hotstone.standard.heroStrategy.HeroStrategyFourRandom;
import hotstone.standard.manaStrategy.ManaStrategyLinearCapped;
import hotstone.standard.winnerStrategy.WinnerStrategyHeroDeath;

public class FactorySemi implements FactoryAbstract {
    private final ManaStrategy manaStrategy;
    private final DeckStrategyFoodEffectsAndAttributes deckStrategy;
    private final HeroStrategyFourRandom heroStrategy;
    private final WinnerStrategyHeroDeath winnerStrategy;

    public FactorySemi(RandomSelector randomSelector) {
        manaStrategy = new ManaStrategyLinearCapped();
        deckStrategy = new DeckStrategyFoodEffectsAndAttributes(randomSelector);
        heroStrategy = new HeroStrategyFourRandom(randomSelector);
        winnerStrategy = new WinnerStrategyHeroDeath();
    }

    @Override
    public WinnerStrategy getWinnerStrategy() {
        return winnerStrategy;
    }

    @Override
    public ManaStrategy getManaStrategy() {
        return manaStrategy;
    }

    @Override
    public HeroStrategy getHeroStrategy() {
        return heroStrategy;
    }

    @Override
    public DeckStrategy getDeckStrategy() {
        return deckStrategy;
    }
}
