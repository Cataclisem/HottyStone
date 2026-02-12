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
import hotstone.framework.Effect;
import hotstone.framework.Player;
import hotstone.framework.card.Card;
import hotstone.framework.card.CardAttribute;
import hotstone.framework.hero.Hero;

/** Template/starter code for your ClientProxy of Game.
 */
public class CardClientProxy implements Card, ClientProxy {
  private final String id;

  private final Requestor requestor;

  public CardClientProxy(Requestor requestor, String cardID) {
    this.requestor = requestor;
    id = cardID;
  }

  @Override
  public String getName() {
    String name = requestor.sendRequestAndAwaitReply(id, OperationNames.CARD_GET_NAME, String.class);
    return name;
  }

  @Override
  public int getManaCost() {
    int mana = requestor.sendRequestAndAwaitReply(id, OperationNames.CARD_GET_MANA_COST, Integer.class);
    return mana;
  }

  @Override
  public int getAttack() {
    int attack = requestor.sendRequestAndAwaitReply(id, OperationNames.CARD_GET_ATTACK, Integer.class);
    return attack;
  }

  @Override
  public int getHealth() {
    int health = requestor.sendRequestAndAwaitReply(id, OperationNames.CARD_GET_HEALTH, Integer.class);
    return health;
  }

  @Override
  public boolean isActive() {
    boolean active = requestor.sendRequestAndAwaitReply(id, OperationNames.CARD_IS_ACTIVE, Boolean.class);
    return active;
  }

  @Override
  public Player getOwner() {
    Player owner = requestor.sendRequestAndAwaitReply(id, OperationNames.CARD_GET_OWNER, Player.class);
    return owner;
  }

  @Override
  public String getCardEffectDescription() {
    String desc = requestor.sendRequestAndAwaitReply(id, OperationNames.CARD_GET_EFFECT_DESCRIPTION, String.class);
    return desc;
  }

  @Override
  public CardAttribute getAttribute() {
    CardAttribute attr = requestor.sendRequestAndAwaitReply(id, OperationNames.CARD_GET_ATTRIBUTE, CardAttribute.class);
    return attr;
  }

  @Override
  public String getID() {
    return id;
  }
}
