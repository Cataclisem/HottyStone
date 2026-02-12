/*
 * Copyright (C) 2023. Henrik Bærbak Christensen, Aarhus University.
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

package hotstone.view.tool;

import frds.broker.IPCException;
import hotstone.framework.Player;
import hotstone.framework.Utility;
import hotstone.framework.game.Game;
import hotstone.view.core.HotStoneDrawing;
import hotstone.view.figure.HotStoneFigure;
import hotstone.view.figure.HotStoneFigureType;
import minidraw.framework.Drawing;
import minidraw.framework.DrawingEditor;
import minidraw.framework.Figure;
import minidraw.framework.Tool;
import minidraw.standard.NullTool;

import java.awt.event.MouseEvent;

/** Template for the State tool - similar to MiniDraw SelectionTool
 * it is a tool that delegates all mouse events to a subtool, and
 * the kind of subtool to use is determined by what is clicked on in the
 * mouse down event. If it is a button, then delegate to ButtonTool,
 * if it is a card, delegate to PlayCardTool, if it is a minion,
 * delegate to a MinionAttackTool, etc.
 *
 * Quite a lot of the code is complete - fill in the missing pieces...
 */
public class StateTool extends NullTool {
  private final Tool theNullTool;
  private final Drawing model;
  private Tool state;
  private DrawingEditor editor;
  private Game game;
  private Player whoItIs;

  public StateTool(DrawingEditor editor, Game game, Player whoItIs) {
    this.editor = editor;
    this.game = game;
    this.whoItIs = whoItIs;
    model = editor.drawing();
    state = theNullTool = new NullTool();
  }

  @Override
  public void mouseDown(MouseEvent e, int x, int y) {
    // Find the figure below mouse (x,y)
    Figure figureAtPosition = model.findFigure(e.getX(), e.getY());
    // Iff that figure is associated with our HotStone
    // (All MiniDraw figures that handle HotStone graphics are
    // implementing this role interface).
    if (figureAtPosition instanceof HotStoneFigure) {
      HotStoneFigure hsf = (HotStoneFigure) figureAtPosition;
      // TODO: Complete this state selection
      if (hsf.getType() == HotStoneFigureType.CARD_FIGURE) {
        state = new PlayCardTool(editor, game, whoItIs);
      } else if (hsf.getType() == HotStoneFigureType.TURN_BUTTON ||
              hsf.getType() == HotStoneFigureType.SWAP_BUTTON) {
        state = new EndTurnTool(editor, game);
      } else if (hsf.getType() == HotStoneFigureType.MINION_FIGURE) {
        state = new AttackCardTool(editor, game, whoItIs);
      } else if (hsf.getType() == HotStoneFigureType.HERO_FIGURE) {
        state = new HeroPowerTool(editor, game, whoItIs);
      } else if (hsf.getType() == HotStoneFigureType.WIN_BUTTON) {
        // Clicking the 'won button' should do nothing!
        state = theNullTool; // User have to close the window to restart.
      }
      // Implemented Opponent action button as update button
      else if (hsf.getType() == HotStoneFigureType.OPPONENT_ACTION_BUTTON) {
        model.requestUpdate();
      }
    }
    else {
      state = theNullTool;
    }
    state.mouseDown(e, x, y);

    // Get info on players
    Player Observer = Utility.computeOpponent(whoItIs);
    Player whoInTurn = whoItIs;

    // For getting winner scene
    HotStoneDrawing hotStoneDrawing = (HotStoneDrawing) editor.drawing();

    // Checks if it is a draw with health
    if (game.getWinner() != null){
      // Gives winner scene
      hotStoneDrawing.onGameWon(game.getWinner());
      // Make no action possible
      state = theNullTool;

    }
  }

  @Override
  public void mouseUp(MouseEvent e, int x, int y) {
    try {
      state.mouseUp(e, x, y);
      if (state != theNullTool){
        System.out.println("---> BruteForce Redraw");
        editor.drawing().requestUpdate();
      }
    } catch (IPCException exc){
      state = theNullTool;
    }
  }

  @Override
  public void mouseDrag(MouseEvent e, int x, int y) {
    state.mouseDrag(e, x, y);
  }

  @Override
  public void mouseMove(MouseEvent e, int x, int y) {
    state.mouseMove(e, x, y);
  }

}
