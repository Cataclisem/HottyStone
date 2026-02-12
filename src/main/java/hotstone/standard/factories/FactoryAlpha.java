package hotstone.standard.factories;

import hotstone.framework.*;
import hotstone.framework.hero.HeroStrategy;
import hotstone.standard.deckStrategy.DeckStrategySpanish;
import hotstone.standard.heroStrategy.HeroStrategyBaby;
import hotstone.standard.manaStrategy.ManaStrategyLinear;
import hotstone.standard.winnerStrategy.WinnerStrategyTurnNumber;

public class FactoryAlpha implements FactoryAbstract {
    private final DeckStrategySpanish deckStrategy;
    private final HeroStrategyBaby heroStrategy;
    private final WinnerStrategyTurnNumber winnerStrategy;
    private final ManaStrategy manaStrategy;

    public FactoryAlpha() {
        manaStrategy = new ManaStrategyLinear();
        deckStrategy = new DeckStrategySpanish();
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
