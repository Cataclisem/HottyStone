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

package hotstone.domain2gui;

import hotstone.doubles.FakeObjectGame;
import hotstone.framework.Status;
import hotstone.framework.card.Card;
import hotstone.framework.game.Game;
import hotstone.framework.hero.Hero;
import hotstone.framework.Player;
import hotstone.standard.StandardHotStoneGame;
import hotstone.standard.factories.FactoryAlpha;
import hotstone.standard.factories.FactorySemi;
import hotstone.standard.random.RandomSelectorDouble;
import hotstone.standard.random.RandomSelectorStandard;
import hotstone.view.core.HotStoneDrawing;
import hotstone.view.core.HotStoneDrawingType;
import hotstone.view.core.HotStoneFactory;
import minidraw.framework.DrawingEditor;
import minidraw.standard.MiniDrawApplication;
import minidraw.standard.NullTool;

import java.awt.event.MouseEvent;

/** Visual tests of the ability of HotStoneDrawing to respond to
 * observer events notified by the Game instance - i.e. the Domain
 * to the GUI flow of events.
 */
public class ShowUpdate {
  public static void main(String[] args) {
    // TODO: Replace the below assignment into a stable and well
    // tested variant of HotStone
    Game game = new StandardHotStoneGame(new FactorySemi(new RandomSelectorDouble(0)));

    DrawingEditor editor =
      new MiniDrawApplication( "Click anywhere to progress in an update sequence...",
                               new HotStoneFactory(game, Player.FINDUS,
                                       HotStoneDrawingType.OPPONENT_MODE) );
    editor.open();
    editor.setTool( new TriggerGameUpdateTool(editor, game) );
  }
}

/** A tool whose only purpose is to trigger a new, visual,
 * test case for each click that the user makes.
 */
class TriggerGameUpdateTool extends NullTool {
  private DrawingEditor editor;
  private Game game;
  private int count;

  public TriggerGameUpdateTool(DrawingEditor editor, Game game) {
    this.editor = editor;
    this.game = game;
    count = 0;
  }

  @Override
  public void mouseUp(MouseEvent e, int x, int y) {
    // Switch on 'which visual test case is the next to execute'
    switch (count) {
      case 0: {
        Card c = game.getCardInHand(Player.FINDUS, 0);
        game.playCard(Player.FINDUS, c);
        editor.showStatus("FINDUS played " + c.getName());
        break;
      }
      case 1: {
        // Peddersen turn
        game.endTurn();
        editor.showStatus("FINDUS ended turn");
        break;
      }
      case 2: {
        // Peddersen plays card #1
        Card c = game.getCardInHand(Player.PEDDERSEN, 0);
        game.playCard(Player.PEDDERSEN, c);
        editor.showStatus("PEDDERSEN played " + c.getName());
        break;
      }
      case 3: {
        // Findus turn
        game.endTurn();
        editor.showStatus("PEDDESEN ended turn");
        break;
      }
      case 4: {
        Card c = game.getCardInHand(Player.FINDUS, 1);
        game.playCard(Player.FINDUS, c);
        editor.showStatus("FINDUS played " + c.getName());
        break;
      }
      case 5: {
        Card attacker = game.getCardInField(Player.FINDUS, 0);
        Card defender = game.getCardInField(Player.PEDDERSEN, 0);
        game.attackCard(Player.FINDUS, attacker, defender);
        editor.showStatus("FINDUS attacked " + defender.getName() + " with " + attacker.getName());
        break;
      }
      case 6: {
        // pedersen turn
        editor.showStatus("FINDUS ended turn");
        game.endTurn();
        break;
      }
      case 7: {
        game.usePower(Player.PEDDERSEN);
        editor.showStatus("PEDDERSEN used Hero Power");
        break;
      }
      case 8: {
        // Findus turn
        editor.showStatus("PEDDERSEN ended turn");
        game.endTurn();
        break;
      }
      case 9: {
        Card attacker = game.getCardInField(Player.FINDUS, 0);
        game.attackHero(Player.FINDUS, attacker);
        editor.showStatus("Findus attacks Peddersen hero with " + attacker.getName() + "!");
        break;
      }
      default: {
        editor.showStatus("No more events in the list...");
      }
    }
    // Increment count to prepare to pick a new 'visual test case' in the
    // above list
    count++;
  }
}
