package hotstone.standard.random;

import hotstone.framework.card.Card;
import hotstone.framework.game.Game;
import hotstone.framework.Player;
import hotstone.framework.RandomSelector;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RandomSelectorStandard implements RandomSelector {
    private final Random ranNum;

    public RandomSelectorStandard() {
        this.ranNum = new Random();
    }

    @Override
    public Card getRandomMinionOnField(Game game, Player player) {
        int fieldSize = game.getFieldSize(player);
        if (fieldSize == 0)
            return null;

        int ranNumFromField = ranNum.nextInt(fieldSize);

        return game.getCardInField(player, ranNumFromField);
    }

    @Override
    public int getRandomNumber(int maxValueExcluded) {
        return ranNum.nextInt(maxValueExcluded);
    }

    @Override
    public void shuffleDeck(List<Card> deck) {
        Collections.shuffle(deck);
    }
}
