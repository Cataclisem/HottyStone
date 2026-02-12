/*
 * Copyright (C) 2022. Henrik Bærbak Christensen, Aarhus University.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package hotstone.standard;

import hotstone.broker.server.HotStoneRootInvoker;
import hotstone.framework.Player;
import hotstone.framework.Status;
import hotstone.framework.Utility;
import hotstone.framework.card.Card;
import hotstone.framework.game.Game;

import hotstone.standard.factories.FactoryAlpha;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import frds.broker.ClientRequestHandler;
import frds.broker.Invoker;
import frds.broker.Requestor;
import frds.broker.marshall.json.StandardJSONRequestor;

import hotstone.broker.client.GameClientProxy;
import hotstone.broker.doubles.LocalMethodClientRequestHandler;

import java.util.ArrayList;

/** Skeleton test case for a 'depth-first' test-driven
 * development process to develop the Broker implementation
 * of client proxies and invokers, for all 'primitive' methods
 * in Game, that is, methods that do NOT take objects as
 * parameters, only primitive types and Strings.
 */
public class TestGameBroker {
  private Game game;

  @BeforeEach
  public void setup() {
    // === We start at the server side of the Broker pattern:
    // define the servant, next the invoker

    // Given a Servant game, here a test stub with canned output
    Game servant = new StandardHotStoneGame(new FactoryAlpha());
    // Which is injected into the dedicated Invoker which you must develop
    Invoker invoker = new HotStoneRootInvoker(servant);

    // === Next define the client side of the pattern:
    // the client request handler, the requestor, and the client proxy

    // Instead of a network-based client- and server request handler
    // we make a fake object CRH that talks directly with the injected invoker
    ClientRequestHandler crh =
            new LocalMethodClientRequestHandler(invoker);

    // Which is injected into the standard JSON requestor of the
    // FRDS.Broker library
    Requestor requestor = new StandardJSONRequestor(crh);

    // Which is finally injected into the GameClientProxy that
    // you must develop...
    game = new GameClientProxy(requestor);
  }
  @Test
  public void shouldHaveTurnNumber0() {
    // Test stub hard codes the turn number to 0
    assertThat(game.getTurnNumber(), is(0));
  }
  @Test
  public void shouldGetNoWinner() {
    assertThat(game.getWinner(), is(nullValue()));
  }
  @Test
  public void shouldBeFindusTurn() {
    // Test stub hard codes the turn number to 312
    assertThat(game.getPlayerInTurn(), is(Player.FINDUS));
  }
  @Test
  public void shouldGetHero() {
    assertThat(game.getHero(Player.FINDUS).getType(), is(GameConstants.BABY_HERO_TYPE));
    assertThat(game.getHero(Player.PEDDERSEN).getType(), is(GameConstants.BABY_HERO_TYPE));
  }
  @Test
  public void shouldGetDeckSize() {
    assertThat(game.getDeckSize(Player.FINDUS), is(4));
    assertThat(game.getDeckSize(Player.PEDDERSEN), is(4));
  }
  @Test
  public void shouldGetHandSize() {
    // Test stub hard codes the turn number to 312
    assertThat(game.getHandSize(Player.FINDUS), is(3));
    assertThat(game.getHandSize(Player.PEDDERSEN), is(3));
  }
  @Test
  public void shouldGetFieldSize0() {
    assertThat(game.getFieldSize(Player.FINDUS), is(0));
    assertThat(game.getFieldSize(Player.PEDDERSEN), is(0));
  }
  @Test
  public void shouldGetCardInHand() {
    assertThat(game.getCardInHand(Player.FINDUS,0).getName(), is(GameConstants.TRES_CARD));
    assertThat(game.getCardInHand(Player.PEDDERSEN,1).getName(), is(GameConstants.DOS_CARD));
  }
  @Test
  public void shouldGetCardInField() {
    Player player = Player.FINDUS;
    Card card = game.getCardInHand(player, 2);
    game.playCard(player, card);

    assertThat(game.getCardInField(Player.FINDUS,0).getName(), is(GameConstants.UNO_CARD));
  }
  @Test
  public void shouldGetHand() {
    ArrayList<Card> findusHand = (ArrayList<Card>) game.getHand(Player.FINDUS);
    ArrayList<Card> peddersenHand = (ArrayList<Card>) game.getHand(Player.PEDDERSEN);

    assertThat(findusHand.get(0).getName(), is(GameConstants.TRES_CARD));
    assertThat(findusHand.get(1).getName(), is(GameConstants.DOS_CARD));
    assertThat(findusHand.get(2).getName(), is(GameConstants.UNO_CARD));

    assertThat(peddersenHand.get(0).getName(), is(GameConstants.TRES_CARD));
    assertThat(peddersenHand.get(1).getName(), is(GameConstants.DOS_CARD));
    assertThat(peddersenHand.get(2).getName(), is(GameConstants.UNO_CARD));
  }
  @Test
  public void shouldGetFieldAfterTwoTurns() {
    Player player = game.getPlayerInTurn();
    Card card = game.getCardInHand(player, 2);
    game.playCard(player, card);
    game.endTurn();

    player = game.getPlayerInTurn();
    card = game.getCardInHand(player, 2);
    game.playCard(player, card);

    ArrayList<Card> findusField = (ArrayList<Card>) game.getField(Player.FINDUS);
    ArrayList<Card> peddersenField = (ArrayList<Card>) game.getField(Player.PEDDERSEN);

    assertThat(findusField.get(0).getName(), is(GameConstants.UNO_CARD));
    assertThat(peddersenField.get(0).getName(), is(GameConstants.UNO_CARD));
  }

  @Test
  public void shouldAllowCardAttackOnCard() {
    Player player = game.getPlayerInTurn();
    Card card = game.getCardInHand(player, 2);
    game.playCard(player, card);
    game.endTurn();

    player = game.getPlayerInTurn();
    card = game.getCardInHand(player, 2);
    game.playCard(player, card);
    game.endTurn();

    player = game.getPlayerInTurn();
    Card attackingCard = game.getCardInField(player, 0);
    Card defendingCard = game.getCardInField(Utility.computeOpponent(player), 0);
    Status status = game.attackCard(player, attackingCard, defendingCard);

    assertThat(status, is(Status.OK));
    assertThat(defendingCard.getHealth(), is(0));
  }

  @Test
  public void shouldAllowCardAttackOnHero() {
    Player player = game.getPlayerInTurn();
    Card card = game.getCardInHand(player, 2);
    game.playCard(player, card);
    game.endTurn();
    game.endTurn();

    Status status = game.attackHero(player, card);

    assertThat(status, is(Status.OK));
  }

  @Test
  public void shouldAllowPowerUse() {
    Player player = game.getPlayerInTurn();
    Status status = game.usePower(player);
    assertThat(status, is(Status.OK));
  }
}
