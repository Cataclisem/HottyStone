package hotstone.standard.specialDeckDecorator;

import hotstone.framework.DeckStrategy;
import hotstone.framework.FactoryAbstract;
import hotstone.standard.deckStrategy.DeckStrategySpecialised;

public class TwoDeckStrategySpecialised extends DeckStrategyDecorator {
    private final DeckStrategySpecialised deckStrategy;


    public TwoDeckStrategySpecialised(FactoryAbstract factory) { super(factory);
        deckStrategy = new DeckStrategySpecialised();
    }

    @Override
    public DeckStrategy getDeckStrategy() { return deckStrategy; }
}
