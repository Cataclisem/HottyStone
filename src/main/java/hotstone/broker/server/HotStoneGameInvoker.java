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
import frds.broker.RequestObject;
import hotstone.broker.common.OperationNames;
import hotstone.framework.Player;
import hotstone.framework.Status;
import hotstone.framework.card.Card;
import hotstone.framework.game.Game;
import hotstone.framework.hero.Hero;

import java.util.ArrayList;

/**
 * Template code for solving the Broker exercises
 */
public class HotStoneGameInvoker implements Invoker {
    private final Gson gson;
    private final Game game;

    StandardNameService nameServices = new StandardNameService();

    public HotStoneGameInvoker(Game servant, StandardNameService nameService) {
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

        if (operationName.equals(OperationNames.GAME_GET_TURN_NUMBER)) {
            reply = String.valueOf(game.getTurnNumber());

        } else if (operationName.equals(OperationNames.GAME_GET_PLAYER_IN_TURN)) {
            reply = String.valueOf(game.getPlayerInTurn());

        } else if (operationName.equals(OperationNames.GAME_GET_WINNER)) {
            reply = String.valueOf(game.getWinner());

        } else if (operationName.equals(OperationNames.GAME_GET_HERO)) {
            Player who = gson.fromJson(array.get(0), Player.class);
            Hero hero = game.getHero(who);
            String heroID = hero.getID();
            nameServices.putHero(heroID, hero);

            reply = heroID;

        } else if (operationName.equals(OperationNames.GAME_GET_DECK_SIZE)) {
            Player who = gson.fromJson(array.get(0), Player.class);
            reply = String.valueOf(game.getDeckSize(who));

        } else if (operationName.equals(OperationNames.GAME_GET_HAND_SIZE)) {
            Player who = gson.fromJson(array.get(0), Player.class);
            reply = String.valueOf(game.getHandSize(who));

        } else if (operationName.equals(OperationNames.GAME_GET_CARD_IN_HAND)) {
            Player who = gson.fromJson(array.get(0), Player.class);
            int index = gson.fromJson(array.get(1), Integer.class);
            Card card = game.getCardInHand(who, index);
            String cardID = card.getID();
            nameServices.putCard(cardID, card);
            reply = cardID;

        } else if (operationName.equals(OperationNames.GAME_GET_HAND)) {
            Player who = gson.fromJson(array.get(0), Player.class);
            ArrayList<Card> hand = (ArrayList<Card>) game.getHand(who);
            ArrayList<String> ids = new ArrayList<>();
            for (Card c : hand) {
                nameServices.putCard(c.getID(), c);
                ids.add(c.getID());
            }
            reply = String.valueOf(ids);

        } else if (operationName.equals(OperationNames.GAME_GET_FIELD_SIZE)) {
            Player who = gson.fromJson(array.get(0), Player.class);
            reply = String.valueOf(game.getFieldSize(who));

        } else if (operationName.equals(OperationNames.GAME_GET_CARD_IN_FIELD)) {
            Player who = gson.fromJson(array.get(0), Player.class);
            int index = gson.fromJson(array.get(1), Integer.class);
            Card card = game.getCardInField(who, index);
            String cardID = card.getID();
            nameServices.putCard(cardID, card);
            reply = cardID;

        } else if (operationName.equals(OperationNames.GAME_GET_FIELD)) {
            Player who = gson.fromJson(array.get(0), Player.class);
            ArrayList<Card> field = (ArrayList<Card>) game.getField(who);
            ArrayList<String> ids = new ArrayList<>();
            for (Card c : field) {
                nameServices.putCard(c.getID(), c);
                ids.add(c.getID());
            }
            reply = String.valueOf(ids);

        } else if (operationName.equals(OperationNames.GAME_USE_POWER)) {
            Player who = gson.fromJson(array.get(0), Player.class);
            reply = String.valueOf(game.usePower(who));

        } else if (requestObject.getOperationName().equals(OperationNames.GAME_END_OF_TURN)) {
            game.endTurn();
            reply = null;

        } else if (requestObject.getOperationName().equals(OperationNames.GAME_PLAY_CARD)) {
            Player who = gson.fromJson(array.get(0), Player.class);
            String cardID = gson.fromJson(array.get(1), String.class);
            Card card = lookupCard(cardID);
            Status status = game.playCard(who, card);
            reply = String.valueOf(status);

        } else if (requestObject.getOperationName().equals(OperationNames.GAME_ATTACK_CARD)) {
            Player who = gson.fromJson(array.get(0), Player.class);
            String attackCardID = gson.fromJson(array.get(1), String.class);
            String defendCardID = gson.fromJson(array.get(2), String.class);
            Card attackingCard = lookupCard(attackCardID);
            Card defendingCard = lookupCard(defendCardID);
            Status status = game.attackCard(who, attackingCard, defendingCard);
            reply = String.valueOf(status);

        } else if (requestObject.getOperationName().equals(OperationNames.GAME_ATTACK_HERO)) {
            Player who = gson.fromJson(array.get(0), Player.class);
            String attackCardID = gson.fromJson(array.get(1), String.class);
            Card attackingCard = lookupCard(attackCardID);
            Status status = game.attackHero(who, attackingCard);
            reply = String.valueOf(status);

        } else {
            // Unkown operation
            reply = "Server received unknown operation name: '" + requestObject.getOperationName() + "'.";
        }

        return reply;
    }

    private Card lookupCard(String cardID) {
        return nameServices.getCard(cardID);
    }

}
