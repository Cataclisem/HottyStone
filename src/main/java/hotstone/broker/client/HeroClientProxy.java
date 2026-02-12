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
import hotstone.framework.Status;
import hotstone.framework.card.Card;
import hotstone.framework.game.Game;
import hotstone.framework.hero.Hero;
import hotstone.observer.GameObserver;

import java.util.ArrayList;

/** Template/starter code for your ClientProxy of Game.
 */
public class HeroClientProxy implements Hero, ClientProxy {
  private final String id;

  private final Requestor requestor;

  public HeroClientProxy(Requestor requestor, String heroID) {
    this.requestor = requestor;
    id = heroID;
  }

  @Override
  public int getMana() {
    int mana = requestor.sendRequestAndAwaitReply(id, OperationNames.HERO_GET_MANA, Integer.class);
    return mana;
  }

  @Override
  public int getHealth() {
    int health = requestor.sendRequestAndAwaitReply(id, OperationNames.HERO_GET_HEALTH, Integer.class);
    return health;
  }

  @Override
  public boolean canUsePower() {
    boolean power = requestor.sendRequestAndAwaitReply(id, OperationNames.HERO_IS_ACTIVE, Boolean.class);
    return power;
  }

  @Override
  public String getType() {
    String type = requestor.sendRequestAndAwaitReply(id, OperationNames.HERO_GET_TYPE, String.class);
    return type;
  }

  @Override
  public Player getOwner() {
    Player player = requestor.sendRequestAndAwaitReply(id, OperationNames.HERO_GET_OWNER, Player.class);
    return player;
  }

  @Override
  public String getPowerDesc() {
    String desc = requestor.sendRequestAndAwaitReply(id, OperationNames.HERO_GET_POWER_DESCRIPTION, String.class);
    return desc;
  }


  @Override
  public String getID() {
    return id;
  }
}
