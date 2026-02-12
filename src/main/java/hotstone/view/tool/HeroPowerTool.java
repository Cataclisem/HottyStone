package hotstone.view.tool;

import hotstone.framework.Player;
import hotstone.framework.Status;
import hotstone.framework.game.Game;
import hotstone.framework.hero.Hero;
import hotstone.view.figure.HeroFigure;
import hotstone.view.figure.HotStoneActorFigure;
import minidraw.framework.Drawing;
import minidraw.framework.DrawingEditor;
import minidraw.framework.Figure;
import minidraw.standard.NullTool;

import java.awt.event.MouseEvent;

public class HeroPowerTool extends NullTool {
    private DrawingEditor editor;
    private Game game;
    private Player whoAmIPlaying;

    public HeroPowerTool(DrawingEditor editor, Game game, Player whoAmIPlaying) {
        this.editor = editor;
        this.game = game;
        this.whoAmIPlaying = whoAmIPlaying;
    }

    @Override
    public void mouseDown(MouseEvent e, int x, int y) {
        Drawing model = editor.drawing();
        HeroFigure figureAtPosition = (HeroFigure) model.findFigure(e.getX(), e.getY());
        Hero associatedHero = figureAtPosition.getAssociatedHero();

        if (associatedHero.getOwner() == whoAmIPlaying) {
            Status status = game.usePower(whoAmIPlaying);
            editor.showStatus("Use hero power. Result = " + status);
        }
        editor.drawing().requestUpdate();
    }
}
