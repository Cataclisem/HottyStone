package hotstone.framework;

import hotstone.framework.card.Card;
import hotstone.framework.game.Game;

import java.util.List;

public interface RandomSelector {

    Card getRandomMinionOnField(Game game, Player player);

    int getRandomNumber(int maxValueExcluded);

    void shuffleDeck(List<Card> deck);
}
