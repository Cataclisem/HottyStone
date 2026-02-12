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

package hotstone.broker.server;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import frds.broker.Invoker;
import frds.broker.ReplyObject;
import frds.broker.RequestObject;
import hotstone.broker.common.OperationNames;
import hotstone.framework.Player;
import hotstone.framework.Status;
import hotstone.framework.card.Card;
import hotstone.framework.game.Game;
import hotstone.framework.hero.Hero;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/**
 * Template code for solving the Broker exercises
 */
public class HotStoneHeroInvoker implements Invoker {
    private final Gson gson;
    private final Game game;
    private StandardNameService nameServices;

    public HotStoneHeroInvoker(Game servant, StandardNameService nameService) {
        game = servant;
        gson = new Gson();

        nameServices = nameService;
    }

    @Override
    public String handleRequest(String request) {
        RequestObject requestObject = gson.fromJson(request, RequestObject.class);
        JsonArray array = JsonParser.parseString(requestObject.getPayload()).getAsJsonArray();

        String operationName = requestObject.getOperationName();
        String objectID = requestObject.getObjectId();

        String reply;

        Hero heroServant = lookupHero(objectID);
        if (operationName.equals(OperationNames.HERO_GET_OWNER)) {
            reply = heroServant.getOwner().toString();

        } else if (operationName.equals(OperationNames.HERO_GET_MANA)) {
            reply = String.valueOf(heroServant.getMana());

        } else if (operationName.equals(OperationNames.HERO_GET_HEALTH)) {
            reply = String.valueOf(heroServant.getHealth());

        } else if (operationName.equals(OperationNames.HERO_GET_TYPE)) {
            reply = heroServant.getType();

        } else if (operationName.equals(OperationNames.HERO_IS_ACTIVE)) {
            reply = String.valueOf(heroServant.canUsePower());

        } else if (operationName.equals(OperationNames.HERO_GET_POWER_DESCRIPTION)) {
            reply = heroServant.getPowerDesc();

        } else {
            // Unkown operation
            reply = "Server received unknown operation name: '" + requestObject.getOperationName() + "'.";
        }

        return reply;
    }

    private Hero lookupHero(String heroID) {
        return nameServices.getHero(heroID);
    }
}
