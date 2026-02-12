package hotstone.standard.factories;

import hotstone.framework.*;
import hotstone.framework.hero.HeroStrategy;
import hotstone.standard.deckStrategy.DeckStrategySpanish;
import hotstone.standard.heroStrategy.HeroStrategyThaiDanish;
import hotstone.standard.manaStrategy.ManaStrategyLinear;
import hotstone.standard.winnerStrategy.WinnerStrategyTurnNumber;

public class FactoryGamma implements FactoryAbstract {
    private final ManaStrategyLinear manaStrategy;
    private final DeckStrategySpanish deckStrategy;
    private final HeroStrategyThaiDanish heroStrategy;
    private final WinnerStrategyTurnNumber winnerStrategy;

    public FactoryGamma() {
        manaStrategy = new ManaStrategyLinear();
        deckStrategy = new DeckStrategySpanish();
        heroStrategy = new HeroStrategyThaiDanish();
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
