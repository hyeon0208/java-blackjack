package domain.gamer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.card.Card;
import domain.card.Rank;
import domain.card.Symbol;
import exception.NotAllowedNameException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PlayerTest {
    Deque<Card> cards;
    Player player;

    @BeforeEach
    void init() {
        Card card1 = new Card(Symbol.HEART, Rank.NINE);
        Card card2 = new Card(Symbol.SPADE, Rank.QUEEN);
        Card card3 = new Card(Symbol.SPADE, Rank.THREE);

        cards = Stream.of(card1, card2, card3)
                .collect(Collectors.toCollection(ArrayDeque::new));

        Name name = new Name("lini");
        player = new Player(name);
    }

    @DisplayName("플레이어가 카드를 뽑아서 저장한다.")
    @Test
    void hitTest() {
        // when
        player.hit(cards.removeLast());

        // then
        assertThat(player.getCardsInHand()).hasSize(1);
    }

    @DisplayName("플레이어가 가진 카드의 점수를 알 수 있다.")
    @Test
    void calculateTotalScoreTest() {
        // given
        player.hit(cards.removeLast());
        player.hit(cards.removeLast());

        int expectedScore = 13;

        // when
        int result = player.calculateTotalScore();

        // then
        assertThat(result).isEqualTo(expectedScore);
    }

    @DisplayName("플레이어가 버스트인지 확인한다.")
    @Test
    void isBust() {
        // given
        player.hit(cards.removeLast());
        player.hit(cards.removeLast());
        player.hit(cards.removeLast());

        // when
        boolean bust = player.isBust();

        // then
        assertThat(bust).isTrue();
    }

    @DisplayName("플레이어가 카드를 더 받지 않는다.")
    @Test
    void isNotStayTest() {
        // given
        player.hit(cards.removeLast());
        player.hit(cards.removeLast());
        player.hit(cards.removeLast());

        // when
        boolean stay = player.isOverTurn();

        // then
        assertThat(stay).isTrue();
    }

    @DisplayName("플레이어가 카드를 더 빋는다.")
    @Test
    void isStayTest() {
        // given
        player.hit(cards.removeLast());
        player.hit(cards.removeLast());

        // when
        boolean stay = player.isOverTurn();

        // then
        assertThat(stay).isFalse();
    }

    @DisplayName("플레이어는 '딜러'와 동일한 이름을 사용하면 예외를 던진다.")
    @Test
    void invalidNameTest() {
        // given
        Name name = new Name("딜러");

        // then
        assertThatThrownBy(() -> new Player(name))
                .isInstanceOf(NotAllowedNameException.class)
                .hasMessage(NotAllowedNameException.NOT_ALLOWED_NAME);
    }
}