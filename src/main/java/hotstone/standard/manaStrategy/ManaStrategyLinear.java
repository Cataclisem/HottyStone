package hotstone.standard.manaStrategy;

import hotstone.framework.ManaStrategy;
import hotstone.framework.game.ReadOnlyGame;

public class ManaStrategyLinear implements ManaStrategy {
    @Override
    public int computeMana(ReadOnlyGame game) {
        return 2 + game.getTurnNumber() / 2;
    }
}
