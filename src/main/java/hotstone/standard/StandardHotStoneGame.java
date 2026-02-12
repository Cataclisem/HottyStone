/*
 * Copyright (C) 2022. Henrik Bærbak Christensen, Aarhus University.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *
 *  You may obtain a copy of the License at
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package hotstone.standard;

import hotstone.framework.*;
import hotstone.framework.card.Card;
import hotstone.framework.card.CardAttribute;
import hotstone.framework.card.MutableCard;
import hotstone.framework.game.MutableGame;
import hotstone.framework.game.ReadOnlyGame;
import hotstone.framework.hero.Hero;
import hotstone.framework.hero.HeroStrategy;
import hotstone.framework.hero.MutableHero;
import hotstone.observer.GameObserver;
import hotstone.observer.Observable;
import hotstone.observer.ObserverHandler;

import java.util.ArrayList;

/**
 * This is the 'temporary test stub' in TDD
 * terms: the initial empty but compilable implementation
 * of the game interface.
 * <p> .
 * It already includes a bit of FAKE-IT code for the first
 * test case about hand management.
 * <p>
 * Start solving the AlphaStone exercise by
 * following the TDD rythm: pick a one-step-test
 * from your test list, quickly add a test,
 * run it to see it fail, and then modify this
 * implementing class (and supporting classes)
 * to make your test case run. Refactor and repeat.
 * <p>
 * While this is the implementation of Game for
 * the AlphaStone game, you will constantly
 * refactor it over the course of the exercises
 * to become the 'core implementation' which will
 * enable a lot of game variants. This is also
 * why it is not called 'AlphaGame'.
 */
public class StandardHotStoneGame implements MutableGame, ReadOnlyGame, Observable {
    private final ManaStrategy manaStrategy;
    private final DeckStrategy deckStrategy;
    private final HeroStrategy heroStrategy;
    private final WinnerStrategy winnerStrategy;

    private Player playerInTurn = Player.FINDUS;
    private int turnNumber = 0;
    private Player winner;

    private final ArrayList<Card> handFindus = new ArrayList<>();
    private final ArrayList<Card> handPeddersen = new ArrayList<>();
    private final ArrayList<Card> fieldFindus = new ArrayList<>();
    private final ArrayList<Card> fieldPeddersen = new ArrayList<>();
    private final ArrayList<Card> deckFindus;
    private final ArrayList<Card> deckPeddersen;

    private final StandardHero heroFindus;
    private final StandardHero heroPeddersen;

    private final ObserverHandler observerHandler;

    public StandardHotStoneGame(FactoryAbstract hotstoneVersion) {
        manaStrategy = hotstoneVersion.getManaStrategy();
        deckStrategy = hotstoneVersion.getDeckStrategy();
        heroStrategy = hotstoneVersion.getHeroStrategy();
        winnerStrategy = hotstoneVersion.getWinnerStrategy();

        heroFindus = heroStrategy.computeHero(Player.FINDUS);
        heroPeddersen = heroStrategy.computeHero(Player.PEDDERSEN);

        deckFindus = deckStrategy.giveDeck(Player.FINDUS);
        deckPeddersen = deckStrategy.giveDeck(Player.PEDDERSEN);

        observerHandler = new ObserverHandler();

        // Giving heroes mana
        setHeroMana(Player.FINDUS, manaStrategy.computeMana(this));
        setHeroMana(Player.PEDDERSEN, manaStrategy.computeMana(this));

        int findussize = deckStrategy.computeStartingHandSize(Player.FINDUS);
        int peddersensize = deckStrategy.computeStartingHandSize(Player.PEDDERSEN);

        for (int i = 0; i < findussize; i++) {
            handFindus.add(0, deckFindus.get(0));
            deckFindus.remove(0);
        }

        for (int i = 0; i < peddersensize; i++) {
            handPeddersen.add(0, deckPeddersen.get(0));
            deckPeddersen.remove(0);
        }
    }

    @Override
    public Player getPlayerInTurn() {
        return playerInTurn;
    }

    @Override
    public MutableHero getHero(Player who) {
        if (who == Player.FINDUS) return heroFindus;
        else return heroPeddersen;
    }

    @Override
    public Player getWinner() {
        return winner;
    }

    @Override
    public int getTurnNumber() {
        return turnNumber;
    }

    @Override
    public int getDeckSize(Player who) {
        if (who == Player.FINDUS) return deckFindus.size();
        else return deckPeddersen.size();
    }

    @Override
    public Card getCardInHand(Player who, int indexInHand) {
        // Fake it
        if (who == Player.FINDUS) {
            return handFindus.get(indexInHand);
        } else {
            return handPeddersen.get(indexInHand);
        }
    }

    @Override
    public ArrayList<Card> getHand(Player who) {
        if (who == Player.FINDUS) {
            return handFindus;
        } else {
            return handPeddersen;
        }
    }

    @Override
    public int getHandSize(Player who) {
        if (who == Player.FINDUS) {
            return handFindus.size();
        } else {
            return handPeddersen.size();
        }
    }

    @Override
    public MutableCard getCardInField(Player who, int indexInField) {
        if (who == Player.FINDUS) {
            return (MutableCard) fieldFindus.get(indexInField);
        } else {
            return (MutableCard) fieldPeddersen.get(indexInField);
        }
    }

    @Override
    public ArrayList<Card> getField(Player who) {
        if (who == Player.FINDUS) {
            return fieldFindus;
        } else {
            return fieldPeddersen;
        }
    }

    @Override
    public int getFieldSize(Player who) {
        if (who == Player.FINDUS) {
            return fieldFindus.size();
        } else {
            return fieldPeddersen.size();
        }
    }

    @Override
    public ArrayList<Card> getDeck(Player who) {
        if (who == Player.FINDUS) {
            return deckFindus;
        } else {
            return deckPeddersen;
        }
    }

    @Override
    public void endTurn() {
        updateWinner();
        updateTurnCycle();
        observerHandler.notifyTurnChangeTo(getPlayerInTurn());
        activatePowerAndField();
        drawCardTurnStart();
        updateStartTurnMana();
    }

    @Override
    public void drawCard() {
        Player player = playerInTurn;
        ArrayList<Card> hand = getHand(player);
        ArrayList<Card> deck = getDeck(player);
        if (!deck.isEmpty()) {
            Card drawnCard = deck.get(0);
            hand.add(0, drawnCard);
            deck.remove(0);

            observerHandler.notifyCardDraw(player, drawnCard);
        } else {
            int fatigueDamage = GameConstants.HERO_HEALTH_PENALTY_ON_EMPTY_DECK;
            inflictDamageOnHero(player, fatigueDamage);
        }
    }

    @Override
    public Status playCard(Player who, Card card) {
        // Check if the card can be played
        Status status = isOkayToPlayCard(who, card);
        if (status != Status.OK) return status;

        // Use card effect and play the card
        ((MutableCard) card).getCardEffect().useEffect(who, this);
        addCardFromHandToFieldAndUpdateMana(who, card);

        // Notify observers
        observerHandler.notifyPlayCard(who, card);
        return status;
    }

    @Override
    public Status attackCard(Player playerAttacking, Card attackingCard, Card defendingCard) {
        Status status = legalAttackOnCard(playerAttacking, attackingCard, defendingCard);
        if (status != Status.OK) return status;

        // Update observers
        observerHandler.notifyAttackCard(playerAttacking, attackingCard, defendingCard);

        actAttackCard(playerAttacking, (MutableCard) attackingCard, (MutableCard) defendingCard);

        return status;
    }

    @Override
    public void inflictDamageOnCard(Player playerAttacking, MutableCard defendingCard, int damage) {
        defendingCard.setHealth(defendingCard.getHealth() - damage);

        if (defendingCard.getHealth() <= 0) {
            Player opponent = Utility.computeOpponent(playerAttacking);
            getField(opponent).remove(defendingCard);
            observerHandler.notifyCardRemove(opponent, defendingCard);
        } else {
            observerHandler.notifyCardUpdate(defendingCard);
        }
    }

    @Override
    public void setCardAttack(MutableCard card, int newDamage) {
        card.setAttack(newDamage);
        observerHandler.notifyCardUpdate(card);
    }

    @Override
    public Status attackHero(Player playerAttacking, Card attackingCard) {
        Status status = legalAttack(playerAttacking, attackingCard);
        if (status != Status.OK) return status;

        Player opponent = Utility.computeOpponent(playerAttacking);
        boolean existsTauntMinion = existsTauntInField(opponent);

        if (existsTauntMinion)
            return Status.ATTACK_NOT_ALLOWED_ON_NON_TAUNT_MINION;


        inflictDamageOnOpponentHero(playerAttacking, attackingCard.getAttack());
        observerHandler.notifyAttackHero(playerAttacking, attackingCard);
        observerHandler.notifyHeroUpdate(Utility.computeOpponent(playerAttacking));

        setCardActive((MutableCard) attackingCard, false);

        return status;
    }

    @Override
    public void healHero(Player player, int heal) {
        MutableHero hero = getHero(player);
        hero.setHealth(hero.getHealth() + heal);
    }

    @Override
    public void inflictDamageOnOpponentHero(Player playerAttacking, int damage) {
        Player opponent = Utility.computeOpponent(playerAttacking);
        inflictDamageOnHero(opponent, damage);
    }

    @Override
    public Status usePower(Player who) {
        Status status = legalPowerUse(who);
        if (status != Status.OK) return status;
        actUsePower(who);

        // Notify power use and power inactive
        observerHandler.notifyUsePower(who);
        observerHandler.notifyHeroUpdate(who);

        return status;
    }

    @Override
    public void addObserver(GameObserver observer) {
        observerHandler.addObserver(observer);
    }

    private void inflictDamageOnHero(Player player, int damage) {
        MutableHero hero = getHero(player);
        hero.setHealth(hero.getHealth() - damage);

        observerHandler.notifyHeroUpdate(player);
    }

    private void activatePowerAndField() {
        activateHeroPower(playerInTurn);

        for (int i = 0; i < getFieldSize(playerInTurn); i++) {
            setCardActive(getCardInField(playerInTurn, i), true);
        }
    }

    private void activateHeroPower(Player player) {
        if (player == Player.FINDUS) {
            heroFindus.setPowerActive(true);
        } else {
            heroPeddersen.setPowerActive(true);
        }

        observerHandler.notifyHeroUpdate(player);
    }

    private void updateTurnCycle() {
        playerInTurn = Utility.computeOpponent(playerInTurn);
        turnNumber++;
    }

    private void updateStartTurnMana() {
        setHeroMana(playerInTurn, manaStrategy.computeMana(this));
    }

    private void drawCardTurnStart() {
        if (turnNumber < 2) {
            return;
        }
        drawCard();
    }

    private void actAttackCard(Player playerAttacking, MutableCard attackingCard, MutableCard defendingCard) {
        int attackingCardDamage = attackingCard.getAttack();
        int defendingCardDamage = defendingCard.getAttack();
        Player opponent = Utility.computeOpponent(playerAttacking);

        inflictDamageOnCard(playerAttacking, defendingCard, attackingCardDamage);
        winnerStrategy.onMinionAttack(this, playerAttacking, attackingCard);

        inflictDamageOnCard(opponent, attackingCard, defendingCardDamage);
        if (attackingCard.getHealth() > 0)
            setCardActive(attackingCard, false);
    }

    private void actUsePower(Player who) {
        Hero hero = getHero(who);
        ((MutableHero)hero).getPower().useEffect(who, this);
        getHero(who).setPowerActive(false);
        setHeroMana(who, getHero(who).getMana() - GameConstants.HERO_POWER_COST);
    }

    private void addCardFromHandToFieldAndUpdateMana(Player who, Card card) {
        ArrayList<Card> hand = getHand(who);
        ArrayList<Card> field = getField(who);
        MutableHero hero = getHero(who);

        if (card.getHealth() > 0) field.add(card);

        hand.remove(card);

        int currentMana = hero.getMana();
        int manaCost = card.getManaCost();
        setHeroMana(who, currentMana - manaCost);
    }

    private void setHeroMana(Player who, int newMana) {
        MutableHero hero = getHero(who);
        hero.setMana(newMana);

        observerHandler.notifyHeroUpdate(who);
    }

    private void setCardActive(MutableCard card, boolean active) {
        card.setActive(active);
        // Notify observers
        observerHandler.notifyCardUpdate(card);
    }

    private boolean playerInTurnHasNotEnoughMana(int manaCost) {
        Hero hero = getHero(playerInTurn);
        return hero.getMana() < manaCost;
    }

    private Status legalAttack(Player player, Card attackingcard) {
        if (playerInTurn != player) return Status.NOT_PLAYER_IN_TURN;
        if (attackingcard.getOwner() != player) return Status.NOT_OWNER;
        if (!attackingcard.isActive()) return Status.ATTACK_NOT_ALLOWED_FOR_NON_ACTIVE_MINION;
        return Status.OK;
    }

    private Status legalAttackOnCard(Player player, Card attackingcard, Card defendingCard) {
        Status status = legalAttack(player, attackingcard);
        if (status != Status.OK) return status;
        if (defendingCard.getOwner() == player) return Status.ATTACK_NOT_ALLOWED_ON_OWN_MINION;

        Player opponent = Utility.computeOpponent(player);
        boolean existsTauntMinion = existsTauntInField(opponent);

        if (defendingCard.getAttribute() != CardAttribute.TAUNT && existsTauntMinion)
            return Status.ATTACK_NOT_ALLOWED_ON_NON_TAUNT_MINION;

        return Status.OK;
    }

    private Status legalPowerUse(Player who) {
        if (who != playerInTurn) return Status.NOT_PLAYER_IN_TURN;
        if (!getHero(who).canUsePower()) return Status.POWER_USE_NOT_ALLOWED_TWICE_PR_ROUND;
        if (playerInTurnHasNotEnoughMana(GameConstants.HERO_POWER_COST)) return Status.NOT_ENOUGH_MANA;
        return Status.OK;
    }

    private boolean existsTauntInField(Player player) {
        boolean existsTauntMinion = false;

        for (int i = 0; i < getFieldSize(player); i++) {
            if (getCardInField(player, i).getAttribute() == CardAttribute.TAUNT) {
                existsTauntMinion = true;
                break;
            }
        }

        return existsTauntMinion;
    }

    private Status isOkayToPlayCard(Player who, Card card) {
        if (who != playerInTurn) return Status.NOT_PLAYER_IN_TURN;

        if (card.getOwner() != who) return Status.NOT_OWNER;

        if (playerInTurnHasNotEnoughMana(card.getManaCost())) return Status.NOT_ENOUGH_MANA;

        return Status.OK;

    }

    private void updateWinner() {
        Player previousWinner = winner;
        winner = winnerStrategy.computeWinner(this, winner);
        if (previousWinner != winner)
            observerHandler.notifyGameWon(winner);
    }
}
