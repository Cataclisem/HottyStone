package hotstone.framework;

import hotstone.framework.hero.HeroStrategy;

public interface FactoryAbstract {
    WinnerStrategy getWinnerStrategy();

    ManaStrategy getManaStrategy();

    HeroStrategy getHeroStrategy();

    DeckStrategy getDeckStrategy();
}
