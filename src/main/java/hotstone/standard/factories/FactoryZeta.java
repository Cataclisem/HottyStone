package hotstone.standard.factories;

import hotstone.framework.*;
import hotstone.framework.hero.HeroStrategy;
import hotstone.standard.deckStrategy.DeckStrategyCincoOnly;
import hotstone.standard.heroStrategy.HeroStrategyBaby;
import hotstone.standard.manaStrategy.ManaStrategyLinear;
import hotstone.standard.winnerStrategy.WinnerStrategyHeroDeathFirstThenAttackSum;

public class FactoryZeta implements FactoryAbstract {
    private final DeckStrategyCincoOnly deckStrategy;
    private final HeroStrategyBaby heroStrategy;
    private final WinnerStrategyHeroDeathFirstThenAttackSum winnerStrategy;
    private final ManaStrategy manaStrategy;

    public FactoryZeta() {
        manaStrategy = new ManaStrategyLinear();
        deckStrategy = new DeckStrategyCincoOnly();
        heroStrategy = new HeroStrategyBaby();
        winnerStrategy = new WinnerStrategyHeroDeathFirstThenAttackSum();
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
