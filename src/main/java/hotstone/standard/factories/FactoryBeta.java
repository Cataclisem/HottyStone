package hotstone.standard.factories;

import hotstone.framework.*;
import hotstone.framework.hero.HeroStrategy;
import hotstone.standard.deckStrategy.DeckStrategySpanish;
import hotstone.standard.heroStrategy.HeroStrategyBaby;
import hotstone.standard.manaStrategy.ManaStrategyLinearCapped;
import hotstone.standard.winnerStrategy.WinnerStrategyHeroDeath;

public class FactoryBeta implements FactoryAbstract {
    private final ManaStrategy manaStrategy;
    private final DeckStrategySpanish deckStrategy;
    private final HeroStrategyBaby heroStrategy;
    private final WinnerStrategyHeroDeath winnerStrategy;

    public FactoryBeta() {
        manaStrategy = new ManaStrategyLinearCapped();
        deckStrategy = new DeckStrategySpanish();
        heroStrategy = new HeroStrategyBaby();
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
