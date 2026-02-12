package hotstone.standard.factories;

import hotstone.framework.*;
import hotstone.framework.hero.HeroStrategy;
import hotstone.standard.deckStrategy.DeckStrategyFoods;
import hotstone.standard.heroStrategy.HeroStrategyBaby;
import hotstone.standard.manaStrategy.ManaStrategyFixed;
import hotstone.standard.winnerStrategy.WinnerStrategyTurnNumber;

public class FactoryDelta implements FactoryAbstract {
    private final ManaStrategyFixed manaStrategy;
    private final DeckStrategyFoods deckStrategy;
    private final HeroStrategyBaby heroStrategy;
    private final WinnerStrategyTurnNumber winnerStrategy;

    public FactoryDelta() {
        manaStrategy = new ManaStrategyFixed();
        deckStrategy = new DeckStrategyFoods();
        heroStrategy = new HeroStrategyBaby();
        winnerStrategy = new WinnerStrategyTurnNumber();
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
