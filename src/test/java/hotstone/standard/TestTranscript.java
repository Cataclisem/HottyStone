package hotstone.standard;

/*
  Skeleton class for Trascript test cases.
  Using the AlphaStone game version we test transcribing through a decorator.
 */

import hotstone.framework.*;
import hotstone.framework.card.Card;
import hotstone.standard.transcriptDecorator.StandardHotStoneGameDecorator;
import hotstone.standard.transcriptDecorator.TranscriptStandardHotStoneGame;
import hotstone.standard.factories.FactoryAlpha;
import hotstone.utility.TestHelper;
import org.junit.jupiter.api.*;


import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestTranscript {
    private StandardHotStoneGameDecorator game;
    /** Fixture for AlphaStone testing. */
    @BeforeEach
    public void setUp() { game = new TranscriptStandardHotStoneGame(new StandardHotStoneGame(new FactoryAlpha())); }

    // Example of an early, simple test case:
    // Turn handling


    @Test
    public void shouldNotPrintTranscriptAndHaveFindusAsFirstPlayer() {
        // Given a game
        // When the game ask for the player in turn
        game.toggleTranscript();
        Player player = game.getPlayerInTurn();
        // Then it should be Findus
        assertThat(player, is(Player.FINDUS));
        // Showing game
        TestHelper.printGameState(game);
    }

    @Test
    public void shouldHaveCorrectCardsInFieldAfterPlayingThem() {
        // Given a game
        // Skipping to Peddersens third turn
        game.endTurn();
        game.endTurn();
        game.endTurn();
        game.endTurn();
        game.endTurn();
        // Player in turn is Peddersen
        Player player = Player.PEDDERSEN;
        // When Peddersen plays his "Uno" and "Dos" cards from index four and three of his hand
        Card card_1 = game.getCardInHand(player,4);
        Card card_2 = game.getCardInHand(player,3);
        game.playCard(player,card_1);
        game.playCard(player,card_2);
        // Then they should appear on Peddersens field
        assertThat(game.getCardInField(player,0).getName(), is(card_1.getName()));
        assertThat(game.getCardInField(player,1).getName(), is(card_2.getName()));
        // Showing game
        TestHelper.printGameState(game);
    }

    @Test
    public void shouldFindusUsesHeroPower(){
        // Given a game
        // When Findus uses his heros power
        Status status = game.usePower(Player.FINDUS);
        // Then he should be allowed to
        assertThat(status, is(Status.OK));
    }

    @Test
    public void shouldNotPrintPlayedCardIfNotEnoughMana() {
        // Given a game
        // Findus Plays his card "Dos" and has zero mana left
        game.playCard(game.getPlayerInTurn(), game.getCardInHand(game.getPlayerInTurn(), 1));
        // When Findus tries to use his heros power
        Status status = game.usePower(game.getPlayerInTurn());
        // Then he should not be able to and is given the status message "not enough mana"
        assertThat(status, is(Status.NOT_ENOUGH_MANA));
    }


    @Test
    public void shouldNotAllowPeddersenToAttackOnFindusTurn(){
        // Given a game
        // Findus play card "Uno" on his turn
        Card uno = game.getCardInHand(Player.FINDUS,2);
        game.playCard(Player.FINDUS, uno);
        game.endTurn();
        // Peddersen skips his turn
        game.endTurn();
        // Findus plays "Tres" on his next turn
        Card tres = game.getCardInHand(Player.FINDUS,0);
        game.playCard(Player.FINDUS, tres);
        // When Peddersen tries to attack "Uno" with "Tres" on Findus' turn
        Status status = game.attackCard(Player.PEDDERSEN, uno, tres);
        // Then he is not allowed and gets the status message "not player in turn"
        assertThat(status, is(Status.NOT_PLAYER_IN_TURN));
    }

    @Test
    public void shouldNotPrintTranscriptIfAttackNotValid(){
        // Given a game
        // Findus play card "Uno" on his turn
        Card uno = game.getCardInHand(Player.FINDUS,2);
        game.playCard(Player.FINDUS, uno);
        game.endTurn();
        // Peddersen plays card "Dos" on his turn
        Card dos = game.getCardInHand(Player.PEDDERSEN,1);
        game.playCard(Player.PEDDERSEN, dos);
        game.endTurn();
        // Findus plays "cuatro" on his next turn
        Card cuatro = game.getCardInHand(Player.FINDUS,0);
        game.playCard(Player.FINDUS, cuatro);
        game.endTurn();
        // Peddersen attacks Findus' "Uno" with Peddersens "Dos"
        game.attackCard(Player.PEDDERSEN, dos, uno);
        // When Peddersen tries to attack Findus' "cuatro" with Peddersens "Dos"
        Status status = game.attackCard(Player.PEDDERSEN, dos, cuatro);
        // Then he is not allowed and get the status message "attack not allowed for non-active minion"
        assertThat(status, is(Status.ATTACK_NOT_ALLOWED_FOR_NON_ACTIVE_MINION));
    }

    @Test
    public void shouldLoseOneHealthOnCardDosWhenAttackedByUno() {
        // Given a game
        // Findus plays "Uno"
        game.playCard(game.getPlayerInTurn(), game.getCardInHand(game.getPlayerInTurn(), 2));
        game.endTurn();
        // Peddersen plays "Dos"
        game.playCard(game.getPlayerInTurn(), game.getCardInHand(game.getPlayerInTurn(), 1));
        game.endTurn();
        // Findus attacks Peddersens "Dos" with his own "Uno"
        Card uno = game.getCardInField(Player.FINDUS, 0);
        Card dos = game.getCardInField(Player.PEDDERSEN, 0);
        game.attackCard(game.getPlayerInTurn(), uno, dos);
        // When checking the health of Peddersens "Dos"
        // Then shows that "Dos" has one health left
        assertThat(dos.getHealth(), is(1));
    }
    @Test
    public void shouldLoseHealthAsAttackingCard() {
        // Given a game
        // Findus plays "Dos"
        game.playCard(game.getPlayerInTurn(), game.getCardInHand(game.getPlayerInTurn(), 1));
        game.endTurn();
        // Peddersen plays "Uno"
        game.playCard(game.getPlayerInTurn(), game.getCardInHand(game.getPlayerInTurn(), 2));
        game.endTurn();
        // When Findus attacks Peddersens "Uno" with his own "Dos"
        Card dos = game.getCardInField(Player.FINDUS, 0);
        Card uno = game.getCardInField(Player.PEDDERSEN, 0);
        game.attackCard(game.getPlayerInTurn(), dos, uno);
        // Then Findus' "Dos" should lose one health left from attacking Peddersens "Uno"
        assertThat(dos.getHealth(), is(1));
    }

    @Test
    public void shouldLoseOneHealthOnPeddersensHeroAfterAttackedByUnoCard() {
        // Given a game
        Player player = game.getPlayerInTurn();
        // Findus plays "Uno"
        game.playCard(player, game.getCardInHand(player, 2));
        game.endTurn();
        // Peddersen skips his turn
        game.endTurn();
        // When Findus attacks Peddersens hero with his own "Uno"
        game.attackHero(player, game.getCardInField(player, 0));
        // Then Peddersens hero should lose one health and have twenty left
        assertThat(game.getHero(Player.PEDDERSEN).getHealth(), is(20));
    }

    @Test
    public void shouldLoseTwoHealthOnFindusHeroAfterAttackedByDosCard() {
        // Given a game
        // Findus skips his turn
        game.endTurn();
        // Peddersen plays "Dos"
        Player player = game.getPlayerInTurn();
        game.playCard(player, game.getCardInHand(player, 1));
        game.endTurn();
        // Findus skips his second turn
        game.endTurn();
        // When Peddersen attack Findus' hero with Peddersens "Dos"
        game.attackHero(player, game.getCardInField(player, 0));

        // Then Findus' hero loses two health and have nineteen left
        assertThat(game.getHero(Player.FINDUS).getHealth(), is(19));
    }

}
