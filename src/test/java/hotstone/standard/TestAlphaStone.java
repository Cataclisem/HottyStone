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

/**
 * Skeleton class for AlphaStone test cases

 *    This source code is from the book
 *      "Flexible, Reliable Software:
 *        Using Patterns and Agile Development"
 *      2nd Edition
 *    Author:
 *      Henrik Bærbak Christensen
 *      Department of Computer Science
 *      Aarhus University
 */

import hotstone.framework.*;
import hotstone.framework.card.Card;
import hotstone.framework.game.Game;
import hotstone.standard.factories.FactoryAlpha;
import hotstone.utility.TestHelper;
import org.junit.jupiter.api.*;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/** Template for your own ongoing TDD process.
 * Fill it out until you have covered all
 * requirements for the alpha stone game.
 */
public class TestAlphaStone {
  private Game game;
  /** Fixture for AlphaStone testing. */
  @BeforeEach
  public void setUp() {
    game = new StandardHotStoneGame(new FactoryAlpha());
  }

  // Example of an early, simple test case:
  // Turn handling

  @Test
  public void shouldHaveFindusAsFirstPlayer() {
    // Given a game
    // When the game ask for the player in turn
    Player player = game.getPlayerInTurn();
    // Then it should be Findus
    assertThat(player, is(Player.FINDUS));
    // Showing game
    TestHelper.printGameState(game);
  }

  @Test
  public void shouldBePeddersenAfterFindus() {
    // Given a game
    // Findus skips his first turn
    game.endTurn();
    // When the game ask for the player in turn
    Player player = game.getPlayerInTurn();
    // Then it should be Peddersen
    assertThat(game.getPlayerInTurn(), is(Player.PEDDERSEN));
  }

  @Test
  public void shouldBeFindusAfterPeddersen() {
    // Given a game
    // Both Findus and Peddersen skips their first turn
    game.endTurn();
    game.endTurn();
    // When the game ask for the player in turn
    Player player = game.getPlayerInTurn();
    // Then it should be Findus again
    assertThat(game.getPlayerInTurn(), is(Player.FINDUS));
  }

  @Test
  public void shouldGetTurnNumberZeroAtGameStart() {
    // Given a game
    // When the game starts
    // Then the turn number is zero
    assertThat(game.getTurnNumber(), is(0));
  }

  @Test
  public void shouldGetTurnNumberOneAfterOneEndturn() {
    // Given a game
    // When Findus ends his first turn
    game.endTurn();
    // Then the turn number should be one
    assertThat(game.getTurnNumber(), is(1));
    // Showing game
    TestHelper.printGameState(game);
  }

  // Example of a later, more complex, test case:
  // Card handling

  // The HotStone specs are quite insisting on how
  // the cards, drawn from the deck, are organized
  // in the hand. So when drawing the top three cards
  // from the deck (uno, dos, tres) they have to
  // be organized in the hand as
  // index 0 = tres; index 1 = dos; index 2 = uno
  // That is, a newly drawn card is 'at the top'
  // of the hand - always entered at position 0
  // and pushing the rest of the cards 1 position
  // 'down'

  @Test
  public void shouldFindusHaveUnoDosTresCardsInitially() {
    // Given a game
    // Findus draws three cards into his hand
    int count = game.getHandSize(Player.FINDUS);
    // When the game asks for the size of his hand
    // Then the size should be three
    assertThat(count, is(3));
    // These are ordered as Tres, Dos and Uno in index zero, one and two respectively
    // When asking for the card in index zero
    Card cardFirst = game.getCardInHand(Player.FINDUS, 0);
    // Then it should be Tres
    assertThat(cardFirst.getName(), is(GameConstants.TRES_CARD));
    // When asking for the one in index one
    Card cardSecond = game.getCardInHand(Player.FINDUS, 1);
    // Then it should be Dos
    assertThat(cardSecond.getName(), is(GameConstants.DOS_CARD));
    // When asking for the card in index two
    Card cardThird = game.getCardInHand(Player.FINDUS, 2);
    // Then it should be Uno
    assertThat(cardThird.getName(), is(GameConstants.UNO_CARD));
    // Showing game
    TestHelper.printGameState(game);
  }

  @Test
  public void shouldPeddersenHaveUnoDosTresCardsInitially() {
    // Given a game
    // When Peddersen draws three cards into his hand
    int count = game.getHandSize(Player.PEDDERSEN);
    // When the game asks for the size of his hand
    // Then the size should be three
    assertThat(count, is(3));
    // These are ordered as Tres, Dos and Uno in index zero, one and two respectively
    // When asking for the card in index zero
    Card cardFirst = game.getCardInHand(Player.PEDDERSEN, 0);
    // Then it should be Tres
    assertThat(cardFirst.getName(), is(GameConstants.TRES_CARD));
    // When asking for the one in index one
    Card cardSecond = game.getCardInHand(Player.PEDDERSEN, 1);
    // Then it should be Dos
    assertThat(cardSecond.getName(), is(GameConstants.DOS_CARD));
    // When asking for the card in index two
    Card cardThird = game.getCardInHand(Player.PEDDERSEN, 2);
    // Then it should be Uno
    assertThat(cardThird.getName(), is(GameConstants.UNO_CARD));
  }

  @Test
  public void shouldReturnTwoTwoTwoWhenCardDosIsCalled(){
    // Given a game
    // When inspecting the "Dos" card in Findus' hand
    StandardCard dos = new StandardCard(GameConstants.DOS_CARD, 2, 2, 2, Player.FINDUS);
    // Then it should have the attack, health and mana cost of two, two and two
    assertThat(dos.getAttack(), is(2));
    assertThat(dos.getHealth(), is(2));
    assertThat(dos.getManaCost(), is(2));
    // Showing game
    TestHelper.printGameState(game);
  }

  @Test
  public void shouldReturnThreeThreeThreeWhenCardTresIsCalled(){
    // Given a game
    // When inspecting the "Tres" card in Findus hand
    StandardCard tres = new StandardCard(GameConstants.TRES_CARD, 3, 3, 3, Player.FINDUS);
    // Then it should have the attack, health and mana cost of three, three and three
    assertThat(tres.getAttack(), is(3));
    assertThat(tres.getHealth(), is(3));
    assertThat(tres.getManaCost(), is(3));
  }

  @Test
  public void shouldNoLongerHaveTresInHandAfterPlayingIt(){
    // Given a game
    Player player = game.getPlayerInTurn();
    // When Findus plays the "Tres" card in index two of his hand
    Card card = game.getCardInHand(player, 2);
    game.playCard(player, card);
    // Then his hand should not contain this card anymore.
    assertThat(((ArrayList<StandardCard>) game.getHand(player)).contains(card), is(false));
    // Showing game
    TestHelper.printGameState(game);
  }

  @Test
  public void shouldPeddersenShouldHaveThreeCards(){
    // Given a game
    // When it is Peddersens first turn
    game.endTurn();
    Player player = game.getPlayerInTurn();
    ArrayList<StandardCard> initialCards = (ArrayList<StandardCard>) game.getHand(player);
    // Then he should have with three cards in hand.
    assertThat(initialCards.size(), is(3));
  }

  @Test
  public void ShouldFindusLosesOneOfThreeCards(){
    // Given a game
    // Findus starts his first turn
    Player player = game.getPlayerInTurn();
    ArrayList<StandardCard> initialCards = (ArrayList<StandardCard>) game.getHand(player);
    // When he plays the card in index two of his hand
    game.playCard(player, initialCards.get(2));
    // Then he should lose one from his hand size
    assertThat(game.getHandSize(player), is(2));
    // Showing game
    TestHelper.printGameState(game);
  }

  @Test
  public void shouldStartWithEmptyField() {
    // Given a game
    // When game starts
    // Then both Findus and Peddersens field should be of the size zero
    assertThat(game.getFieldSize(Player.FINDUS), is(0));
    assertThat(game.getFieldSize(Player.PEDDERSEN), is(0));
    // Showing game
    TestHelper.printGameState(game);
  }

  @Test
  public void shouldGetFieldSizeOneAfterPlayingCard() {
    // Given a game
    // Findus does nothing
    game.endTurn();
    // Player in turn is Peddersen
    Player player = Player.PEDDERSEN;
    // When Peddersen plays his "Uno" card in index two
    game.playCard(player,game.getCardInHand(player,2));
    // Then Peddersens field should be of size one
    assertThat(game.getFieldSize(Player.PEDDERSEN), is(1));
    // When asking for Findus field size
    // Then it should still have the size zero
    assertThat(game.getFieldSize(Player.FINDUS), is(0));
    // Showing game
    TestHelper.printGameState(game);
  }
  @Test
  public void shouldGetCardOnFieldAfterPlayingIt() {
    // Given a game
    // Findus skips his first turn
    game.endTurn();
    // Player in turn is Peddersen
    Player player = Player.PEDDERSEN;
    // When Peddersen plays his "Uno" card in index two of his hand
    game.playCard(player,game.getCardInHand(player,2));
    // Then this card should appear on his field
    assertThat(game.getCardInField(player,0).getName(), is(GameConstants.UNO_CARD));
    // Showing game
    TestHelper.printGameState(game);
  }
  @Test
  public void shouldHaveCorrectCardsInFieldAfterPlayingThem() {
    // Given a game
    // Skipping to Peddersens third turn
    game.endTurn();
    game.endTurn();
    game.endTurn();
    game.endTurn();
    game.endTurn();
    // Player in turn is Peddersen
    Player player = Player.PEDDERSEN;
    // When Peddersen plays his "Uno" and "Dos" cards from index four and three of his hand
    Card card_1 = game.getCardInHand(player,4);
    Card card_2 = game.getCardInHand(player,3);
    game.playCard(player,card_1);
    game.playCard(player,card_2);
    // Then they should appear on Peddersens field
    assertThat(game.getCardInField(player,0).getName(), is(card_1.getName()));
    assertThat(game.getCardInField(player,1).getName(), is(card_2.getName()));
    // Showing game
    TestHelper.printGameState(game);
  }

  @Test
  public void shouldGetCardOnFieldAfterPlayingItPeddersen() {
    // Given a game
    // Skipping to Peddersens third turn
    game.endTurn();
    game.endTurn();
    game.endTurn();
    game.endTurn();
    game.endTurn();
    // Player in turn is Peddersen
    Player player = Player.PEDDERSEN;
    // Peddersen plays his "Uno" and "Dos" cards from index four and three of his hand
    Card card_1 = game.getCardInHand(player,4);
    Card card_2 = game.getCardInHand(player,3);
    game.playCard(player,card_1);
    game.playCard(player,card_2);
    // When checking if the cards are in the order "Uno Dos" on the field
    ArrayList<Card> CurrentField = (ArrayList<Card>) game.getField(player);
    ArrayList<Card> ExpectedField = new ArrayList<Card>();
    ExpectedField.add(card_1);
    ExpectedField.add(card_2);
    // Then it should be correct.
    assertThat(CurrentField, is(ExpectedField));
  }

  @Test
  public void shouldHaveBabyAsFindusHero() {
    // Given a game
    // When game starts
    // Then Findus has hero type Baby
    assertThat(game.getHero(Player.FINDUS).getType(), is(GameConstants.BABY_HERO_TYPE));
  }

  @Test
  public void shouldReturnFindusAsWinnerAfterTurn8() {
    // Given a game
    // Skipping to Findus' fourth turn on the turn number eight
    for (int i = 0; i < 7; i++)
      game.endTurn();
    // When checking if a winner by calling getWinner()
    game.endTurn();
    Player winner = game.getWinner();
    // Then it should return Findus
    assertThat(winner, is(Player.FINDUS));
  }

  @Test
  public void shouldHaveNoWinnerAtBeginning() {
    // Given a game
    // When checking if a winner set at game start
    Player winner = game.getWinner();
    // Then should not have a winner, returning null as winner
    assertThat(winner, is(nullValue()));
  }

  @Test
  public void shouldHaveCorrectOwnerUponPlayingCardFindus() {
    // Given a game
    Player player = game.getPlayerInTurn();
    // When Findus plays his "Uno" card on index two of his hand
    game.playCard(player, game.getCardInHand(player, 2));
    // Then the owner of this card should be Findus
    assertThat(game.getCardInField(player, 0).getOwner(), is(player));
    // Showing game
    TestHelper.printGameState(game);
  }

  @Test
  public void shouldHaveCorrectOwnerPlayingCardPeddersen() {
    // Given a game
    // Findus skips his first turn
    game.endTurn();
    // Player in turn is Peddersen
    Player player = game.getPlayerInTurn();
    // When Peddersen plays his "Uno" card on index two of his hand
    game.playCard(player, game.getCardInHand(player, 2));
    // Then its owner should be Peddersen
    assertThat(game.getCardInField(player, 0).getOwner(), is(player));
  }

  @Test
  public void shouldNotAllowFindusToPlayPeddersensCards() {
    // Given a game
    Player player = Player.FINDUS;
    // When Findus tries to play the "Uno" card from index two of Peddersen hand
    Status status = game.playCard(player, game.getCardInHand(Player.PEDDERSEN,2));
    // Then the status message "NOT OWNER" should be given
    assertThat(status, is(Status.NOT_OWNER));
    // Showing game
    TestHelper.printGameState(game);
  }

  @Test
  public void shouldAllowFindusToPlayHisCards() {
    // Given a game
    Player player = Player.FINDUS;
    // When Findus tries to play the "Uno" card from index two of Peddersen hand
    Status status = game.playCard(player, game.getCardInHand(player,2));
    // Then the status message "OK" should be given
    assertThat(status, is(Status.OK));
  }


  @Test
  public void shouldNotAllowPeddersenToPlayWhenItsNotHisTurn() {
    // Given a game
    Player player = Player.FINDUS;
    // When Peddersen tries to play his "Uno" card on Findus' turn
    Status status = game.playCard(Player.PEDDERSEN, game.getCardInHand(Player.PEDDERSEN, 0));
    // Then the status message "NOT PLAYER IN TURN" should be given
    assertThat(status, is(Status.NOT_PLAYER_IN_TURN));
  }

  @Test
  public void shouldFindusNotBeAbleToPlayCardWhenNotEnoughMana() {
    // Given a game
    Player player = game.getPlayerInTurn();
    // Findus and Peddersen start with two mana
    // When Findus plays tries to play his "Tres" card, which costs three mana
    Status status = game.playCard(player, game.getCardInHand(player, 0));
    // Then it should return the status message "NOT ENOUGH MANA"
    assertThat(status, is(Status.NOT_ENOUGH_MANA));
  }

  @Test
  public void shouldPeddersenNotBeAbleToPlayCardWhenNotEnoughMana() {
    // Given a game
    game.endTurn();
    // When Peddersen plays a card, which costs more mana than he has
    Player player = game.getPlayerInTurn();
    //Is card tres
    Status status = game.playCard(player, game.getCardInHand(player, 0));
    // Then it should return status NOT_ENOUGH_MANA
    assertThat(status, is(Status.NOT_ENOUGH_MANA));
  }
  @Test
  public void shouldHaveBabyAsPeddersensHero(){
    // Given a game
    // When game starts
    // Peddersens hero should be "Baby"
    Player player = Player.PEDDERSEN;
    assertThat(game.getHero(player).getType(), is(GameConstants.BABY_HERO_TYPE));
  }

  @Test
  public void shouldReturnFindusAsOwnerOfFindusHero(){
    // Given a game
    // When checking the owner of Findus' hero
    // Then the owner should be FINDUS
    Player player = Player.FINDUS;
    assertThat(game.getHero(player).getOwner(), is(player));
  }

  @Test
  public void shouldReturnPeddersenAsOwnerOfPeddersenHero(){
    // Given a game
    // When checking the owner of Peddersens hero
    // Then the owner should be Peddersen
    Player player = Player.PEDDERSEN;
    assertThat(game.getHero(player).getOwner(), is(player));
  }

  @Test
  public void shouldStartWithTwoManaEach() {
    // Given a game
    // Then both players should start with two mana.
    assertThat(game.getHero(Player.FINDUS).getMana(), is(2));
    assertThat(game.getHero(Player.PEDDERSEN).getMana(), is(2));
  }

  @Test
  public void shouldGetTwoManaEachTurn() {
    // Given a game
    // Findus and Peddersen starts with two mana each
    // When skipping to Peddersens third turn
    game.endTurn();
    game.endTurn();
    game.endTurn();
    game.endTurn();
    game.endTurn();
    // Then both Findus and Peddersen should have two additional mana of top of their original amount
    assertThat(game.getHero(Player.FINDUS).getMana(), is(4));
    assertThat(game.getHero(Player.PEDDERSEN).getMana(), is(4));
    // Showing game
    TestHelper.printGameState(game);
  }
  @Test
  public void shouldFindusShouldLoseOneManaWhenPlayingCardUno(){
    // Given a game
    // When Findus plays his "Uno" card, which has a mana cost of one
    game.playCard(Player.FINDUS, game.getCardInHand(Player.FINDUS,2));
    //Then Findus should lose one mana
    assertThat(game.getHero(Player.FINDUS).getMana(), is(1));
    // Showing game
    TestHelper.printGameState(game);
  }
  @Test
  public void shouldFindusShouldLoseTwoManaWhenPlayingCardDos(){
    // Given a game
    // When Findus plays his "Dos" card, which has a mana cost of two
    game.playCard(Player.FINDUS, game.getCardInHand(Player.FINDUS,1));
    // Then Findus should lose two mana
    TestHelper.printGameState(game);
    assertThat(game.getHero(Player.FINDUS).getMana(), is(0));
  }

  @Test
  public void shouldPeddersenShouldLoseTwoManaWhenPlayingCardSeis(){
    // Given a game
    // Findus skips his first turn
    game.endTurn();
    // When Peddersen plays the cards "Dos"
    game.playCard(Player.PEDDERSEN, game.getCardInHand(Player.PEDDERSEN,1));
    // Then Peddersen should lose two mana
    TestHelper.printGameState(game);
    assertThat(game.getHero(Player.PEDDERSEN).getMana(), is(0));
    // Showing game
    TestHelper.printGameState(game);
  }

  @Test
  public void shouldFindusUsesHeroPower(){
    // Given a game
    // When Findus uses his heros power
    Status status = game.usePower(Player.FINDUS);
    // Then he should be allowed to
    assertThat(status, is(Status.OK));
  }
  @Test
  public void shouldFindusUsesHeroPowerTwoTimes(){
    // Given a game
    // When Findus tries to use his heros power twice in one turn
    game.usePower(Player.FINDUS);
    Status status = game.usePower(Player.FINDUS);
    // Then he is not allowed and is given the status message "power use not allowed twice pr round"
    assertThat(status, is(Status.POWER_USE_NOT_ALLOWED_TWICE_PR_ROUND));
  }

  @Test
  public void shouldNotAllowPeddersenToUsePowerWhenFindusTurn() {
    // Given a game
    // When Peddersen tries to use his heros power on Findus' turn
    Status status = game.usePower(Player.PEDDERSEN);
    // Then he is not allowed and is given the status message "not player in turn"
    assertThat(status, is(Status.NOT_PLAYER_IN_TURN));
  }

  @Test
  public void shouldFindusBeAbleToUseHeroPowerAgainNextTurn(){
    // Given a game
    // Findus uses hero power
    game.usePower(Player.FINDUS);
    // When on Findus' turn again
    game.endTurn();
    game.endTurn();
    // Then Findus should be able to use his heros power once again
    Status status = game.usePower(Player.FINDUS);
    assertThat(status, is(Status.OK));
  }

  @Test
  public void ShouldCostTwoManaWhenFindusUsesPower(){
    // Given a game
    // When Findus use his heros power, which cost two mana
    int oldMana = game.getHero(Player.FINDUS).getMana();
    game.usePower(Player.FINDUS);
    // Then Findus loses two mana
    assertThat(game.getHero(Player.FINDUS).getMana(), is(oldMana - 2));
    // Showing game
    TestHelper.printGameState(game);
  }

  @Test
  public void ShouldCostTwoManaWhenPeddersenUsesPower(){
    // Given a game
    // Findus skips his first turn
    game.endTurn();
    // When Peddersen use his heros power
    int oldMana = game.getHero(Player.PEDDERSEN).getMana();
    game.usePower(Player.PEDDERSEN);
    //Then Peddersen loses two mana
    assertThat(game.getHero(Player.PEDDERSEN).getMana(), is(oldMana - 2));
  }

  @Test
  public void shouldNotBeAbleToUsePowerWhenNotEnoughMana() {
    // Given a game
    // Findus Plays his card "Dos" and has zero mana left
    game.playCard(game.getPlayerInTurn(), game.getCardInHand(game.getPlayerInTurn(), 1));
    // When Findus tries to use his heros power
    Status status = game.usePower(game.getPlayerInTurn());
    // Then he should not be able to and is given the status message "not enough mana"
    assertThat(status, is(Status.NOT_ENOUGH_MANA));
  }

  @Test
  public void shouldFindusCardBeInactiveOnTurnPlayed(){
    // Given a game
    // Findus plays his card "Dos"
    game.playCard(Player.FINDUS, game.getCardInHand(Player.FINDUS,1));
    // Then this card should be inactive on its first turn
    Card card = game.getCardInField(Player.FINDUS, 0);
    assertThat(card.isActive(), is(Boolean.FALSE));
  }

  @Test
  public void shouldFindusCardBeActiveOnTurnsAfterPlayed(){
    // Given a game
    // Findus plays his card "Dos"
    game.playCard(Player.FINDUS, game.getCardInHand(Player.FINDUS,1));
    // When it is Findus' turn again
    game.endTurn();
    game.endTurn();
    //Then his card "Dos" should now be active
    Card card = game.getCardInField(Player.FINDUS, 0);
    assertThat(card.isActive(), is(Boolean.TRUE));
  }
  @Test
  public void shouldAllowFindusToAttackWithAnActiveCard(){
    // Given a game
    // Findus play his card "Dos" on his turn
    Card dosFindus = game.getCardInHand(Player.FINDUS,1);
    game.playCard(Player.FINDUS, dosFindus);
    game.endTurn();
    // Peddersen plays his card "Dos" on his turn
    Card dosPeddersen = game.getCardInHand(Player.PEDDERSEN,1);
    game.playCard(Player.PEDDERSEN, dosPeddersen);
    game.endTurn();
    // It is now Findus' turn again
    // When Findus tries to attack Peddersens "Dos" card with his own "Dos" card
    Status status = game.attackCard(Player.FINDUS, dosFindus, dosPeddersen);
    // Then it should be allowed
    assertThat(status, is(Status.OK));
  }

  @Test
  public void shouldNotAllowPeddersenToAttackWithAnInactiveCard(){
    // Given a game
    // Findus play card "Uno" on his turn
    Card uno = game.getCardInHand(Player.FINDUS,2);
    game.playCard(Player.FINDUS, uno);
    game.endTurn();
    // Peddersen plays card Dos on his turn
    Card dos = game.getCardInHand(Player.PEDDERSEN,1);
    game.playCard(Player.PEDDERSEN, dos);
    // When Peddersen tries to attack Findus' "Uno" with Peddersens own "Dos" card
    Status status = game.attackCard(Player.PEDDERSEN, dos, uno);
    // Then it should not be allowed and the status message "attack not allawed for non-active minion" is given
    assertThat(status, is(Status.ATTACK_NOT_ALLOWED_FOR_NON_ACTIVE_MINION));
  }

  @Test
  public void shouldNotAllowFindusToAttackHisOwnMinions(){
    // Given a game
    // Findus play card "Uno" on his turn
    Card uno = game.getCardInHand(Player.FINDUS,2);
    game.playCard(Player.FINDUS, uno);
    game.endTurn();
    game.endTurn();
    // Findus plays "Tres" on his next turn
    Card tres = game.getCardInHand(Player.FINDUS,0);
    game.playCard(Player.FINDUS, tres);
    // When Findus tries to attack "Tres" with his own "Uno"
    Status status = game.attackCard(Player.FINDUS, uno, tres);
    // Then he is not allowed and get the status message "attack not allowed on own minion"
    assertThat(status, is(Status.ATTACK_NOT_ALLOWED_ON_OWN_MINION));
  }

  @Test
  public void shouldNotAllowPeddersenToAttackWithFindusMinion(){
    // Given a game
    // Findus play card "Uno" on his turn
    Card uno = game.getCardInHand(Player.FINDUS,2);
    game.playCard(Player.FINDUS, uno);
    game.endTurn();
    // Peddersen skips his turn
    game.endTurn();
    // Findus plays "Tres" on his next turn
    Card tres = game.getCardInHand(Player.FINDUS,0);
    game.playCard(Player.FINDUS, tres);
    game.endTurn();
    // It is Peddersen turn again
    // When Peddersen tries to attack Findus' "Uno" with his "Tres"
    Status status = game.attackCard(Player.PEDDERSEN, uno, tres);
    // Then he is not allowed and get the status message "not owner"
    assertThat(status, is(Status.NOT_OWNER));
  }

  @Test
  public void shouldNotAllowPeddersenToAttackOnFindusTurn(){
    // Given a game
    // Findus play card "Uno" on his turn
    Card uno = game.getCardInHand(Player.FINDUS,2);
    game.playCard(Player.FINDUS, uno);
    game.endTurn();
    // Peddersen skips his turn
    game.endTurn();
    // Findus plays "Tres" on his next turn
    Card tres = game.getCardInHand(Player.FINDUS,0);
    game.playCard(Player.FINDUS, tres);
    // When Peddersen tries to attack "Uno" with "Tres" on Findus' turn
    Status status = game.attackCard(Player.PEDDERSEN, uno, tres);
    // Then he is not allowed and gets the status message "not player in turn"
    assertThat(status, is(Status.NOT_PLAYER_IN_TURN));
  }

  @Test
  public void shouldNotAllowFindusToAttackTwiceWithOneMinion(){
    // Given a game
    // Findus play card "Uno" on his turn
    Card uno = game.getCardInHand(Player.FINDUS,2);
    game.playCard(Player.FINDUS, uno);
    game.endTurn();
    // Peddersen plays card "Dos" on his turn
    Card dos = game.getCardInHand(Player.PEDDERSEN,1);
    game.playCard(Player.PEDDERSEN, dos);
    game.endTurn();
    // Findus plays "Tres" on his next turn
    Card tres = game.getCardInHand(Player.FINDUS,0);
    game.playCard(Player.FINDUS, tres);
    game.endTurn();
    // Peddersen attacks Findus' "Uno" with Peddersens "Dos"
    game.attackCard(Player.PEDDERSEN, dos, uno);
    // When Peddersen tries to attack Findus' "Tres" with Peddersens "Dos"
    Status status = game.attackCard(Player.PEDDERSEN, dos, tres);
    // Then he is not allowed and get the status message "attack not allowed for non-active minion"
    assertThat(status, is(Status.ATTACK_NOT_ALLOWED_FOR_NON_ACTIVE_MINION));
    // Showing game
    TestHelper.printGameState(game);
  }

  @Test
  public void shouldDeckSizeBeFourWhenStartingGameForBothPlayers(){
    // Given a game
    // When game starts
    // Then the deck size for both players should be four
    assertThat(game.getDeckSize(Player.FINDUS), is(4));
    assertThat(game.getDeckSize(Player.PEDDERSEN), is(4));
    // Showing game
    TestHelper.printGameState(game);
  }

  @Test
  public void shouldFindusDrawCardAtStartOfTurn(){
    // Given a game
    // Skipping both players' first round
    game.endTurn();
    game.endTurn();
    // When checking if Findus has drawn the next card, named "Cuatro", to the zero index of his hand
    // Then it is true
    assertThat(game.getCardInHand(Player.FINDUS, 0).getName(), is(GameConstants.CUATRO_CARD));
    // Showing game
    TestHelper.printGameState(game);
  }

  @Test
  public void shouldFindusDeckSizeOnNextTurnBeThree(){
    // Given a game
    // When skipping both players' first round, and it is Findus' second round
    game.endTurn();
    game.endTurn();
    // Then the deck size should be three for findus
    assertThat(game.getDeckSize(Player.FINDUS), is(3));
  }

  @Test
  public void shouldOnFindusSecondTurnPeddersenDeckSizeBeFourAndFindusDeckSizeBeThree(){
    // Given a game
    // When skipping both players' first round, and it is Findus' second round
    game.endTurn();
    game.endTurn();
    // Then the deck size should be four for Peddersen
    TestHelper.printGameState(game);
    assertThat(game.getDeckSize(Player.PEDDERSEN), is(4));
  }

  @Test
  public void shouldHaveSpanishDeckFindus() {
    // Given a game
    // When skipping to Findus' fifth turn
    game.endTurn();
    game.endTurn();
    game.endTurn();
    game.endTurn();
    game.endTurn();
    game.endTurn();
    game.endTurn();
    game.endTurn();
    // Then his hand should be "Siete Seis Cinco Cuatro Tres Dos Uno"
    ArrayList<Card> hand = (ArrayList<Card>) game.getHand(game.getPlayerInTurn());
    assertThat(hand.get(0).getName(), is(GameConstants.SIETE_CARD));
    assertThat(hand.get(1).getName(), is(GameConstants.SEIS_CARD));
    assertThat(hand.get(2).getName(), is(GameConstants.CINCO_CARD));
    assertThat(hand.get(3).getName(), is(GameConstants.CUATRO_CARD));
    assertThat(hand.get(4).getName(), is(GameConstants.TRES_CARD));
    assertThat(hand.get(5).getName(), is(GameConstants.DOS_CARD));
    assertThat(hand.get(6).getName(), is(GameConstants.UNO_CARD));
    // Showing game
    TestHelper.printGameState(game);
  }

  @Test
  public void shouldHaveSpanishDeckPeddersen() {
    // Given a game
    // When skipping to Peddersen' fifth turn
    game.endTurn();
    game.endTurn();
    game.endTurn();
    game.endTurn();
    game.endTurn();
    game.endTurn();
    game.endTurn();
    game.endTurn();
    game.endTurn();
    // Then his hand should be "Siete Seis Cinco Cuatro Tres Dos Uno"
    ArrayList<Card> hand = (ArrayList<Card>) game.getHand(game.getPlayerInTurn());
    assertThat(hand.get(0).getName(), is(GameConstants.SIETE_CARD));
    assertThat(hand.get(1).getName(), is(GameConstants.SEIS_CARD));
    assertThat(hand.get(2).getName(), is(GameConstants.CINCO_CARD));
    assertThat(hand.get(3).getName(), is(GameConstants.CUATRO_CARD));
    assertThat(hand.get(4).getName(), is(GameConstants.TRES_CARD));
    assertThat(hand.get(5).getName(), is(GameConstants.DOS_CARD));
    assertThat(hand.get(6).getName(), is(GameConstants.UNO_CARD));
    // Showing game
    TestHelper.printGameState(game);
  }

  @Test
  public void shouldReturnOneHealthOnUnoAfterPlayingIt() {
    // Given a game
    Player player = game.getPlayerInTurn();
    // Findus plays his "Uno" card
    game.playCard(player, game.getCardInHand(player, 2));
    // When checking the health of Findus' "Uno" card
    // Then it should show one health
    assertThat(game.getCardInField(player, 0).getHealth(), is(1));
  }

  @Test
  public void shouldLoseOneHealthOnCardDosWhenAttackedByUno() {
    // Given a game
    // Findus plays "Uno"
    game.playCard(game.getPlayerInTurn(), game.getCardInHand(game.getPlayerInTurn(), 2));
    game.endTurn();
    // Peddersen plays "Dos"
    game.playCard(game.getPlayerInTurn(), game.getCardInHand(game.getPlayerInTurn(), 1));
    game.endTurn();
    // Findus attacks Peddersens "Dos" with his own "Uno"
    Card uno = game.getCardInField(Player.FINDUS, 0);
    Card dos = game.getCardInField(Player.PEDDERSEN, 0);
    game.attackCard(game.getPlayerInTurn(), uno, dos);
    // When checking the health of Peddersens "Dos"
    // Then shows that "Dos" has one health left
    assertThat(dos.getHealth(), is(1));
    // Showing game
    TestHelper.printGameState(game);
  }

  @Test
  public void shouldLoseOneHealthOnCardTresWhenAttackedByUno() {
    // Given a game
    // Skipping the first round
    game.endTurn();
    game.endTurn();
    // Findus plays "Dos"
    game.playCard(game.getPlayerInTurn(), game.getCardInHand(game.getPlayerInTurn(), 3));
    game.endTurn();
    // Peddersen plays "Tres"
    game.playCard(game.getPlayerInTurn(), game.getCardInHand(game.getPlayerInTurn(), 1));
    game.endTurn();
    // Findus attacks Peddersens "Tres" with his own "Dos"
    Card uno = game.getCardInField(Player.FINDUS, 0);
    Card tres = game.getCardInField(Player.PEDDERSEN, 0);
    game.attackCard(game.getPlayerInTurn(), uno, tres);
    // When checking Peddersens "Tres"
    // Then shows that "Tres" has two health left
    assertThat(tres.getHealth(), is(2));
  }

  @Test
  public void shouldUnoDieAfterBeingAttackedByDos() {
    // Given a game
    // Findus plays "Dos"
    game.playCard(game.getPlayerInTurn(), game.getCardInHand(game.getPlayerInTurn(), 1));
    game.endTurn();
    // Peddersen plays "Uno"
    game.playCard(game.getPlayerInTurn(), game.getCardInHand(game.getPlayerInTurn(), 2));
    game.endTurn();
    // When Findus attacks Peddersens "Uno" with his own "Dos"
    Card dos = game.getCardInField(Player.FINDUS, 0);
    Card uno = game.getCardInField(Player.PEDDERSEN, 0);
    game.attackCard(game.getPlayerInTurn(), dos, uno);
    // Then Peddersens "Uno" should die, i.e., it should be removed from Peddersen's field
    assertThat(((ArrayList<Card>) game.getField(Player.PEDDERSEN)).contains(uno), is(false));
    // Showing game
    TestHelper.printGameState(game);
  }

  @Test
  public void shouldLoseHealthAsAttackingCard() {
    // Given a game
    // Findus plays "Dos"
    game.playCard(game.getPlayerInTurn(), game.getCardInHand(game.getPlayerInTurn(), 1));
    game.endTurn();
    // Peddersen plays "Uno"
    game.playCard(game.getPlayerInTurn(), game.getCardInHand(game.getPlayerInTurn(), 2));
    game.endTurn();
    // When Findus attacks Peddersens "Uno" with his own "Dos"
    Card dos = game.getCardInField(Player.FINDUS, 0);
    Card uno = game.getCardInField(Player.PEDDERSEN, 0);
    game.attackCard(game.getPlayerInTurn(), dos, uno);
    // Then Findus' "Dos" should lose one health left from attacking Peddersens "Uno"
    assertThat(dos.getHealth(), is(1));
  }

  @Test
  public void shouldUnoDieWhenAttackingDos() {
    // Given a game
    // Findus plays "Uno"
    game.playCard(game.getPlayerInTurn(), game.getCardInHand(game.getPlayerInTurn(), 2));
    game.endTurn();
    // Peddersen plays "Dos"
    game.playCard(game.getPlayerInTurn(), game.getCardInHand(game.getPlayerInTurn(), 1));
    game.endTurn();
    // When Findus attacks Peddersens "Dos" with his own "Uno"
    Card uno = game.getCardInField(Player.FINDUS, 0);
    Card dos = game.getCardInField(Player.PEDDERSEN, 0);
    game.attackCard(game.getPlayerInTurn(), uno, dos);
    // Then Findus' "Uno" should die
    assertThat(((ArrayList<Card>) game.getField(Player.FINDUS)).contains(uno), is(false));
    // Showing game
    TestHelper.printGameState(game);
  }

  @Test
  public void shouldStartWith21HealthOnHero() {
    // Given a game
    // When game starts
    // Then both heros should have twenty-one health
    TestHelper.printGameState(game);
    assertThat(game.getHero(Player.FINDUS).getHealth(), is(21));
    assertThat(game.getHero(Player.PEDDERSEN).getHealth(), is(21));
    // Showing game
    TestHelper.printGameState(game);
  }

  @Test
  public void shouldLoseOneHealthOnPeddersensHeroAfterAttackedByUnoCard() {
    // Given a game
    Player player = game.getPlayerInTurn();
    // Findus plays "Uno"
    game.playCard(player, game.getCardInHand(player, 2));
    game.endTurn();
    // Peddersen skips his turn
    game.endTurn();
    // When Findus attacks Peddersens hero with his own "Uno"
    game.attackHero(player, game.getCardInField(player, 0));
    // Then Peddersens hero should lose one health and have twenty left
    assertThat(game.getHero(Player.PEDDERSEN).getHealth(), is(20));
    // Showing game
    TestHelper.printGameState(game);
  }

  @Test
  public void shouldLoseTwoHealthOnFindusHeroAfterAttackedByDosCard() {
    // Given a game
    // Findus skips his turn
    game.endTurn();
    // Peddersen plays "Dos"
    Player player = game.getPlayerInTurn();
    game.playCard(player, game.getCardInHand(player, 1));
    game.endTurn();
    // Findus skips his second turn
    game.endTurn();
    // When Peddersen attack Findus' hero with Peddersens "Dos"
    game.attackHero(player, game.getCardInField(player, 0));

    // Then Findus' hero loses two health and have nineteen left
    assertThat(game.getHero(Player.FINDUS).getHealth(), is(19));
    // Showing game
    TestHelper.printGameState(game);
  }

  @Test
  public void shouldFindusGetOKWhenAttackingPeddersenHero(){
    //Given a game
    // When Findus plays uno, then waits for his turn, then attacks Findus himself
    Player player = game.getPlayerInTurn();
    game.playCard(player, game.getCardInHand(player, 2));
    game.endTurn();
    game.endTurn();

    assertThat(game.attackHero(player, game.getCardInField(player, 0)), is(Status.OK));
  }
}
