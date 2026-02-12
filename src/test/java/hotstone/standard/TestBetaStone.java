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
import hotstone.framework.game.Game;
import hotstone.standard.factories.FactoryBeta;
import hotstone.utility.TestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/** Template for your own ongoing TDD process.
 * Fill it out until you have covered all
 * requirements for the alpha stone game.
 */
public class TestBetaStone {
  private Game game;
  /** Fixture for AlphaStone testing. */
  @BeforeEach
  public void setUp() {
    game = new StandardHotStoneGame(new FactoryBeta());
  }

  @Test
  public void shoulComputeBetaVersionOfManaProduction() {
    // Given a game
    // When 20 rounds have gone by
    for (int i = 0; i<20; i++){
      game.endTurn();
    }
    // Then mana production for both is seven
    assertThat(game.getHero(Player.FINDUS).getMana(), is(7));
    assertThat(game.getHero(Player.PEDDERSEN).getMana(), is(7));
    // Showing game
    TestHelper.printGameState(game);
  }
  @Test
  public void shouldFindusWinAfterOpponentHeroLosesAllHealth() {
    // Given a game
    // When Findus Plays DOS
    Player player = game.getPlayerInTurn();
    game.playCard(player, game.getCardInHand(player, 1));
    game.endTurn();
    // Peddersen does nothing
    game.endTurn();
    // Findus attacks Peddersens hero with DOS. Peddersen's hero has 19 health left
    game.attackHero(player, game.getCardInField(player, 0));
    game.endTurn();
    // Peddersen does nothing
    game.endTurn();
    // Findus attacks Peddersens hero with DOS. Peddersen's hero has 17 health left
    game.attackHero(player, game.getCardInField(player, 0));
    game.endTurn();
    // Peddersen does nothing
    game.endTurn();
    // Findus attacks Peddersens hero with DOS. Peddersen's hero has 15 health left
    game.attackHero(player, game.getCardInField(player, 0));
    game.endTurn();
    // Peddersen does nothing
    game.endTurn();
    // Findus attacks Peddersens hero with DOS. Peddersen's hero has 13 health left
    game.attackHero(player, game.getCardInField(player, 0));
    game.endTurn();
    // Peddersen does nothing
    game.endTurn();
    // Findus attacks Peddersens hero with DOS. Peddersen's hero has 11 health left
    game.attackHero(player, game.getCardInField(player, 0));
    game.endTurn();
    // Peddersen does nothing
    game.endTurn();
    // Findus attacks Peddersens hero with DOS. Peddersen's hero has 9 health left
    game.attackHero(player, game.getCardInField(player, 0));
    game.endTurn();
    // Peddersen does nothing
    game.endTurn();
    // Findus attacks Peddersens hero with DOS. Peddersen's hero has 7 health left
    game.attackHero(player, game.getCardInField(player, 0));
    game.endTurn();
    // Peddersen does nothing
    game.endTurn();
    // Findus attacks Peddersens hero with DOS. Peddersen's hero has 5 health left
    game.attackHero(player, game.getCardInField(player, 0));
    game.endTurn();
    // Peddersen does nothing
    game.endTurn();
    // Findus attacks Peddersens hero with DOS. Peddersen's hero has 3 health left
    game.attackHero(player, game.getCardInField(player, 0));
    game.endTurn();
    // Peddersen does nothing
    game.endTurn();
    // Findus attacks Peddersens hero with DOS. Peddersen's hero has 1 health left
    game.attackHero(player, game.getCardInField(player, 0));
    game.endTurn();
    // Peddersen does nothing
    game.endTurn();
    // Findus attacks Peddersens hero with DOS. Peddersen's hero has -1 health left
    game.attackHero(player, game.getCardInField(player, 0));
    // When Peddersen's hero has less the zero health left
    // Then Findus is the winner of the game
    assertThat(game.getWinner(), is(Player.FINDUS));
  }
  @Test
  public void shouldPeddersenWinAfterOpponentHeroLosesAllHealth() {
    // Given a game
    // Findus does nothing
    game.endTurn();
    // Peddersen Plays DOS
    Player player = game.getPlayerInTurn();
    game.playCard(player, game.getCardInHand(player, 1));
    game.endTurn();
    // Findus does nothing
    game.endTurn();
    // Peddersen attacks Findus hero with DOS. Findus' hero has 19 health left
    game.attackHero(player, game.getCardInField(player, 0));
    game.endTurn();
    // Findus does nothing
    game.endTurn();
    // Peddersen attacks Findus hero with DOS. Findus' hero has 17 health left
    game.attackHero(player, game.getCardInField(player, 0));
    game.endTurn();
    // Findus does nothing
    game.endTurn();
    // Peddersen attacks Findus hero with DOS. Findus' hero has 16 health left
    game.attackHero(player, game.getCardInField(player, 0));
    game.endTurn();
    // Findus does nothing
    game.endTurn();
    // Peddersen attacks Findus hero with DOS. Findus' hero has 13 health left
    game.attackHero(player, game.getCardInField(player, 0));
    game.endTurn();
    // Findus does nothing
    game.endTurn();
    // Peddersen attacks Findus hero with DOS. Findus' hero has 11 health left
    game.attackHero(player, game.getCardInField(player, 0));
    game.endTurn();
    // Findus does nothing
    game.endTurn();
    // Peddersen attacks Findus hero with DOS. Findus' hero has 9 health left
    game.attackHero(player, game.getCardInField(player, 0));
    game.endTurn();
    // Findus does nothing
    game.endTurn();
    // Peddersen attacks Findus hero with DOS. Findus' hero has 7 health left
    game.attackHero(player, game.getCardInField(player, 0));
    game.endTurn();
    // Findus does nothing
    game.endTurn();
    // Peddersen attacks Findus hero with DOS. Findus' hero has 5 health left
    game.attackHero(player, game.getCardInField(player, 0));
    game.endTurn();
    // Findus does nothing
    game.endTurn();
    // Peddersen attacks Findus hero with DOS. Findus' hero has 3 health left
    game.attackHero(player, game.getCardInField(player, 0));
    game.endTurn();
    // Findus does nothing
    game.endTurn();
    // Peddersen attacks Findus hero with DOS. Findus' hero has 1 health left
    game.attackHero(player, game.getCardInField(player, 0));
    game.endTurn();
    // Findus does nothing
    game.endTurn();
    // Peddersen attacks Findus hero with DOS. Findus' hero has -1 health left
    game.attackHero(player, game.getCardInField(player, 0));
    game.endTurn();
    // When Findus' hero has less the zero health left
    // Then Peddersen is the winner of the game
    assertThat(game.getWinner(), is(Player.PEDDERSEN));
  }

  @Test
  public void shouldNotChangeWinnerAfterItIsAlreadyChosen() {
    // Given a game
    // When both players take deck fatigue damage until they both have 1 health left
    for (int i = 0; i < 2*4+21; i++)
      game.endTurn();

    assertThat(game.getHero(Player.FINDUS).getHealth() > 0, is(true));
    assertThat(game.getHero(Player.PEDDERSEN).getHealth() > 0, is(true));

    // Then Peddersen should win the turn after since Findus dies
    game.endTurn();
    game.endTurn();
    assertThat(game.getWinner(), is(Player.PEDDERSEN));

    // When Peddersen then also dies from deck fatigue
    game.endTurn();
    game.endTurn();
    // Then the winner should be unchanged
    assertThat(game.getWinner(), is(Player.PEDDERSEN));
  }

  @Test
  public void shouldNotHaveWinnerAtBeginning(){
    // Given a game
    // When game starts
    // Then no winner is set
    assertThat(game.getWinner(), is(nullValue()));
  }

  @Test
  public void shouldDieFromFatigue() {
    // Given a game
    // When I end the turn for both players 4 times
    // and then end turn 2*4 + 19 times
    for (int i = 0; i < 2*4+21; i++)
      game.endTurn();

    TestHelper.printGameState(game);
    // Then the player should survive
    assertThat(game.getHero(Player.FINDUS).getHealth()<=0, is(false));
    game.endTurn();
    game.endTurn();


    // Then after two more turns, the player should die from deck fatigue
    assertThat(game.getHero(Player.FINDUS).getHealth()<=0, is(true));
  }
}
