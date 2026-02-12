package hotstone.standard.manaStrategy;

import hotstone.framework.ManaStrategy;
import hotstone.framework.game.ReadOnlyGame;

public class ManaStrategyFixed implements ManaStrategy {
    @Override
    public int computeMana(ReadOnlyGame game) {
        return 7;
    }
}
