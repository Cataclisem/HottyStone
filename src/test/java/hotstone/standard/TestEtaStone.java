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
import hotstone.standard.factories.FactoryEta;
import hotstone.standard.random.RandomSelectorDouble;
import hotstone.utility.TestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/** Template for your own ongoing TDD process.
 * Fill it out until you have covered all
 * requirements for the alpha stone game.
 */
public class TestEtaStone {
    private Game game;

    /**
     * Fixture for AlphaStone testing.
     */
    @BeforeEach
    public void setUp() {
        game = new StandardHotStoneGame(new FactoryEta(new RandomSelectorDouble(0)));
    }

    @Test
    public void shouldLoseOneHealthOnOpponentHeroWhenBrownRiceIsPlayed(){
        //Given a game
        Player player = game.getPlayerInTurn();
        Player opponent = Utility.computeOpponent(player);

        game.playCard(player, game.getCardInHand(player, 2));

        assertThat((game.getHero(opponent)).getHealth(), is(20));

        TestHelper.printGameState(game);
    }

    @Test
    public void shouldGiveOneAttackToOwnMinion(){
        //Given a game
        Player player = game.getPlayerInTurn();
        game.endTurn();
        game.endTurn();
        game.endTurn();
        game.endTurn();
        game.playCard(player,game.getCardInHand(player,0));
        game.endTurn();
        game.endTurn();
        game.playCard(player,game.getCardInHand(player,0));
        int oldAttack = game.getCardInField(player,0).getAttack();
        game.endTurn();
        game.endTurn();
        game.playCard(player,game.getCardInHand(player,0));
        int newAttack = game.getCardInField(player,0).getAttack();
        assertThat(newAttack, is(oldAttack + 1));
        TestHelper.printGameState(game);
    }
    @Test
    public void shouldGiveOneAttackToOwnMinionSelf(){
        //Given a game
        Player player = game.getPlayerInTurn();
        game.endTurn();
        game.endTurn();
        game.endTurn();
        game.endTurn();
        game.endTurn();
        game.endTurn();
        game.endTurn();
        game.endTurn();
        TestHelper.printGameState(game);
        int oldAttack = game.getCardInHand(player,0).getAttack();
        game.playCard(player,game.getCardInHand(player,0));
        int newAttack = game.getCardInField(player,0).getAttack();
        assertThat(newAttack, is(oldAttack));
        TestHelper.printGameState(game);
    }
    @Test
    public void shouldHealHeroTwoHealth(){
        //Given a game
        Player player = game.getPlayerInTurn();
        game.endTurn();
        game.endTurn();
        game.endTurn();
        game.endTurn();
        game.endTurn();
        game.endTurn();
        game.endTurn();
        game.endTurn();
        game.endTurn();
        game.endTurn();
        game.endTurn();
        game.endTurn();
        int oldHealth = game.getHero(player).getHealth();
        game.playCard(player,game.getCardInHand(player,0));
        int newHealth = game.getHero(player).getHealth();
        TestHelper.printGameState(game);
        assertThat(newHealth, is(oldHealth + 2));
    }
    @Test
    public void shouldGiveRandomCardFromDeck(){
        //Given a game
        Player player = game.getPlayerInTurn();
        game.endTurn();
        game.endTurn();
        game.endTurn();
        game.endTurn();
        game.endTurn();
        game.endTurn();
        game.endTurn();
        game.endTurn();
        game.endTurn();
        game.endTurn();
        game.endTurn();
        game.endTurn();
        game.endTurn();
        game.endTurn();
        game.endTurn();
        game.endTurn();
        game.endTurn();
        game.endTurn();
        game.endTurn();
        game.endTurn();
        int oldHandSize = game.getHandSize(player);
        game.playCard(player,game.getCardInHand(player,0));
        int newHandSize = game.getHandSize(player);
        TestHelper.printGameState(game);
        assertThat(newHandSize, is(oldHandSize));
    }
    @Test
    public void shouldKillRandomMinion(){
        //Given a game
        Player player = game.getPlayerInTurn();
        game.endTurn();
        Player opponent = game.getPlayerInTurn();
        game.endTurn();
        game.endTurn();
        game.playCard(opponent,game.getCardInHand(opponent,0));
        game.endTurn();
        game.endTurn();
        game.playCard(opponent,game.getCardInHand(opponent,0));
        game.endTurn();
        game.endTurn();
        game.playCard(opponent,game.getCardInHand(opponent,0));
        game.endTurn();
        game.endTurn();
        game.playCard(opponent,game.getCardInHand(opponent,0));
        game.endTurn();
        game.endTurn();
        game.playCard(opponent,game.getCardInHand(opponent,0));
        game.endTurn();
        game.endTurn();
        game.playCard(opponent,game.getCardInHand(opponent,0));
        game.endTurn();
        game.endTurn();
        game.playCard(opponent,game.getCardInHand(opponent,0));
        game.endTurn();
        game.endTurn();
        game.playCard(opponent,game.getCardInHand(opponent,0));
        game.endTurn();
        game.endTurn();
        game.playCard(opponent,game.getCardInHand(opponent,0));
        game.endTurn();
        game.endTurn();
        game.endTurn();
        game.endTurn();
        game.endTurn();
        game.endTurn();
        game.endTurn();
        game.endTurn();
        game.endTurn();
        game.endTurn();
        game.endTurn();
        game.endTurn();
        game.endTurn();
        int oldOppFieldSize = game.getFieldSize(opponent);
        game.playCard(player,game.getCardInHand(player,0));
        int newOppFieldSize = game.getFieldSize(opponent);
        TestHelper.printGameState(game);
        assertThat(newOppFieldSize, is(oldOppFieldSize - 1));
    }
    @Test
    public void shouldGiveTwoAttackToRandomEnemyMinion(){
        //Given a game
        Player player = game.getPlayerInTurn();
        game.endTurn();
        Player opponent = game.getPlayerInTurn();
        game.endTurn();
        game.endTurn();
        game.playCard(opponent,game.getCardInHand(opponent,0));
        game.endTurn();
        game.endTurn();
        game.playCard(opponent,game.getCardInHand(opponent,0));
        game.endTurn();
        game.endTurn();
        game.playCard(opponent,game.getCardInHand(opponent,0));
        game.endTurn();
        game.endTurn();
        game.playCard(opponent,game.getCardInHand(opponent,0));
        game.endTurn();
        game.endTurn();
        game.playCard(opponent,game.getCardInHand(opponent,0));
        game.endTurn();
        game.endTurn();
        game.playCard(opponent,game.getCardInHand(opponent,0));
        game.endTurn();
        game.endTurn();
        game.playCard(opponent,game.getCardInHand(opponent,0));
        game.endTurn();
        game.endTurn();
        game.playCard(opponent,game.getCardInHand(opponent,0));
        game.endTurn();
        game.endTurn();
        game.playCard(opponent,game.getCardInHand(opponent,0));
        game.endTurn();
        game.endTurn();
        game.endTurn();
        game.endTurn();
        game.endTurn();
        game.endTurn();
        game.endTurn();
        game.endTurn();
        game.endTurn();
        game.endTurn();
        game.endTurn();
        game.endTurn();
        game.endTurn();
        game.endTurn();
        game.endTurn();
        game.endTurn();
        game.endTurn();
        int oldAttack = game.getCardInField(opponent,0).getAttack();
        game.playCard(player,game.getCardInHand(player,0));
        int newAttack = game.getCardInField(opponent,0).getAttack();
        TestHelper.printGameState(game);
        assertThat(newAttack, is(oldAttack + 2));
    }

}