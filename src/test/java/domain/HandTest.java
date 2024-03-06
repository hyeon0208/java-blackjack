package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HandTest {

    @DisplayName("뽑은 카드를 저장한다.")
    @Test
    void addCardTest() {
        // given
        Hand hand = new Hand();
        Card expectedCard = new Card(Symbol.HEART, Rank.ACE);

        // when
        hand.add(expectedCard);
        List<Card> result = hand.getCards();

        // then
        assertAll(
                () -> assertThat(result).hasSize(1),
                () -> assertThat(result.get(0)).isEqualTo(expectedCard)
        );
    }

    @DisplayName("가지고 있는 카드의 총 합계 점수를 구한다.")
    @Test
    void sumScoreTest() {
        // given
        int expectedScore = 21;

        Hand hand = new Hand();
        Card card1 = new Card(Symbol.HEART, Rank.TWO);
        Card card2 = new Card(Symbol.CLOVER, Rank.NINE);
        Card card3 = new Card(Symbol.SPADE, Rank.QUEEN);

        hand.add(card1);
        hand.add(card2);
        hand.add(card3);

        // when
        int result = hand.sum();

        // then
        assertThat(result).isEqualTo(expectedScore);
    }
}