package hotstone.standard.random;

import hotstone.framework.card.Card;
import hotstone.framework.game.Game;
import hotstone.framework.Player;
import hotstone.framework.RandomSelector;

import java.util.List;

public class RandomSelectorDouble implements RandomSelector {
    private final int seed;

    public RandomSelectorDouble(int seed) {
        this.seed = seed;
    }

    @Override
    public Card getRandomMinionOnField(Game game, Player player) {
        return game.getCardInField(player, seed);
    }

    @Override
    public int getRandomNumber(int maxValueExcluded) {
        return seed;
    }

    @Override
    public void shuffleDeck(List<Card> deck) {}
}
