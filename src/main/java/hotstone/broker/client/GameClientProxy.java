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

package hotstone.broker.client;

import frds.broker.ClientProxy;
import frds.broker.Requestor;
import hotstone.broker.common.OperationNames;
import hotstone.framework.Player;
import hotstone.framework.Status;
import hotstone.framework.card.Card;
import hotstone.framework.game.Game;
import hotstone.framework.hero.Hero;
import hotstone.observer.GameObserver;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

/** Template/starter code for your ClientProxy of Game.
 */
public class GameClientProxy implements Game, ClientProxy {
  private final Requestor requestor;
  private String singletonId = UUID.randomUUID().toString();

  public GameClientProxy(Requestor requestor) {
    this.requestor = requestor;
  }

  @Override
  public int getTurnNumber() {
    int turnNumber = requestor.sendRequestAndAwaitReply(singletonId, OperationNames.GAME_GET_TURN_NUMBER, Integer.class);
    return  turnNumber;
  }

  @Override
  public Player getPlayerInTurn() {
    Player playerInTurn = requestor.sendRequestAndAwaitReply(singletonId, OperationNames.GAME_GET_PLAYER_IN_TURN, Player.class);
    return playerInTurn;
  }

  @Override
  public Hero getHero(Player who) {
    String heroID = requestor.sendRequestAndAwaitReply(singletonId, OperationNames.GAME_GET_HERO, String.class, who);
    HeroClientProxy hero = new HeroClientProxy(requestor, heroID);
    return hero;
  }

  @Override
  public Player getWinner() {
    Player winner = requestor.sendRequestAndAwaitReply(singletonId, OperationNames.GAME_GET_WINNER, Player.class);
    return winner;
  }

  @Override
  public int getDeckSize(Player who) {
    int decksize = requestor.sendRequestAndAwaitReply(singletonId, OperationNames.GAME_GET_DECK_SIZE, Integer.class, who);
    return decksize;
  }

  @Override
  public Card getCardInHand(Player who, int indexInHand) {
    String cardID = requestor.sendRequestAndAwaitReply(singletonId, OperationNames.GAME_GET_CARD_IN_HAND, String.class, who, indexInHand);
    CardClientProxy card = new CardClientProxy(requestor, cardID);

    // card = requestor.sendRequestAndAwaitReply(singletonId, OperationNames.GAME_GET_CARD_IN_HAND, CardClientProxy.class, who, indexInHand);
    return card;
  }

  @Override
  public Iterable<? extends Card> getHand(Player who) {
    Type collectionType = new TypeToken<List<String>>(){}.getType();
    List<String> idList = requestor.sendRequestAndAwaitReply(singletonId, OperationNames.GAME_GET_HAND, collectionType, who);
    ArrayList<CardClientProxy> cards = new ArrayList<>();
    for (String id : idList) {
      cards.add(new CardClientProxy(requestor, id));
    }
    return cards;
  }

  @Override
  public int getHandSize(Player who) {
    int handSize = requestor.sendRequestAndAwaitReply(singletonId, OperationNames.GAME_GET_HAND_SIZE, Integer.class, who);
    return handSize;
  }

  @Override
  public Card getCardInField(Player who, int indexInField) {
    String cardID = requestor.sendRequestAndAwaitReply(singletonId, OperationNames.GAME_GET_CARD_IN_FIELD, String.class, who, indexInField);
    CardClientProxy card = new CardClientProxy(requestor, cardID);
    return card;
  }

  @Override
  public Iterable<? extends Card> getField(Player who) {
    Type collectionType = new TypeToken<List<String>>(){}.getType();
    List<String> idList = requestor.sendRequestAndAwaitReply(singletonId, OperationNames.GAME_GET_FIELD, collectionType, who);
    ArrayList<CardClientProxy> cards = new ArrayList<>();
    for (String id : idList) {
      cards.add(new CardClientProxy(requestor, id));
    }
    return cards;
  }

  @Override
  public int getFieldSize(Player who) {
    int fieldSize = requestor.sendRequestAndAwaitReply(singletonId, OperationNames.GAME_GET_FIELD_SIZE, Integer.class, who);
    return fieldSize;
  }

  @Override
  public void endTurn() {
    requestor.sendRequestAndAwaitReply(singletonId, OperationNames.GAME_END_OF_TURN, void.class);
  }

  @Override
  public Status playCard(Player who, Card card) {
    Status status = requestor.sendRequestAndAwaitReply(singletonId, OperationNames.GAME_PLAY_CARD, Status.class, who, card.getID());
    return status;
  }

  @Override
  public Status attackCard(Player playerAttacking, Card attackingCard, Card defendingCard) {
    Status status = requestor.sendRequestAndAwaitReply(singletonId, OperationNames.GAME_ATTACK_CARD, Status.class, playerAttacking, attackingCard.getID(), defendingCard.getID());
    return status;
  }

  @Override
  public Status attackHero(Player playerAttacking, Card attackingCard) {
    Status status = requestor.sendRequestAndAwaitReply(singletonId, OperationNames.GAME_ATTACK_HERO, Status.class, playerAttacking, attackingCard.getID());
    return status;
  }

  @Override
  public Status usePower(Player who) {
    Status usePower = requestor.sendRequestAndAwaitReply(singletonId, OperationNames.GAME_USE_POWER, Status.class, who);
    return usePower;
  }

  @Override
  public void addObserver(GameObserver observer) {

  }
}
