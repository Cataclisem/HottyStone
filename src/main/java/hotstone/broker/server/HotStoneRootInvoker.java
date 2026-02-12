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
public class HotStoneRootInvoker implements Invoker {
    private final Gson gson;
    private final Game game;
    private final Invoker heroInvoker;
    private final Invoker gameInvoker;
    private final Invoker cardInvoker;
    private StandardNameService nameService = new StandardNameService();

    public HotStoneRootInvoker(Game servant) {
        game = servant;
        gson = new Gson();

        this.gameInvoker = new HotStoneGameInvoker(game, nameService);
        this.heroInvoker = new HotStoneHeroInvoker(game, nameService);
        this.cardInvoker = new HotStoneCardInvoker(game, nameService);

    }

    @Override
    public String handleRequest(String request) {

        RequestObject requestObject = gson.fromJson(request, RequestObject.class);
        String operationName = requestObject.getOperationName();

        ReplyObject reply = null;

        if (operationName.startsWith(OperationNames.GAME_PREFIX)) {
            String answer = gameInvoker.handleRequest(request);
            reply = new ReplyObject(HttpServletResponse.SC_CREATED, answer);

        } else if (operationName.startsWith(OperationNames.HERO_PREFIX)) {
            String answer = heroInvoker.handleRequest(request);
            reply = new ReplyObject(HttpServletResponse.SC_CREATED, answer);

        } else if (operationName.startsWith(OperationNames.CARD_PREFIX)) {
            String answer = cardInvoker.handleRequest(request);
            reply = new ReplyObject(HttpServletResponse.SC_CREATED, answer);

        } else {
            // Unkown operation
            reply = new ReplyObject(HttpServletResponse.
                    SC_NOT_IMPLEMENTED,
                    "Server received unknown operation name: '"
                            + requestObject.getOperationName() + "'.");
        }

        return gson.toJson(reply);
    }

}
