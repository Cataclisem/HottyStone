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

import demo.DemoDeck;
import hotstone.framework.Player;
import hotstone.framework.Status;
import hotstone.framework.card.Card;
import hotstone.framework.game.Game;
import hotstone.standard.factories.FactoryAlpha;
import hotstone.standard.specialDeckDecorator.TwoDeckStrategySpecialised;
import hotstone.utility.TestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import thirdparty.CardPODO;
import thirdparty.PersonalDeckReader;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;


/** Template for your own ongoing TDD process.
 * Fill it out until you have covered all
 * requirements for the alpha stone game.
 */
public class TestDemoDeck {
  private Game game;
  /** Fixture for AlphaStone testing. */
  @BeforeEach
  public void setUp() {
    game = new StandardHotStoneGame(new TwoDeckStrategySpecialised(new FactoryAlpha()));
  }

  // Example of an early, simple test case:
  // Turn handling

  @Test
  public void shouldHaveCorrectCardsFindus() {
    // The deck type "Norse Gods"
    ArrayList<Card> findus_Deck = new ArrayList<>();
    PersonalDeckReader fcards = DemoDeck.main("Norse Gods");
    for (CardPODO acard : fcards) {
      findus_Deck.add(new StandardCard(acard.name(), acard.mana(), acard.attack(), acard.health(), Player.FINDUS));
      findus_Deck.add(new StandardCard(acard.name(), acard.mana(), acard.attack(), acard.health(), Player.FINDUS));
    }
    // Given a game
    // Then Findus should have the deck type "Norse Gods"
    assertThat(game.getCardInHand(Player.FINDUS,2).getName(), is(findus_Deck.get(0).getName()));
    assertThat(game.getCardInHand(Player.FINDUS,1).getName(), is(findus_Deck.get(1).getName()));
    for (int i=0; i<findus_Deck.size()-game.getHandSize(Player.FINDUS); i++) {
      assertThat(game.getCardInHand(Player.FINDUS,0).getName(), is(findus_Deck.get(2+i).getName()));
      game.endTurn();
      game.endTurn();
    }
    TestHelper.printGameState(game);
  }
  @Test
  public void shouldHaveCorrectCardsPeddersen(){
    // The deck type "Animals"
    ArrayList<Card> peddersen_Deck = new ArrayList<>();
    PersonalDeckReader pcards = DemoDeck.main("Animals");
    for (CardPODO acard : pcards) {
      peddersen_Deck.add(new StandardCard(acard.name(), acard.mana(), acard.attack(), acard.health(), Player.PEDDERSEN));
      peddersen_Deck.add(new StandardCard(acard.name(), acard.mana(), acard.attack(), acard.health(), Player.PEDDERSEN));
    }
    // Given a game
    // Then Peddersen should have the deck type "Animals"
    game.endTurn();
    assertThat(game.getCardInHand(Player.PEDDERSEN,2).getName(), is(peddersen_Deck.get(0).getName()));
    assertThat(game.getCardInHand(Player.PEDDERSEN,1).getName(), is(peddersen_Deck.get(1).getName()));
    for (int i=0; i<peddersen_Deck.size()-game.getHandSize(Player.PEDDERSEN); i++) {
      assertThat(game.getCardInHand(Player.PEDDERSEN,0).getName(), is(peddersen_Deck.get(2+i).getName()));
      game.endTurn();
      game.endTurn();
    }
    TestHelper.printGameState(game);
  }
}
