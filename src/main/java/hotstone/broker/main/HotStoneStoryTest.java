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

package hotstone.broker.main;

import frds.broker.Requestor;
import frds.broker.ipc.http.UriTunnelClientRequestHandler;
import frds.broker.marshall.json.StandardJSONRequestor;
import hotstone.broker.client.CardClientProxy;
import hotstone.broker.client.GameClientProxy;
import hotstone.broker.client.HeroClientProxy;
import hotstone.broker.common.BrokerConstants;
import hotstone.framework.Player;
import hotstone.framework.card.Card;
import hotstone.framework.game.Game;
import hotstone.framework.hero.Hero;

public class HotStoneStoryTest {
  public static void main(String[] args)  {
    // Get the name of the host from the commandline parameters
    String host = args[0];
    // and execute the story test, talking to the server with that name
    new HotStoneStoryTest(host);
  }

  public HotStoneStoryTest(String host) {
    // Create the client side Broker roles
    UriTunnelClientRequestHandler clientRequestHandler
            = new UriTunnelClientRequestHandler(host, BrokerConstants.HOTSTONE_PORT,
            false, BrokerConstants.HOTSTONE_TUNNEL_PATH);
    Requestor requestor = new StandardJSONRequestor(clientRequestHandler);

    Game game = new GameClientProxy(requestor);
    testSimpleMethods(game);
  }

  private void testSimpleMethods(Game game) {
    System.out.println("=== Testing pass-by-value methods of Game ===");
    System.out.println(" --> Game turnNumber            " + game.getTurnNumber());
    System.out.println(" --> Game winner                " + game.getWinner());
    System.out.println(" --> Game playerInTurn          " + game.getPlayerInTurn());
    System.out.println(" --> Game getHero Findus        " + game.getHero(Player.FINDUS));
    System.out.println(" --> Game getHero Peddersen     " + game.getHero(Player.PEDDERSEN));
    System.out.println(" --> Game deckSize Findus       " + game.getDeckSize(Player.FINDUS));
    System.out.println(" --> Game deckSize Peddersen    " + game.getDeckSize(Player.PEDDERSEN));
    System.out.println(" --> Game handSize Findus       " + game.getHandSize(Player.FINDUS));
    System.out.println(" --> Game handSize Peddersen    " + game.getHandSize(Player.PEDDERSEN));
    System.out.println(" --> Game fieldSize Findus      " + game.getFieldSize(Player.FINDUS));
    System.out.println(" --> Game fieldSize Peddersen   " + game.getFieldSize(Player.PEDDERSEN));
    System.out.println(" --> Game hand Findus           " + game.getHand(Player.FINDUS));
    System.out.println(" --> Game hand Peddersen        " + game.getHand(Player.PEDDERSEN));
    System.out.println(" --> Game field Findus          " + game.getField(Player.FINDUS));
    System.out.println(" --> Game field Peddersen       " + game.getField(Player.PEDDERSEN));

    //Card for later
    Card findusCard = game.getCardInHand(Player.FINDUS, 2);
    Card peddersenCard = game.getCardInHand(Player.PEDDERSEN, 2);

    System.out.println(" --> Game cardInHand Findus     " + findusCard);
    System.out.println(" --> Game cardInHand Peddersen  " + peddersenCard);

    // Gør at lortet(Testen) virker
    game.playCard(Player.FINDUS, findusCard);
    game.endTurn();
    game.playCard(Player.PEDDERSEN, peddersenCard);

    System.out.println(" --> Game cardInField Findus    " + game.getCardInField(Player.FINDUS, 0));
    System.out.println(" --> Game cardInField Peddersen " + game.getCardInField(Player.PEDDERSEN, 0));

    game.endTurn();
    System.out.println(" --> Game usePower Findus       " + game.usePower(Player.FINDUS));
    game.endTurn();
    System.out.println(" --> Game usePower Peddersen    " + game.usePower(Player.PEDDERSEN));

    System.out.println(" --> Hero Mana                  " + game.getHero(Player.FINDUS).getMana());
    System.out.println(" --> Hero Health                " + game.getHero(Player.FINDUS).getHealth());
    System.out.println(" --> Hero Type                  " + game.getHero(Player.FINDUS).getType());
    System.out.println(" --> Hero Owner                 " + game.getHero(Player.FINDUS).getOwner());
    System.out.println(" --> Hero Power description     " + game.getHero(Player.FINDUS).getPowerDesc());

    System.out.println(" --> Card Manacost              " + game.getCardInHand(Player.FINDUS, 0).getManaCost());
    System.out.println(" --> Card Name                  " + game.getCardInHand(Player.FINDUS, 0).getName());
    System.out.println(" --> Card Health                " + game.getCardInHand(Player.FINDUS, 0).getHealth());
    System.out.println(" --> Card Owner                 " + game.getCardInHand(Player.FINDUS, 0).getOwner());
    System.out.println(" --> Card Attack                " + game.getCardInHand(Player.FINDUS, 0).getAttack());
    System.out.println(" --> Card Attribute             " + game.getCardInHand(Player.FINDUS, 0).getAttribute());
    System.out.println(" --> Card Effect Description    " + game.getCardInHand(Player.FINDUS, 0).getCardEffectDescription());
    System.out.println("=== End ===");
  }
}
