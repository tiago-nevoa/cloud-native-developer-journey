package com.sap.ase.poker.model.rules;

import com.sap.ase.poker.fixtures.HandFixtures;
import com.sap.ase.poker.fixtures.PlayerFixtures;
import com.sap.ase.poker.model.Player;
import com.sap.ase.poker.model.deck.Card;
import com.sap.ase.poker.model.hands.HighCard;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

@ExtendWith(MockitoExtension.class)
class WinnerRulesTest {


    WinnerRules underTest = new WinnerRules(new HandRules());

    @Test
    void findWinners_shouldCombineCommunityCardsWithHandCards() {
        Player alWithHighCard = PlayerFixtures.AL_CAPONE();

        List<Card> highCardOfKing = HandFixtures.highCardOfKing();
        List<Card> handCards = highCardOfKing.subList(0, 2);
        List<Card> communityCards = highCardOfKing.subList(2, highCardOfKing.size());
        alWithHighCard.setHandCards(handCards);

        final List<Player> players = Arrays.asList(alWithHighCard);
        Winners winners =
                underTest.findWinners(communityCards, players);

        assertThat(winners.getWinners()).contains(alWithHighCard);
        if (winners.getWinningHand().isPresent()) {
            assertThat(winners.getWinningHand().get()).isInstanceOf(HighCard.class);
        } else {
            fail("");
        }
    }

    @Test
    void whenPairVsHighCard_findWinners_shouldReturnPlayerWithPair() {
        Player alWithHighCard = PlayerFixtures.AL_CAPONE();
        Player patWithPair = PlayerFixtures.PAT_GARRETT();

        alWithHighCard.setHandCards(HandFixtures.highCardOfKing());
        patWithPair.setHandCards(HandFixtures.pairOfSevens());

        final List<Player> players = Arrays.asList(patWithPair, alWithHighCard);
        Winners winners = underTest.findWinners(
                Collections.emptyList(), players);

        assertThat(winners.getWinners()).contains(patWithPair);
        assertThat(winners.getWinners()).doesNotContain(alWithHighCard);
    }

    @Test
    void whenFourOfAKindVsPair_findWinners_shouldReturnPlayerWithFourOfAKind() {
        Player alWithFourOfAKind = PlayerFixtures.AL_CAPONE();
        Player patWithPair = PlayerFixtures.PAT_GARRETT();

        alWithFourOfAKind.setHandCards(HandFixtures.fourOfAKindOfSevens());
        patWithPair.setHandCards(HandFixtures.pairOfSevens());

        final List<Player> players = Arrays.asList(patWithPair, alWithFourOfAKind);
        Winners winners =
                underTest.findWinners(
                        Collections.emptyList(), players);

        assertThat(winners.getWinners()).contains(alWithFourOfAKind);
        assertThat(winners.getWinners()).doesNotContain(patWithPair);
    }

    @Test
    void whenSamePairsCompete_findWinners_shouldReturnBothPlayers() {
        Player alWithPair = PlayerFixtures.AL_CAPONE();
        Player patWithPair = PlayerFixtures.PAT_GARRETT();

        alWithPair.setHandCards(HandFixtures.pairOfRedJacks());
        patWithPair.setHandCards(HandFixtures.pairOfBlackJacks());

        final List<Player> players = Arrays.asList(patWithPair, alWithPair);
        Winners winners = underTest.findWinners(
                Collections.emptyList(), players);

        assertThat(winners.getWinners()).contains(alWithPair, patWithPair);
    }

    @Test
    void whenHighCardAceVsHighCardKing_findWinners_shouldReturnPlayerWithHighCardAce() {
        Player alWithHighCardAce = PlayerFixtures.AL_CAPONE();
        Player patWithHighCardKing = PlayerFixtures.PAT_GARRETT();

        alWithHighCardAce.setHandCards(HandFixtures.highCardOfAce());
        patWithHighCardKing.setHandCards(HandFixtures.highCardOfKing());

        final List<Player> players = Arrays.asList(alWithHighCardAce, patWithHighCardKing);
        Winners winners =
                underTest.findWinners(
                        Collections.emptyList(), players);

        assertThat(winners.getWinners()).contains(alWithHighCardAce);
        assertThat(winners.getWinners()).doesNotContain(patWithHighCardKing);
    }

    @Test
    void whenPairOfSevensVsPairOfNines_findWinners_shouldReturnPlayerWithPairOfSevens() {
        Player alWithPairOfSevens = PlayerFixtures.AL_CAPONE();
        Player patWithPairOfNines = PlayerFixtures.PAT_GARRETT();

        alWithPairOfSevens.setHandCards(HandFixtures.pairOfSevens());
        patWithPairOfNines.setHandCards(HandFixtures.pairOfNines());

        final List<Player> players = Arrays.asList(patWithPairOfNines, alWithPairOfSevens);
        Winners winners =
                underTest.findWinners(
                        Collections.emptyList(), players);

        assertThat(winners.getWinners()).contains(patWithPairOfNines);
        assertThat(winners.getWinners()).doesNotContain(alWithPairOfSevens);
    }

    @Test
    void whenStraightVsThreeOfAKind_findWinners_shouldReturnPlayerWithStraight() {
        Player alWithThreeAces = PlayerFixtures.AL_CAPONE();
        Player patWithFlush = PlayerFixtures.PAT_GARRETT();

        alWithThreeAces.setHandCards(HandFixtures.threeOfAKindOfAces());
        patWithFlush.setHandCards(HandFixtures.FlushWithKing());

        final List<Player> players = Arrays.asList(patWithFlush, alWithThreeAces);
        Winners winners =
                underTest.findWinners(
                        Collections.emptyList(), players);

        assertThat(winners.getWinners()).contains(patWithFlush);
        assertThat(winners.getWinners()).doesNotContain(alWithThreeAces);
    }

    @Test
    void whenPairInCommunityCards_findWinners_shouldReturnPlayerWithHighCard() {

        Player alWithAce = PlayerFixtures.AL_CAPONE();
        Player patWithLowCards = PlayerFixtures.PAT_GARRETT();

        alWithAce.setHandCards(HandFixtures.handWithAce());
        patWithLowCards.setHandCards(HandFixtures.handWithLowCards());
        List<Card> communityCards = HandFixtures.communityCardsWithPairOfTens();
        final List<Player> players = Arrays.asList(alWithAce, patWithLowCards);
        Winners winners = underTest.findWinners(communityCards, players);
        assertThat(winners.getWinners()).hasSize(1);
        assertThat(winners.getWinners()).contains(alWithAce);
        assertThat(winners.getWinners()).doesNotContain(patWithLowCards);
    }

    @Test
    void whenBothHaveThreeOfAKind_findWinners_shouldReturnPlayerWithHigherKickerCard() {
        Player alWithHighCard = PlayerFixtures.AL_CAPONE();
        Player patWithLowCard = PlayerFixtures.PAT_GARRETT();

        alWithHighCard.setHandCards(HandFixtures.handWithTenAndHighCard());
        patWithLowCard.setHandCards(HandFixtures.handWithTenAndLowCard());
        List<Card> communityCards = HandFixtures.communityCardsWithPairOfTens();
        final List<Player> players = Arrays.asList(alWithHighCard, patWithLowCard);
        Winners winners = underTest.findWinners(communityCards, players);
        assertThat(winners.getWinners()).hasSize(1);
        assertThat(winners.getWinners()).contains(alWithHighCard);
        assertThat(winners.getWinners()).doesNotContain(patWithLowCard);
    }
}