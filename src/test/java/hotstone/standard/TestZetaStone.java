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

import hotstone.framework.game.Game;
import hotstone.framework.Player;
import hotstone.framework.Utility;
import hotstone.standard.factories.FactoryZeta;
import hotstone.utility.TestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/** Template for your own ongoing TDD process.
 * Fill it out until you have covered all
 * requirements for the alpha stone game.
 */
public class TestZetaStone {
  private Game game;
  /** Fixture for AlphaStone testing. */
  @BeforeEach
  public void setUp() {
    game = new StandardHotStoneGame(new FactoryZeta());
  }

  @Test
  public void shouldOnlyHaveCincoCards() {
    // Given a game
    // Then all cards in Findus' hand and deck should be Cinco
    Player player = game.getPlayerInTurn();

    for (int i = 0; i < 8; i++)
      game.endTurn();

    for (int i = 0; i < 7; i++)
      assertThat(game.getCardInHand(player, i).getName(), is(GameConstants.CINCO_CARD));


    // and then the same for Peddersen
    game.endTurn();
    player = game.getPlayerInTurn();

    for (int i = 0; i < 7; i++)
      assertThat(game.getCardInHand(player, i).getName(), is(GameConstants.CINCO_CARD));
  }

  @Test
  public void shouldWinWithHeroDeathBeforeTurn12() {
    // Given a game
    // When Findus defeats Peddersen's hero before turn 12
    game.endTurn();
    game.endTurn();

    Player player = game.getPlayerInTurn();
    for (int i = 0; i < 4; i++) {
      game.playCard(player, game.getCardInHand(player, 0));
      for (int j = 0; j < game.getFieldSize(player); j++) {
        game.attackHero(player, game.getCardInField(player, j));
      }
      game.endTurn();
      game.endTurn();
    }

    TestHelper.printGameState(game);
    assertThat(game.getWinner(), is(Player.FINDUS));
  }

  @Test
  public void shouldNotWinWithHeroDeathBeforeAfter12() {
    // Given a game
    // When Findus defeats Peddersen's hero before turn 12
    game.endTurn();
    game.endTurn();

    for (int i = 0; i < 12; i++)
      game.endTurn();

    Player player = game.getPlayerInTurn();
    for (int i = 0; i < 4; i++) {
      game.playCard(player, game.getCardInHand(player, 0));
      for (int j = 0; j < game.getFieldSize(player); j++) {
        game.attackHero(player, game.getCardInField(player, j));
      }
      game.endTurn();
      game.endTurn();
    }

    TestHelper.printGameState(game);
    assertThat(game.getWinner(), is(nullValue()));
  }

  @Test
  public void shouldNotWinWithMinionDamageBeforeTurn12() {
    // Given a game
    game.endTurn();
    game.endTurn();

    Player player = game.getPlayerInTurn();
    Player opponent = Utility.computeOpponent(player);

    // When Findus does more than 7 minion to minion damage before turn 12
    game.playCard(player, game.getCardInHand(player, 0));
    game.endTurn();

    game.playCard(opponent, game.getCardInHand(opponent, 0));
    game.endTurn();

    game.attackCard(player, game.getCardInField(player, 0), game.getCardInField(opponent, 0));
    game.playCard(player, game.getCardInHand(player, 0));
    game.endTurn();

    game.playCard(opponent, game.getCardInHand(opponent, 0));
    game.endTurn();

    game.attackCard(player, game.getCardInField(player, 0), game.getCardInField(opponent, 0));
    game.playCard(player, game.getCardInHand(player, 0));

    // Then Findus should not win.
    assertThat(game.getWinner(), is(nullValue()));
  }

  @Test
  public void shouldNotWinWithSomeMinionDamageBeforeTurn12AndRestAfter() {
    // Given a game
    game.endTurn();
    game.endTurn();

    Player player = game.getPlayerInTurn();
    Player opponent = Utility.computeOpponent(player);

    // When Findus does 5 minion to minion damage before turn 12
    game.playCard(player, game.getCardInHand(player, 0));
    game.endTurn();

    game.playCard(opponent, game.getCardInHand(opponent, 0));
    game.endTurn();

    game.attackCard(player, game.getCardInField(player, 0), game.getCardInField(opponent, 0));
    game.playCard(player, game.getCardInHand(player, 0));
    game.endTurn();

    for (int i = 0; i < 12; i++)
      game.endTurn();

    // and when Findus does an additional 5 damage after turn 12
    game.playCard(opponent, game.getCardInHand(opponent, 0));
    game.endTurn();

    game.attackCard(player, game.getCardInField(player, 0), game.getCardInField(opponent, 0));
    game.playCard(player, game.getCardInHand(player, 0));

    // Then Findus should not win.
    assertThat(game.getWinner(), is(nullValue()));
  }

  @Test
  public void shouldNotWinWithMinionDamageBeforeTurn12AndThenWaitingForTurn12() {
    // Given a game
    game.endTurn();
    game.endTurn();

    Player player = game.getPlayerInTurn();
    Player opponent = Utility.computeOpponent(player);

    // When Findus does more than 7 minion to minion damage before turn 12
    game.playCard(player, game.getCardInHand(player, 0));
    game.endTurn();

    game.playCard(opponent, game.getCardInHand(opponent, 0));
    game.endTurn();

    game.attackCard(player, game.getCardInField(player, 0), game.getCardInField(opponent, 0));
    game.playCard(player, game.getCardInHand(player, 0));
    game.endTurn();

    game.playCard(opponent, game.getCardInHand(opponent, 0));
    game.endTurn();

    game.attackCard(player, game.getCardInField(player, 0), game.getCardInField(opponent, 0));
    game.playCard(player, game.getCardInHand(player, 0));

    // and when the turn number is above 12
    for (int i = 0; i < 12; i++)
      game.endTurn();

    // Then Findus should not win.
    assertThat(game.getWinner(), is(nullValue()));
  }

  @Test
  public void shouldWinWithMinionDamageAfterTurn12() {
    // Given a game
    // When the turn number is above 12
    for (int i = 0; i < 12; i++)
      game.endTurn();

    Player player = game.getPlayerInTurn();
    Player opponent = Utility.computeOpponent(player);

    // When Findus does more than 7 minion to minion damage
    game.playCard(player, game.getCardInHand(player, 0));
    game.endTurn();

    game.playCard(opponent, game.getCardInHand(opponent, 0));
    game.endTurn();

    game.attackCard(player, game.getCardInField(player, 0), game.getCardInField(opponent, 0));
    game.playCard(player, game.getCardInHand(player, 0));
    game.endTurn();

    game.playCard(opponent, game.getCardInHand(opponent, 0));
    game.endTurn();

    game.attackCard(player, game.getCardInField(player, 0), game.getCardInField(opponent, 0));
    game.playCard(player, game.getCardInHand(player, 0));

    TestHelper.printGameState(game);
    // Then Findus should win.
    game.endTurn();
    assertThat(game.getWinner(), is(Player.FINDUS));
  }

  @Test
  public void shouldNotWinWithHeroDeathAfterTurn12() {
    // Given a game
    // When Findus defeats Peddersen's hero after turn 12
    for (int i = 0; i < 12; i++)
      game.endTurn();

    Player player = game.getPlayerInTurn();
    for (int i = 0; i < 4; i++) {
      game.playCard(player, game.getCardInHand(player, 0));
      for (int j = 0; j < game.getFieldSize(player); j++) {
        game.attackHero(player, game.getCardInField(player, j));
      }
      game.endTurn();
      game.endTurn();
    }

    // Then Findus should not win
    assertThat(game.getWinner(), is(nullValue()));
  }
}
