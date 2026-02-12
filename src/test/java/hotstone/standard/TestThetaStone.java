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
import hotstone.standard.factories.FactoryTheta;
import hotstone.standard.random.RandomSelectorDouble;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/** Template for your own ongoing TDD process.
 * Fill it out until you have covered all
 * requirements for the alpha stone game.
 */
public class TestThetaStone {
    private Game game;

    /**
     * Fixture for AlphaStone testing.
     */
    @BeforeEach
    public void setUp() {
        game = new StandardHotStoneGame(new FactoryTheta(new RandomSelectorDouble(0)));
    }

    @Test
    public void shouldNotBeAbleToAttackNonTauntMinionWhenTauntMinionIsOnTheField() {
        // Given a game
        // When Findus plays a non-taunt card and a taunt card
        for (int i = 0; i < 10; i++)
            game.endTurn();

        Player player = game.getPlayerInTurn();
        game.playCard(player, game.getCardInHand(player, 0));

        game.endTurn();
        player = game.getPlayerInTurn();
        game.playCard(player, game.getCardInHand(player, 0));

        game.endTurn();
        player = game.getPlayerInTurn();
        game.playCard(player, game.getCardInHand(player, 0));

        game.endTurn();
        player = game.getPlayerInTurn();
        Player opponent = Utility.computeOpponent(player);

        // and when Peddersen attacks a non-taunt card
        Status status = game.attackCard(player, game.getCardInField(player, 0), game.getCardInField(opponent, 0));

        // Then it should not be allowed
        assertThat(status, is(Status.ATTACK_NOT_ALLOWED_ON_NON_TAUNT_MINION));
    }

    @Test
    public void shouldHaveFourCardsAtBeginningOfGameOnPeddersenSide(){
        // Given a game
        // When Peddersen draws the "Musli Bar" card on top of the three cards at the beginning
        int findusHand = game.getHandSize(Player.FINDUS);
        int peddersenHand = game.getHandSize(Player.PEDDERSEN);
        // Then Peddersens should have four cards and Findus three cards.
        assertThat(findusHand, is(3));
        assertThat(peddersenHand, is(4));
    }
    @Test
    public void shouldRecieveMusliBarOnPeddersen() {
        // Given a game
        // Then Peddersen should have the Müsli-bar card in hand.
        Card musliBar = game.getCardInHand(Player.PEDDERSEN,3);
        assertThat(musliBar.getName(), is(GameConstants.MUSLI_BAR_CARD));
    }

    @Test
    public void shouldPeddersenHaveOneExtraManaAfterPlayingMusliBar(){
        //Given a game
        game.endTurn();
        //And it is Peddersens turn
        Player player = game.getPlayerInTurn();
        int oldMana = game.getHero(player).getMana();

        Card musliCard = game.getCardInHand(player, 3);
        game.playCard(player, musliCard);

        assertThat(game.getHero(player).getMana(), is(oldMana + 1));
    }

    @Test
    public void shouldMusliBarNotBeOnFieldWhenPlayed(){
        //Given a game
        //And Peddersen turn
        game.endTurn();

        Player player = game.getPlayerInTurn();
        Card musliCard = game.getCardInHand(player, 3);
        int fieldSize = game.getFieldSize(player);

        //And Peddersen plays muslibar
        game.playCard(player, musliCard);
        int newFieldSize = game.getFieldSize(player);

        //Then the field sizes should be the same
        assertThat(newFieldSize, is(fieldSize));
    }
}