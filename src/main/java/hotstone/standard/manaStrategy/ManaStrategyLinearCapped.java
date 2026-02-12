package hotstone.standard.manaStrategy;

import hotstone.framework.ManaStrategy;
import hotstone.framework.game.ReadOnlyGame;

public class ManaStrategyLinearCapped implements ManaStrategy {
    @Override
    public int computeMana(ReadOnlyGame game) {
        if (game.getTurnNumber() <= 10) {
            return 2 + game.getTurnNumber() / 2;
        } else {
            return 7;
        }
    }
}
