package hotstone.framework;

import hotstone.framework.game.MutableGame;

public interface Effect {
    void useEffect(Player player, MutableGame game);

    String getDescription();
}
