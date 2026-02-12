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

import frds.broker.ClientRequestHandler;
import frds.broker.Invoker;
import frds.broker.Requestor;
import frds.broker.marshall.json.StandardJSONRequestor;
import hotstone.broker.client.GameClientProxy;
import hotstone.broker.doubles.LocalMethodClientRequestHandler;
import hotstone.broker.server.HotStoneRootInvoker;
import hotstone.framework.Player;
import hotstone.framework.card.Card;
import hotstone.framework.game.Game;
import hotstone.standard.factories.FactoryAlpha;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/** Skeleton test case for a 'depth-first' test-driven
 * development process to develop the Broker implementation
 * of client proxies and invokers, for all 'primitive' methods
 * in Game, that is, methods that do NOT take objects as
 * parameters, only primitive types and Strings.
 */
public class TestCardBroker {
  private Game game;
  private Card card;

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
    card = game.getCardInHand(Player.FINDUS, 0);
  }

  @Test
  public void shouldReturnCorrectCardOwner() {
    Player owner = card.getOwner();
    assertThat(owner, is(Player.FINDUS));
  }

  @Test
  public void shouldReturnCorrectCardName() {
    String name = card.getName();
    assertThat(name, is(GameConstants.TRES_CARD));
  }

  @Test
  public void shouldReturnCorrectCardHealth() {
    int health = card.getHealth();
    assertThat(health, is(3));
  }

  @Test
  public void shouldReturnCorrectCardAttack() {
    int attack = card.getAttack();
    assertThat(attack, is(3));
  }

  @Test
  public void shouldReturnCorrectCardManaCost() {
    int mana = card.getManaCost();
    assertThat(mana, is(3));
  }

  @Test
  public void shouldReturnCorrectCardActiveness() {
    boolean active = card.isActive();
    assertThat(active, is(false));
  }
}
