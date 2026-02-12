package hotstone.standard.factories;

import hotstone.framework.*;
import hotstone.framework.hero.HeroStrategy;
import hotstone.standard.deckStrategy.DeckStrategyFoodEffectsAndAttributes;
import hotstone.standard.heroStrategy.HeroStrategyBaby;
import hotstone.standard.manaStrategy.ManaStrategyFixed;
import hotstone.standard.winnerStrategy.WinnerStrategyTurnNumber;

public class FactoryTheta implements FactoryAbstract {
    private final ManaStrategyFixed manaStrategy;
    private final DeckStrategyFoodEffectsAndAttributes deckStrategy;
    private final HeroStrategyBaby heroStrategy;
    private final WinnerStrategyTurnNumber winnerStrategy;

    public FactoryTheta(RandomSelector randomSelector) {
        manaStrategy = new ManaStrategyFixed();
        deckStrategy = new DeckStrategyFoodEffectsAndAttributes(randomSelector);
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
