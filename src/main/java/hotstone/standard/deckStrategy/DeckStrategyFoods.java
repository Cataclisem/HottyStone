package hotstone.standard.deckStrategy;

import hotstone.framework.*;
import hotstone.framework.card.Card;
import hotstone.standard.GameConstants;
import hotstone.standard.StandardCard;

import java.util.*;

public class DeckStrategyFoods implements DeckStrategy {
    @Override
    public ArrayList<Card> giveDeck(Player player) {
        Random random = new Random();
        ArrayList<Card> initialDeck = new ArrayList<>();
        ArrayList<Card> finalDeck = new ArrayList<>();
        int random_one_mana = random.nextInt(4);
        int random_two_mana = random.nextInt(7);
        int random_four_mana = random.nextInt(12);

        initialDeck.add(new StandardCard(GameConstants.BROWN_RICE_CARD, 1, 1, 2, player));
        initialDeck.add(new StandardCard(GameConstants.BROWN_RICE_CARD, 1, 1, 2, player));
        initialDeck.add(new StandardCard(GameConstants.FRENCH_FRIES_CARD, 1, 2, 1, player));
        initialDeck.add(new StandardCard(GameConstants.FRENCH_FRIES_CARD, 1, 2, 1, player));
        initialDeck.add(new StandardCard(GameConstants.GREEN_SALAD_CARD, 2, 2, 3, player));
        initialDeck.add(new StandardCard(GameConstants.GREEN_SALAD_CARD, 2, 2, 3, player));
        initialDeck.add(new StandardCard(GameConstants.TOMATO_SALAD_CARD, 2, 3, 2, player));
        initialDeck.add(new StandardCard(GameConstants.TOMATO_SALAD_CARD, 2, 3, 2, player));
        initialDeck.add(new StandardCard(GameConstants.POKE_BOWL_CARD, 3, 2, 4, player));
        initialDeck.add(new StandardCard(GameConstants.POKE_BOWL_CARD, 3, 2, 4, player));
        initialDeck.add(new StandardCard(GameConstants.PUMPKIN_SOUP_CARD, 4, 2, 7, player));
        initialDeck.add(new StandardCard(GameConstants.PUMPKIN_SOUP_CARD, 4, 2, 7, player));
        initialDeck.add(new StandardCard(GameConstants.NOODLE_SOUP_CARD, 4, 5, 3, player));
        initialDeck.add(new StandardCard(GameConstants.NOODLE_SOUP_CARD, 4, 5, 3, player));
        initialDeck.add(new StandardCard(GameConstants.SPRING_ROLLS_CARD, 5, 3, 7, player));
        initialDeck.add(new StandardCard(GameConstants.SPRING_ROLLS_CARD, 5, 3, 7, player));
        initialDeck.add(new StandardCard(GameConstants.BAKED_SALMON_CARD, 5, 8, 2, player));
        initialDeck.add(new StandardCard(GameConstants.BAKED_SALMON_CARD, 5, 8, 2, player));
        initialDeck.add(new StandardCard(GameConstants.CHICKEN_CURRY_CARD, 6, 8, 4, player));
        initialDeck.add(new StandardCard(GameConstants.CHICKEN_CURRY_CARD, 6, 8, 4, player));
        initialDeck.add(new StandardCard(GameConstants.BEEF_BURGER_CARD, 6, 5, 6, player));
        initialDeck.add(new StandardCard(GameConstants.BEEF_BURGER_CARD, 6, 5, 6, player));
        initialDeck.add(new StandardCard(GameConstants.FILET_MIGNON_CARD, 7, 9, 5, player));
        initialDeck.add(new StandardCard(GameConstants.FILET_MIGNON_CARD, 7, 9, 5, player));

        finalDeck.add(initialDeck.get(random_one_mana));
        initialDeck.remove(random_one_mana);
        finalDeck.add(initialDeck.get(random_two_mana));
        initialDeck.remove(random_two_mana);
        finalDeck.add(initialDeck.get(random_four_mana));
        initialDeck.remove(random_four_mana);

        Collections.shuffle(initialDeck);
        finalDeck.addAll(initialDeck);
        return finalDeck;
    }

    @Override
    public int computeStartingHandSize(Player player) {
        return 3;
    }
}
