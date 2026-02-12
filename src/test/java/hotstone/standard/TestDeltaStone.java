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

import hotstone.framework.card.Card;
import hotstone.framework.game.Game;
import hotstone.framework.Player;
import hotstone.standard.factories.FactoryDelta;
import hotstone.utility.TestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/** Template for your own ongoing TDD process.
 * Fill it out until you have covered all
 * requirements for the alpha stone game.
 */
public class TestDeltaStone {
  private Game game;
  /** Fixture for AlphaStone testing. */
  @BeforeEach
  public void setUp() {
    game = new StandardHotStoneGame(new FactoryDelta());
  }

  @Test
  public void shouldHaveFoodDeckFindus() {
    // Given a game
    // Then his hand should be different kind of meals.
    ArrayList<Card> hand = (ArrayList<Card>) game.getHand(game.getPlayerInTurn());

    assertThat(hand.get(2).getManaCost()==1, is(true));
    assertThat(hand.get(1).getManaCost()<=2, is(true));
    assertThat(hand.get(0).getManaCost()<=4, is(true));
    // Showing game
    TestHelper.printGameState(game);
  }

  @Test
  public void shouldHaveFoodDeckPeddersen() {
    // Given a game
    game.endTurn();
    // Then his hand should be different kind of meals.
    ArrayList<Card> hand = (ArrayList<Card>) game.getHand(game.getPlayerInTurn());
    TestHelper.printGameState(game);

    assertThat(hand.get(2).getManaCost()==1, is(true));
    assertThat(hand.get(1).getManaCost()<=2, is(true));
    assertThat(hand.get(0).getManaCost()<=4, is(true));
  }

  @Test
  public void shouldStartGameWithSevenMana(){
    // Given a game
    // Then Findus starts with 7 mana
    assertThat(game.getHero(Player.FINDUS).getMana(), is(7));
    // And Peddersen starts with 7 mana
    assertThat(game.getHero(Player.PEDDERSEN).getMana(), is(7));
    // Showing game
    TestHelper.printGameState(game);
  }
  @Test
  public void shouldHaveCorrectDeckSize() {
    // Given a game
    // Then his hand should be different kind of meals.
    int Size = game.getDeckSize(game.getPlayerInTurn());

    assertThat(Size, is(24-3));
    game.endTurn();
    assertThat(Size, is(24-3));
  }
}
