package hotstone.framework.hero;

import hotstone.framework.Player;
import hotstone.standard.StandardHero;

public interface HeroStrategy {
    StandardHero computeHero(Player player);
}
