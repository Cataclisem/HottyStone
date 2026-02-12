package hotstone.standard;

import hotstone.framework.Player;
import hotstone.framework.card.Card;
import hotstone.observer.GameObserver;

import java.util.ArrayList;
import java.util.List;

public class GameObserverSpy implements GameObserver {
    private final ArrayList<String> observerCalls;

    public GameObserverSpy() {
        observerCalls = new ArrayList<>();
    }

    public List<String> getObserverCalls() {
        return observerCalls;
    }

    @Override
    public void onCardPlay(Player who, Card card) {
        observerCalls.add("onCardPlay(" + who + ", " + card.getName() + ")");
    }

    @Override
    public void onTurnChangeTo(Player playerBecomingActive) {
        observerCalls.add("onTurnChangeTo(" + playerBecomingActive + ")");
    }

    @Override
    public void onAttackCard(Player playerAttacking, Card attackingCard, Card defendingCard) {
        observerCalls.add("onAttackCard(" + playerAttacking + ", " + attackingCard.getName() + ", " + defendingCard.getName() + ")");
    }

    @Override
    public void onAttackHero(Player playerAttacking, Card attackingCard) {
        observerCalls.add("onAttackHero(" + playerAttacking + ", " + attackingCard.getName() + ")");
    }

    @Override
    public void onUsePower(Player who) {
        observerCalls.add("onUsePower(" + who + ")");
    }

    @Override
    public void onCardDraw(Player who, Card drawnCard) {
        observerCalls.add("onCardDraw(" + who + ", " + drawnCard.getName() + ")");
    }

    @Override
    public void onCardUpdate(Card card) {
        observerCalls.add("onCardUpdate(" + card.getName() + ")");
    }

    @Override
    public void onCardRemove(Player who, Card card) {
        observerCalls.add("onCardRemove(" + who + ", " + card.getName() + ")");
    }

    @Override
    public void onHeroUpdate(Player who) {
        observerCalls.add("onHeroUpdate(" + who + ")");
    }

    @Override
    public void onGameWon(Player playerWinning) {
        observerCalls.add("onGameWon(" + playerWinning + ")");
    }
}
