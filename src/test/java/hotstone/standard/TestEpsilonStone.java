package hotstone.standard;

/** Here we write test for epsilonStone */


import hotstone.framework.*;
import hotstone.framework.card.Card;
import hotstone.framework.game.Game;
import hotstone.standard.factories.FactoryEpsilon;
import hotstone.standard.random.RandomSelectorDouble;
import hotstone.utility.TestHelper;
import org.junit.jupiter.api.*;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestEpsilonStone {

    private Game game;

    /** Fixture for AlphaStone testing. */
    @BeforeEach
    public void setUp() {
        game = new StandardHotStoneGame(new FactoryEpsilon(new RandomSelectorDouble(0)));
    }



    @Test
    public void shouldHaveTheCorrectHeroAtStartOfGame(){
        //Given a game
        Player player = game.getPlayerInTurn();
        assertThat(game.getHero(player).getType(), is(GameConstants.FRENCH_CHEF_HERO_TYPE));

        game.endTurn();

        player = game.getPlayerInTurn();
        assertThat(game.getHero(player).getType(), is(GameConstants.ITALIAN_CHEF_HERO_TYPE));
    }

    @Test
    public void shouldFinudsRemoveTwoHealthOnAMinionOnUsePower() {
        //Given a game
        // Findus skips his first turn
        game.endTurn();
        // Player in turn is Peddersen

        Player player = game.getPlayerInTurn();
        // When Peddersen plays his "Uno" card in index two of his hand
        game.playCard(player,game.getCardInHand(player,2));

        //Then its Findus turn
        game.endTurn();

        //Set player to Findus
        player = game.getPlayerInTurn();

        game.usePower(player);

        //After used power uno should die and there are no cards on the field
        assertThat(game.getFieldSize(Player.PEDDERSEN), is(0));
    }

    @Test
    public void shouldTakeTwoDamageOnMinionAfterFindusPowerUse() {
        // Given a game
        game.endTurn();
        game.endTurn();
        game.endTurn();

        // When Peddersen plays tres
        Player player = game.getPlayerInTurn();
        game.playCard(player, game.getCardInHand(player, 1));

        // and when Findus plays a card
        game.endTurn();
        player = game.getPlayerInTurn();
        game.playCard(player, game.getCardInHand(player, 3));

        // and when Peddersen plays uno
        game.endTurn();
        player = game.getPlayerInTurn();
        game.playCard(player, game.getCardInHand(player, 3));

        // and when Findus uses his power
        game.endTurn();
        player = game.getPlayerInTurn();
        game.usePower(player);

        // (The 'randomly' selected minion will be index 0 in tests)
        // Then Tres should have 3-2 health left
        Card tres = game.getCardInField(Player.PEDDERSEN, 0);
        assertThat(tres.getHealth(), is(3-2));
    }

    @Test
    public void shouldGiveTwoExtraAttackPowerOnOwnMinionOnPeddersenPowerUse() {
        // Given a game
        game.endTurn();

        // When Peddersen plays uno
        Player player = game.getPlayerInTurn();
        Card unoCard = game.getCardInHand(player, 2);
        game.playCard(player, unoCard);

        int formerAttack = unoCard.getAttack();

        game.endTurn();
        game.endTurn();

        // and when Pedddersen uses his power
        game.usePower(player);

        // Then the uno card should have 1+2 attack
        assertThat(unoCard.getAttack(), is(formerAttack + 2));
    }

    @Test
    public void shouldGiveTwoExtraAttackPowerOnOneOfPeddersensMinionsOnPeddersenPowerUse() {
        // Given a game
        game.endTurn();
        game.endTurn();
        game.endTurn();

        // When Peddersen plays tres
        Player player = game.getPlayerInTurn();
        Card tresCard = game.getCardInHand(player, 1);
        game.playCard(player, tresCard);

        // and when Findus plays a card
        game.endTurn();
        player = game.getPlayerInTurn();
        game.playCard(player, game.getCardInHand(player, 3));

        // and when Peddersen plays uno
        game.endTurn();
        player = game.getPlayerInTurn();
        game.playCard(player, game.getCardInHand(player, 3));

        // (The 'randomly' selected minion will be index 0 in tests)
        // and when Peddersen uses his power
        game.endTurn();
        game.endTurn();
        player = game.getPlayerInTurn();
        int formerAttackTres = tresCard.getAttack();

        game.usePower(player);

        // Then the tres card should have 3+2 attack power
        assertThat(tresCard.getAttack(), is(formerAttackTres + 2));
    }
    @Test
    public void shouldWinAfterSumOfMinionAttackIsSeven(){
        // Given a game
        // Findus plays Uno
        Player findus = game.getPlayerInTurn();
        Card findus_uno = game.getCardInHand(findus, 2);
        game.playCard(findus, findus_uno);
        game.endTurn();
        // Peddersen plays Uno
        Player peddersen = game.getPlayerInTurn();
        Card peddersen_uno = game.getCardInHand(peddersen, 2);
        game.playCard(peddersen, peddersen_uno);
        game.endTurn();
        // Findus plays Dos
        Card findus_dos = game.getCardInHand(findus, 2);
        game.playCard(findus, findus_dos);
        game.endTurn();
        // Peddersen plays Dos
        Card peddersen_dos = game.getCardInHand(peddersen, 2);
        game.playCard(peddersen, peddersen_dos);
        game.endTurn();
        // Findus plays Tres
        Card findus_tres = game.getCardInHand(findus, 2);
        game.playCard(findus, findus_tres);
        game.endTurn();
        // Peddersen plays Tres
        Card peddersen_tres = game.getCardInHand(peddersen, 2);
        game.playCard(peddersen, peddersen_tres);
        game.endTurn();
        // Findus plays Cinco
        Card findus_cinco = game.getCardInHand(findus, 2);
        game.playCard(findus, findus_cinco);
        game.endTurn();
        // Peddersen plays Cinco
        Card peddersen_cinco = game.getCardInHand(peddersen, 2);
        game.playCard(peddersen, peddersen_cinco);
        game.endTurn();
        // Findus attack Peddersens Cinco with his Cinco
        game.attackCard(findus, findus_cinco, peddersen_cinco);
        // Findus has four in attack-sum and Peddersen has zero.
        game.endTurn();
        // Peddersen attack Findus' Uno with his Uno
        game.attackCard(peddersen, peddersen_uno, findus_uno);
        game.endTurn();
        // Findus attack Peddersens Dos with his Dos
        game.attackCard(findus, findus_dos, peddersen_dos);
        // Findus has six in attack-sum and Peddersen has one.
        assertThat(game.getWinner()==null, is(true));
        game.endTurn();
        // Peddersen does nothing
        game.endTurn();
        // Findus attack Peddersens Dos with his Dos
        game.attackCard(findus, findus_tres, peddersen_tres);
        // Findus has nine in attack-sum and Peddersen has one.
        // Findus wins
        game.endTurn();
        assertThat(game.getWinner(), is(Player.FINDUS));
    }
    @Test
    public void shouldWinAfterSumOfMinionAttackIsSevenElevenHeHe() {
        TestHelper.printGameState(game);
        // Given a game
        // Findus plays Uno
        Player findus = game.getPlayerInTurn();
        Card findus_uno = game.getCardInHand(findus, 2);
        game.playCard(findus, findus_uno);
        game.endTurn();
        // Peddersen plays Uno
        Player peddersen = game.getPlayerInTurn();
        Card peddersen_uno = game.getCardInHand(peddersen, 2);
        game.playCard(peddersen, peddersen_uno);
        game.endTurn();
        // Findus plays Dos
        Card findus_dos = game.getCardInHand(findus, 2);
        game.playCard(findus, findus_dos);
        game.endTurn();
        // Peddersen plays Dos
        Card peddersen_dos = game.getCardInHand(peddersen, 2);
        game.playCard(peddersen, peddersen_dos);
        game.endTurn();
        // Findus plays Tres
        Card findus_tres = game.getCardInHand(findus, 2);
        game.playCard(findus, findus_tres);
        game.endTurn();
        // Peddersen plays Tres
        Card peddersen_tres = game.getCardInHand(peddersen, 2);
        game.playCard(peddersen, peddersen_tres);
        game.endTurn();
        // Findus plays Cuatro
        Card findus_cuatro = game.getCardInHand(findus, 2);
        game.playCard(findus, findus_cuatro);
        game.endTurn();
        // Peddersen plays Cuatro
        Card peddersen_cuatro = game.getCardInHand(peddersen, 2);
        game.playCard(peddersen, peddersen_cuatro);
        game.endTurn();
        // Findus plays Cinco
        Card findus_cinco = game.getCardInHand(findus, 2);
        game.playCard(findus, findus_cinco);
        game.endTurn();
        // Peddersen plays Cinco
        Card peddersen_cinco = game.getCardInHand(peddersen, 2);
        game.playCard(peddersen, peddersen_cinco);
        game.endTurn();
        // Findus plays Seis
        Card findus_seis = game.getCardInHand(findus, 1);
        game.playCard(findus, findus_seis);
        game.endTurn();
        // Peddersen plays Seis
        Card peddersen_seis = game.getCardInHand(peddersen, 1);
        game.playCard(peddersen, peddersen_seis);
        game.endTurn();
        // Findus plays Siete
        Card findus_Siete = game.getCardInHand(findus, 0);
        game.playCard(findus, findus_Siete);
        game.endTurn();
        // Peddersen plays Siete
        Card peddersen_Siete = game.getCardInHand(peddersen, 0);
        game.playCard(peddersen, peddersen_Siete);
        game.endTurn();
        TestHelper.printGameState(game);

        // Findus attack Peddersens Cuatro with his Cuatro: 3 attack
        game.attackCard(findus, findus_cuatro, peddersen_cuatro);
        game.endTurn();
        // Peddersen attack Findus' Uno with his Uno: 1 attack
        game.attackCard(peddersen, peddersen_uno, findus_uno);
        game.endTurn();
        // Findus has three in attack-sum and Peddersen has one.
        // Findus attack Peddersens Dos with his Dos: 2 attack
        game.attackCard(findus, findus_dos, peddersen_dos);
        game.endTurn();
        // Peddersen attack Findus' Tres with his Tres: 3 attack
        game.attackCard(peddersen, peddersen_tres, findus_tres);
        game.endTurn();
        // Findus has five in attack-sum and Peddersen has four.
        // Findus attack Peddersens Seis with his Seis: 1 attack
        game.attackCard(findus, findus_seis, peddersen_seis);
        game.endTurn();
        // Peddersen attack Findus' Cinco with his Cinco:
        game.attackCard(peddersen, peddersen_cinco, findus_cinco);
        // Findus has six in attack-sum and Peddersen has nine.

        // Peddersen wins
        game.endTurn();
        assertThat(game.getWinner(), is(Player.PEDDERSEN));
        // Findus attack Peddersen Siete with his Siete:
        game.attackCard(findus, findus_Siete, peddersen_Siete);
        // Peddersen is still winner
        assertThat(game.getWinner(), is(Player.PEDDERSEN));


    }
}
