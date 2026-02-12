package hotstone.standard.deckStrategy;

import hotstone.framework.*;
import hotstone.framework.card.Card;
import hotstone.standard.GameConstants;
import hotstone.standard.StandardCard;
import hotstone.standard.effect.*;

import java.util.ArrayList;

public class DeckStrategyFoodEffects implements DeckStrategy {
    private final RandomSelector randomSelector;
    public DeckStrategyFoodEffects(RandomSelector randomSelector){this.randomSelector = randomSelector; }
    @Override
    public ArrayList<Card> giveDeck(Player player) {
        Player opponent = Utility.computeOpponent(player);
        ArrayList<Card> initialDeck = new ArrayList<>();
        ArrayList<Card> finalDeck = new ArrayList<>();
        int random_one_mana = randomSelector.getRandomNumber(2);
        int random_two_mana = randomSelector.getRandomNumber(7);
        int random_four_mana = randomSelector.getRandomNumber(12);

        initialDeck.add(new StandardCard(GameConstants.BROWN_RICE_CARD, 1, 1, 1, player, new EffectDoDamageToHero(1)));
        initialDeck.add(new StandardCard(GameConstants.BROWN_RICE_CARD, 1, 1, 1, player, new EffectDoDamageToHero(1)));
        initialDeck.add(new StandardCard(GameConstants.FRENCH_FRIES_CARD, 2, 2, 2, player));
        initialDeck.add(new StandardCard(GameConstants.FRENCH_FRIES_CARD, 2, 2, 2, player));
        initialDeck.add(new StandardCard(GameConstants.GREEN_SALAD_CARD, 2, 2, 3, player));
        initialDeck.add(new StandardCard(GameConstants.GREEN_SALAD_CARD, 2, 2, 3, player));
        initialDeck.add(new StandardCard(GameConstants.TOMATO_SALAD_CARD, 2, 3, 2, player, new EffectGiveAttackToRandomMinion(randomSelector,1, player, player)));
        initialDeck.add(new StandardCard(GameConstants.TOMATO_SALAD_CARD, 2, 3, 2, player, new EffectGiveAttackToRandomMinion(randomSelector,1, player, player)));
        initialDeck.add(new StandardCard(GameConstants.POKE_BOWL_CARD, 3, 2, 3, player, new EffectGiveTwoHealthHero()));
        initialDeck.add(new StandardCard(GameConstants.POKE_BOWL_CARD, 3, 2, 3, player, new EffectGiveTwoHealthHero()));
        initialDeck.add(new StandardCard(GameConstants.PUMPKIN_SOUP_CARD, 4, 2, 7, player));
        initialDeck.add(new StandardCard(GameConstants.PUMPKIN_SOUP_CARD, 4, 2, 7, player));
        initialDeck.add(new StandardCard(GameConstants.NOODLE_SOUP_CARD, 4, 5, 3, player, new EffectDrawRandomCard()));
        initialDeck.add(new StandardCard(GameConstants.NOODLE_SOUP_CARD, 4, 5, 3, player, new EffectDrawRandomCard()));
        initialDeck.add(new StandardCard(GameConstants.SPRING_ROLLS_CARD, 5, 3, 7, player));
        initialDeck.add(new StandardCard(GameConstants.SPRING_ROLLS_CARD, 5, 3, 7, player));
        initialDeck.add(new StandardCard(GameConstants.BAKED_SALMON_CARD, 5, 8, 2, player));
        initialDeck.add(new StandardCard(GameConstants.BAKED_SALMON_CARD, 5, 8, 2, player));
        initialDeck.add(new StandardCard(GameConstants.CHICKEN_CURRY_CARD, 6, 4, 4, player, new EffectKillRandomMinion(randomSelector)));
        initialDeck.add(new StandardCard(GameConstants.CHICKEN_CURRY_CARD, 6, 4, 4, player, new EffectKillRandomMinion(randomSelector)));
        initialDeck.add(new StandardCard(GameConstants.BEEF_BURGER_CARD, 6, 8, 6, player, new EffectGiveAttackToRandomMinion(randomSelector,2, player, opponent)));
        initialDeck.add(new StandardCard(GameConstants.BEEF_BURGER_CARD, 6, 8, 6, player, new EffectGiveAttackToRandomMinion(randomSelector,2, player, opponent)));
        initialDeck.add(new StandardCard(GameConstants.FILET_MIGNON_CARD, 7, 9, 5, player));
        initialDeck.add(new StandardCard(GameConstants.FILET_MIGNON_CARD, 7, 9, 5, player));

        finalDeck.add(initialDeck.get(random_one_mana));
        initialDeck.remove(random_one_mana);
        finalDeck.add(initialDeck.get(random_two_mana));
        initialDeck.remove(random_two_mana);
        finalDeck.add(initialDeck.get(random_four_mana));
        initialDeck.remove(random_four_mana);

        randomSelector.shuffleDeck(initialDeck);
        finalDeck.addAll(initialDeck);
        return finalDeck;
    }

    @Override
    public int computeStartingHandSize(Player player) {
        return 3;
    }
}
