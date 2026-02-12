package hotstone.standard;

import hotstone.framework.Player;
import hotstone.framework.card.Card;
import hotstone.standard.factories.FactoryAlpha;
import hotstone.standard.factories.FactoryGamma;
import hotstone.standard.factories.FactorySemi;
import hotstone.standard.random.RandomSelectorDouble;
import hotstone.utility.TestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestObserver {
    private StandardHotStoneGame game;
    private GameObserverSpy observer;

    @BeforeEach
    public void setUp() {
        game = new StandardHotStoneGame(new FactorySemi(new RandomSelectorDouble(0)));
        observer = new GameObserverSpy();
        game.addObserver(observer);
    }

    @Test
    public void shouldCallOnCardPlay() {
        // Given a game,
        // when a Brown Rice card is played,
        Player player = game.getPlayerInTurn();
        Card card = game.getCardInHand(player, 2);
        game.playCard(player, card);

        // then the last call in the observer spy should be onPlayCard(FINDUS, Brown-Rice).
        String call = observer.getObserverCalls().get(observer.getObserverCalls().size() - 1);
        System.out.println(observer.getObserverCalls());
        assertThat(call, is("onCardPlay(FINDUS, Brown-Rice)"));
    }

    @Test
    public void shouldCallOnTurnChangeTo() {
        // Given a game,
        // when the current player (FINDUS) ends their turn,
        game.endTurn();
        game.endTurn();
        game.endTurn();

        // then the second to last call in the observer spy should be onTurnChangeTo(PEDDERSEN).
        String call = observer.getObserverCalls().get(observer.getObserverCalls().size() - 4);
        assertThat(call, is("onTurnChangeTo(PEDDERSEN)"));

        // When the turn is ended again,
        game.endTurn();

        // then the second to last call in the observer spy should be onTurnChangeTo(FINDUS).
        call = observer.getObserverCalls().get(observer.getObserverCalls().size() - 4);
        assertThat(call, is("onTurnChangeTo(FINDUS)"));
    }

    @Test
    public void shouldCallOnAttackCard() {
        // Given a game,
        // when Findus and Peddersen both play Brown Rice, and Findus then attacks Peddersen's card,
        Player player = game.getPlayerInTurn();
        Card card1 = game.getCardInHand(player, 2);
        game.playCard(player, card1);
        game.endTurn();

        player = game.getPlayerInTurn();
        Card card2 = game.getCardInHand(player, 2);
        game.playCard(player, card2);
        game.endTurn();

        player = game.getPlayerInTurn();
        game.attackCard(player, card1, card2);

        // then the second to last call in the observer spy should be onAttackCard(FINDUS, Brown-Rice, Brown-Rice).
        String call = observer.getObserverCalls().get(observer.getObserverCalls().size() - 3);
        assertThat(call, is("onAttackCard(FINDUS, Brown-Rice, Brown-Rice)"));
    }

    @Test
    public void shouldCallOnAttackHero() {
        // Given a game,
        // when Findus plays Brown Rice, and attacks Peddersen's hero on Findus' next turn
        Player player = game.getPlayerInTurn();
        Card card = game.getCardInHand(player, 2);
        game.playCard(player, card);
        game.endTurn();
        game.endTurn();

        game.attackHero(player, card);

        // then the last call in the observer spy should be onAttackHero(FINDUS, Brown-Rice).
        String call = observer.getObserverCalls().get(observer.getObserverCalls().size() - 3);
        System.out.println(observer.getObserverCalls());
        assertThat(call, is("onAttackHero(FINDUS, Brown-Rice)"));
    }

    @Test
    public void shouldCallOnUsePower() {
        // Given a game,
        // when Findus uses their power,
        Player player = game.getPlayerInTurn();

        game.endTurn();
        game.endTurn();
        game.usePower(player);

        // then the last call in the observer spy should be onUsePower(FINDUS).
        String call = observer.getObserverCalls().get(observer.getObserverCalls().size() - 2);
        assertThat(call, is("onUsePower(FINDUS)"));

        // The same test should work for Peddersen
        game.endTurn();
        player = game.getPlayerInTurn();
        game.usePower(player);

        call = observer.getObserverCalls().get(observer.getObserverCalls().size() - 2);
        assertThat(call, is("onUsePower(PEDDERSEN)"));
    }

    @Test
    public void shouldCallOnCardDrawWhenTurnEnds() {
        // Given a game
        // When the turn is ended
        game.endTurn();
        game.endTurn();
        game.endTurn();

        // then the last call in the observer spy should be onCardDraw(PEDDERSEN, French-Fries).
        String call = observer.getObserverCalls().get(observer.getObserverCalls().size() - 2);
        assertThat(call, is("onCardDraw(PEDDERSEN, French-Fries)"));
    }

    @Test
    public void shouldCallOnCardDrawWhenDrawCardEffectIsTriggered() {
        // Given a game,
        // when Findus plays Noodle Soup card,
        for (int i = 0; i < 28; i++) {
            game.endTurn();
        }

        Player player = game.getPlayerInTurn();
        Card card = game.getCardInHand(player, 0);
        game.playCard(player, card);

        // then the last call in the observer spy should be onCardDraw(FINDUS, Noodle-Soup).
        String call = observer.getObserverCalls().get(observer.getObserverCalls().size() - 3);
        System.out.println(observer.getObserverCalls());
        assertThat(call, is("onCardDraw(FINDUS, Noodle-Soup)"));
    }

    @Test
    public void shouldCallOnCardUpdateWhenAttacked() {
        // Given a game,
        // when a minion is attacked such that its health is reduced,
        game.endTurn();
        game.endTurn();
        game.endTurn();
        game.endTurn();
        Player player = game.getPlayerInTurn();
        Card card1 = game.getCardInHand(player, 0);
        game.playCard(player, card1);
        game.endTurn();

        player = game.getPlayerInTurn();
        Card card2 = game.getCardInHand(player, 0);
        game.playCard(player, card2);
        game.endTurn();


        player = game.getPlayerInTurn();
        game.attackCard(player, card1, card2);

        // then the last call in the observer spy should be onCardUpdate(Green-Salad).
        String call = observer.getObserverCalls().get(observer.getObserverCalls().size() - 1);
        assertThat(call, is("onCardUpdate(Green-Salad)"));
    }

    @Test
    public void shouldCallOnCardUpdateWhenCardActivityIsChanged() {
        // Given a game with a couple of cards played by Peddersen,
        game.endTurn();
        Player player = game.getPlayerInTurn();
        Card card = game.getCardInHand(player, 2);
        game.playCard(player, card);
        game.endTurn();
        game.endTurn();

        player = game.getPlayerInTurn();
        card = game.getCardInHand(player, 2);
        game.playCard(player, card);
        game.endTurn();

        // when it is Pedderssen's turn again,
        game.endTurn();

        // then the last two calls in the observer spy should be onCardUpdate(Brown-Rice).
        String call1 = observer.getObserverCalls().get(observer.getObserverCalls().size() - 3);
        String call2 = observer.getObserverCalls().get(observer.getObserverCalls().size() - 4);
        assertThat(call1, is("onCardUpdate(Brown-Rice)"));
        assertThat(call2, is("onCardUpdate(Brown-Rice)"));
    }

    @Test
    public void shouldCallOnCardUpdateWhenCardAttackIsChanged() {
        // Given a game with a minion on the field,
        Player player = game.getPlayerInTurn();
        Card card = game.getCardInHand(player, 2);
        game.playCard(player, card);

        for (int i = 0; i < 8; i++) game.endTurn();

        // when Tomato Salad card is played such that the attack of the first card is increased,
        player = game.getPlayerInTurn();
        card = game.getCardInHand(player, 0);
        game.playCard(player, card);

        // then the last two calls in the observer spy should be onCardUpdate(Brown-Rice).
        String call = observer.getObserverCalls().get(observer.getObserverCalls().size() - 3);
        System.out.println(observer.getObserverCalls());
        assertThat(call, is("onCardUpdate(Brown-Rice)"));
    }

    @Test
    public void shouldCallOnCardRemoveOnCardDeathByAttack() {
        // Given a game with a minion on both fields,
        Player player = game.getPlayerInTurn();
        Card card1 = game.getCardInHand(player, 2);
        game.playCard(player, card1);
        game.endTurn();

        player = game.getPlayerInTurn();
        Card card2 = game.getCardInHand(player, 2);
        game.playCard(player, card2);
        game.endTurn();

        // when Findus kills the enemy minion,
        player = game.getPlayerInTurn();
        game.attackCard(player, card1, card2);

        // then the last two calls in the observer spy should be
        // onCardRemove(PEDDERSEN, Brown-Rice) and onCardRemove(FINDUS, Brown-Rice).
        String call1 = observer.getObserverCalls().get(observer.getObserverCalls().size() - 1);
        assertThat(call1, is("onCardRemove(FINDUS, Brown-Rice)"));
        String call2 = observer.getObserverCalls().get(observer.getObserverCalls().size() - 2);
        assertThat(call2, is("onCardRemove(PEDDERSEN, Brown-Rice)"));
    }

    @Test
    public void shouldCallOnCardRemoveOnCardDeathByEffect() {
        // Given a game some turns in, with a card on Pedderssen's field,
        game.endTurn();
        Player player = game.getPlayerInTurn();
        Card card1 = game.getCardInHand(player, 2);
        game.playCard(player, card1);
        game.endTurn();

        for (int i = 0; i < 42; i++) game.endTurn();

        // when Findus plays Chicken Curry card, which kills Pedderssen's one card on his field,
        player = game.getPlayerInTurn();
        Card card2 = game.getCardInHand(player, 0);
        game.playCard(player, card2);

        // then the last call in the observer spy should be onCardRemove(PEDDERSEN, Brown-Rice).
        String call = observer.getObserverCalls().get(observer.getObserverCalls().size() - 3);
        System.out.println(observer.getObserverCalls());
        assertThat(call, is("onCardRemove(PEDDERSEN, Brown-Rice)"));
    }

    @Test
    public void shouldCallOnHeroUpdateWhenPowerTurnedActive() {
        // Given a game a couple of turns in,
        game.endTurn();
        game.endTurn();

        // when the turn is ended,
        game.endTurn();

        // then Peddersen's hero power is activated, and thus onHeroUpdate(PEDDERSEN) should be called.
        String call = observer.getObserverCalls().get(observer.getObserverCalls().size() - 3);
        assertThat(call, is("onHeroUpdate(PEDDERSEN)"));
    }

    @Test
    public void shouldCallOnHeroUpdateWhenHeroDamaged() {
        // Given a game with a card on Findus' field,
        Player player = game.getPlayerInTurn();
        Card card1 = game.getCardInHand(player, 2);
        game.playCard(player, card1);
        game.endTurn();
        game.endTurn();

        // when Findus attacks Peddersen's hero, reducing its health,
        game.attackHero(player, card1);

        // then Peddersen's hero health is reduced and onHeroUpdate(PEDDERSEN) should be called.
        String call = observer.getObserverCalls().get(observer.getObserverCalls().size() - 2);
        System.out.println(observer.getObserverCalls());
        assertThat(call, is("onHeroUpdate(PEDDERSEN)"));
    }

    @Test
    public void shouldCallOnHeroUpdateWhenDamagedByDeckFatigue() {
        // Given a game many turns in such that Findus' deck is empty,
        for (int i = 0; i < 54; i++) game.endTurn();

        // when it is Findus' turn again,
        game.endTurn();
        game.endTurn();

        // then Findus' hero's health is reduced and onHeroUpdate(FINDUS) should be called.
        String call = observer.getObserverCalls().get(observer.getObserverCalls().size() - 1);
        assertThat(call, is("onHeroUpdate(FINDUS)"));
    }

    @Test
    public void shouldCallOnHeroUpdateWhenPowerIsUsed() {
        // Given a game a couple of turns in,
        game.endTurn();
        game.endTurn();

        // when Findus uses their power,
        Player player = game.getPlayerInTurn();
        game.usePower(player);

        // then their power is set to inactive, and onHeroUpdate(FINDUS) should be called.
        String call = observer.getObserverCalls().get(observer.getObserverCalls().size() - 1);
        assertThat(call, is("onHeroUpdate(FINDUS)"));
    }

    @Test
    public void shouldCallOnHeroUpdateWhenManaIsReducedByCard() {
        // Given a game,
        // when Findus plays a card,
        Player player = game.getPlayerInTurn();
        Card card1 = game.getCardInHand(player, 2);
        game.playCard(player, card1);

        // then Findus' mana is reduced, and onHeroUpdate(FINDUS) should be called.
        String call = observer.getObserverCalls().get(observer.getObserverCalls().size() - 2);
        System.out.println(observer.getObserverCalls());
        assertThat(call, is("onHeroUpdate(FINDUS)"));
    }

    @Test
    public void shouldCallOnHeroUpdateWhenManaIsReducedByPower() {
        // Given a game a couple of turns in,
        game.endTurn();
        game.endTurn();

        // when Findus uses their power,
        Player player = game.getPlayerInTurn();
        game.usePower(player);

        // then their mana is reduced, and onHeroUpdate(FINDUS) should be called
        String call = observer.getObserverCalls().get(observer.getObserverCalls().size() - 1);
        assertThat(call, is("onHeroUpdate(FINDUS)"));
    }

    @Test
    public void shouldCallOnGameWon() {
        // Given a game,
        // when Findus defeats Peddersen's hero
        Player player = game.getPlayerInTurn();
        Card card1 = game.getCardInHand(player, 0);
        game.playCard(player, card1);
        game.endTurn();
        game.endTurn();

        Card card2 = game.getCardInHand(player, 0);
        game.playCard(player, card2);
        game.attackHero(player, card1);
        game.endTurn();
        game.endTurn();

        Card card3 = game.getCardInHand(player, 0);
        game.playCard(player, card3);
        game.attackHero(player, card1);
        game.attackHero(player, card2);
        game.endTurn();
        game.endTurn();

        Card card4 = game.getCardInHand(player, 0);
        game.playCard(player, card4);
        game.attackHero(player, card1);
        game.attackHero(player, card2);
        game.attackHero(player, card3);
        game.endTurn();
        game.endTurn();

        Card card5 = game.getCardInHand(player, 0);
        game.playCard(player, card5);
        game.attackHero(player, card1);
        game.attackHero(player, card2);
        game.attackHero(player, card3);
        game.attackHero(player, card4);
        game.endTurn();

        // then onGameWon(FINDUS) should be called.
        String call = observer.getObserverCalls().get(observer.getObserverCalls().size() - 5);
        assertThat(call, is("onGameWon(FINDUS)"));
    }

    @Test
    public void shouldObserveFieldingSovsCard() {
        // Given a GammaStone game a couple of turns in, where the game is equipped with an ObserverSpy,
        game = new StandardHotStoneGame(new FactoryGamma());
        observer = new GameObserverSpy();
        game.addObserver(observer);

        game.endTurn();
        game.endTurn();
        game.endTurn();

        // when Peddersen uses their power, which adds the card Sovs to his field,
        Player player = game.getPlayerInTurn();
        game.usePower(player);

        // then the observer should have the calls
        // onCardPlay(PEDDERSEN) for Sovs
        // onHeroUpdate(PEDDERSEN) for mana use of card
        // onHeroUpdate(PEDDERSEN) for mana of power use
        // onPowerUse(PEDDERSEN)
        // onHeroUpdate(PEDDERSEN) for power inactive
        String call1 = observer.getObserverCalls().get(observer.getObserverCalls().size() - 4);
        assertThat(call1, is("onCardPlay(PEDDERSEN, Sovs)"));

        String call2 = observer.getObserverCalls().get(observer.getObserverCalls().size() - 5);
        assertThat(call2, is("onHeroUpdate(PEDDERSEN)"));

        String call3 = observer.getObserverCalls().get(observer.getObserverCalls().size() - 3);
        assertThat(call3, is("onHeroUpdate(PEDDERSEN)"));

        String call4 = observer.getObserverCalls().get(observer.getObserverCalls().size() - 2);
        assertThat(call4, is("onUsePower(PEDDERSEN)"));

        String call5 = observer.getObserverCalls().get(observer.getObserverCalls().size() - 1);
        assertThat(call5, is("onHeroUpdate(PEDDERSEN)"));
    }
}
