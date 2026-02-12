package hotstone.standard.specialDeckDecorator;

import hotstone.framework.ManaStrategy;
import hotstone.framework.WinnerStrategy;
import hotstone.framework.FactoryAbstract;
import hotstone.framework.hero.HeroStrategy;

public abstract class DeckStrategyDecorator implements FactoryAbstract {

    protected FactoryAbstract factory;


    public DeckStrategyDecorator(FactoryAbstract factory) {
        this.factory = factory;
    }

    @Override
    public WinnerStrategy getWinnerStrategy() {
        return factory.getWinnerStrategy();
    }

    @Override
    public ManaStrategy getManaStrategy() {
        return factory.getManaStrategy();
    }

    @Override
    public HeroStrategy getHeroStrategy() {
        return factory.getHeroStrategy();
    }
}
