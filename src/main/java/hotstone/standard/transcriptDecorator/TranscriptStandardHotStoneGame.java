package hotstone.standard.transcriptDecorator;

import hotstone.framework.Player;
import hotstone.framework.Status;
import hotstone.framework.card.Card;
import hotstone.framework.game.Game;
import hotstone.observer.GameObserver;

import java.util.ArrayList;

public class TranscriptStandardHotStoneGame extends StandardHotStoneGameDecorator {

    ArrayList<String> transcript = new ArrayList<>();


    public TranscriptStandardHotStoneGame(Game decoratorGame){
        super(decoratorGame);
    }

    @Override
    public void endTurn() {
        printTranscript(decoratedGame.getPlayerInTurn().name() + " ended turn.", Status.OK);
        decoratedGame.endTurn();
    }

    @Override
    public Status playCard(Player who, Card card) {
        Status status = decoratedGame.playCard(who, card);
        printTranscript(who + " played " + card.getName() + ".", status);
        return status;
    }

    @Override
    public Status attackCard(Player playerAttacking, Card attackingCard, Card defendingCard) {
        Status status = decoratedGame.attackCard(playerAttacking, attackingCard, defendingCard);
        printTranscript(playerAttacking + " attacked " + defendingCard.getName() + " with " + attackingCard.getName() + ".", status);
        return status;
    }

    @Override
    public Status attackHero(Player playerAttacking, Card attackingCard) {
        Status status = decoratedGame.attackHero(playerAttacking, attackingCard);
        printTranscript(playerAttacking + " attacked Hero with " + attackingCard.getName() + ".", status);
        return status;
    }

    @Override
    public Status usePower(Player who) {
        Status status = decoratedGame.usePower(who);
        printTranscript(who + " used Hero Power.", status);
        return status;
    }

    private void printTranscript(String action, Status status) {
        if (status == Status.OK){
            if (transcriptEnabled) System.out.println(action);
            transcript.add(action);
        }
    }
}
