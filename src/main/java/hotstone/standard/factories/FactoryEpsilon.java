package hotstone.standard.factories;

import hotstone.framework.*;
import hotstone.framework.hero.HeroStrategy;
import hotstone.standard.deckStrategy.DeckStrategySpanish;
import hotstone.standard.heroStrategy.HeroStrategyFrenchItalian;
import hotstone.standard.manaStrategy.ManaStrategyLinear;
import hotstone.standard.winnerStrategy.WinnerStrategyAttackSum;

public class FactoryEpsilon implements FactoryAbstract {
    private final DeckStrategySpanish deckStrategy;
    private final HeroStrategyFrenchItalian heroStrategy;
    private final WinnerStrategyAttackSum winnerStrategy;
    private final ManaStrategy manaStrategy;

    public FactoryEpsilon(RandomSelector randomSelector) {
        manaStrategy = new ManaStrategyLinear();
        deckStrategy = new DeckStrategySpanish();
        heroStrategy = new HeroStrategyFrenchItalian(randomSelector);
        winnerStrategy = new WinnerStrategyAttackSum();
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