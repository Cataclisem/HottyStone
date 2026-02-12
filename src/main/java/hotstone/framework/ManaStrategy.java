package hotstone.framework;

import hotstone.framework.game.ReadOnlyGame;

public interface ManaStrategy {
    int computeMana(ReadOnlyGame game);
}
