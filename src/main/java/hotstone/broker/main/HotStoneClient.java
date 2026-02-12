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
import hotstone.broker.client.GameClientProxy;
import hotstone.broker.common.BrokerConstants;
import hotstone.framework.Player;
import hotstone.framework.game.Game;
import hotstone.view.core.HotStoneDrawingType;
import hotstone.view.core.HotStoneFactory;
import hotstone.view.tool.StateTool;
import minidraw.framework.DrawingEditor;
import minidraw.standard.MiniDrawApplication;

public class HotStoneClient {
  public static void main(String[] args)  {
    // Get the name of the host from the commandline parameters
    String host = args[0];
    // and execute the story test, talking to the server with that name
    Player player = Player.valueOf(args[1].toUpperCase());
    new HotStoneClient(host, player);
    // new HotStoneClient(host, Player.PEDDERSEN);
  }

  public HotStoneClient(String host, Player whoToPlay) {
    // Create the client side Broker roles
    UriTunnelClientRequestHandler clientRequestHandler
            = new UriTunnelClientRequestHandler(host, BrokerConstants.HOTSTONE_PORT,
            false, BrokerConstants.HOTSTONE_TUNNEL_PATH);
    Requestor requestor = new StandardJSONRequestor(clientRequestHandler);

    Game game = new GameClientProxy(requestor);
    if (whoToPlay == game.getPlayerInTurn()){
      DrawingEditor editor =
              new MiniDrawApplication( "HotStone",
                      new HotStoneFactory(game, whoToPlay,
                              HotStoneDrawingType.HOTSEAT_MODE) );
      editor.open();
      editor.setTool(new StateTool(editor, game, whoToPlay));
    } else {
      DrawingEditor editor =
              new MiniDrawApplication( "HotStone",
                      new HotStoneFactory(game, whoToPlay,
                              HotStoneDrawingType.OPPONENT_MODE) );
      editor.open();
      editor.setTool(new StateTool(editor, game, whoToPlay));
    }

  }
}
