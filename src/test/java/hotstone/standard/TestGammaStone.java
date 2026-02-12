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
import hotstone.standard.factories.FactoryGamma;
import hotstone.utility.TestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/** Template for your own ongoing TDD process.
 * Fill it out until you have covered all
 * requirements for the alpha stone game.
 */
public class TestGammaStone {
  private Game game;
  /** Fixture for AlphaStone testing. */
  @BeforeEach
  public void setUp() {
    game = new StandardHotStoneGame(new FactoryGamma());
  }

  @Test
  public void shouldGiveCorrectHero(){
    // Given A game
    // Then Findus' hero is the type Thai chef
    assertThat(game.getHero(Player.FINDUS).getType(), is(GameConstants.THAI_CHEF_HERO_TYPE));
    // And Peddersen's hero is the3 type Danish chef
    assertThat(game.getHero(Player.PEDDERSEN).getType(), is(GameConstants.DANISH_CHEF_HERO_TYPE));
    // Showing game
    TestHelper.printGameState(game);
  }
  @Test
  public void shouldUseThaiChefPower(){
    // Given a Game
    // When Findus uses the power of his Thai chef
    game.usePower(Player.FINDUS);
    // Then Peddersen's hero loses two health and has 19 left
    assertThat(game.getHero(Player.PEDDERSEN).getHealth(),is(game.getHero(Player.FINDUS).getHealth()-2));
    // Showing game
    TestHelper.printGameState(game);
  }
  @Test
  public void shouldUseDanishChefPower(){
    // Given a game
    // Findus does nothing
    game.endTurn();
    // When Peddersen uses the power of his Danish chef
    game.usePower(Player.PEDDERSEN);
    // Then Peddersen is given the card "sovs" on his field
    assertThat((game.getCardInField(Player.PEDDERSEN,0)).getName(), is(GameConstants.SOVS_CARD));
    // Showing game
    TestHelper.printGameState(game);
  }
  @Test
  public void shouldGetCorrectPowerDescription(){
    // Given a game
    // When Peddersen is hero danish chef named "meyer"
    assertThat(game.getHero(Player.PEDDERSEN).getType(),is(GameConstants.DANISH_CHEF_HERO_TYPE));
    // Then his hero power description is "Field Sovs"
    assertThat(game.getHero(Player.PEDDERSEN).getPowerDesc(), is(GameConstants.DANISH_CHEF_HERO_POWER_DESC));
    // When Findus is hero Thai chef named "Bunyasaranand"
    assertThat(game.getHero(Player.FINDUS).getType(),is(GameConstants.THAI_CHEF_HERO_TYPE));
    // Then his hero power description is "Opp H: (0,-2)"
    assertThat(game.getHero(Player.FINDUS).getPowerDesc(),is(GameConstants.THAI_CHEF_HERO_POWER_DESC));
    // Showing game
    TestHelper.printGameState(game);
  }
}
