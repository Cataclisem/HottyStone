package hotstone.standard.transcriptDecorator;

import hotstone.framework.*;
import hotstone.framework.card.Card;
import hotstone.framework.game.Game;
import hotstone.framework.hero.Hero;
import hotstone.observer.GameObserver;

public abstract class StandardHotStoneGameDecorator implements Game {

    protected Game decoratedGame;

    boolean transcriptEnabled = true;


    public StandardHotStoneGameDecorator(Game game) {
        this.decoratedGame = game;
    }


    public void toggleTranscript(){
        transcriptEnabled = !transcriptEnabled;
    }

    @Override
    public Player getPlayerInTurn() {
        return decoratedGame.getPlayerInTurn();
    }

    @Override
    public Hero getHero(Player who) {
        return decoratedGame.getHero(who);
    }

    @Override
    public Player getWinner() {
        return decoratedGame.getWinner();
    }

    @Override
    public int getTurnNumber() {
        return decoratedGame.getTurnNumber();
    }

    @Override
    public int getDeckSize(Player who) {
        return decoratedGame.getDeckSize(who);
    }

    @Override
    public Card getCardInHand(Player who, int indexInHand) {
        return decoratedGame.getCardInHand(who, indexInHand);
    }

    @Override
    public Iterable<? extends Card> getHand(Player who) {
        return decoratedGame.getHand(who);
    }

    @Override
    public int getHandSize(Player who) {
        return decoratedGame.getHandSize(who);
    }

    @Override
    public Card getCardInField(Player who, int indexInField) {
        return decoratedGame.getCardInField(who, indexInField);
    }

    @Override
    public Iterable<? extends Card> getField(Player who) {
        return decoratedGame.getField(who);
    }

    @Override
    public int getFieldSize(Player who) {
        return decoratedGame.getFieldSize(who);
    }

    @Override
    public void addObserver(GameObserver observer) { decoratedGame.addObserver(observer); }


}
