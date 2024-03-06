package domain;

import java.util.List;

public class Player {
    private final Name name;
    private final Hand hand;

    public Player(final Name name) {
        this.name = name;
        this.hand = new Hand();
    }

    public void hit(Card card) {
        hand.add(card);
    }

    public Name getName() {
        return name;
    }

    public List<Card> getHand() {
        return hand.getCards();
    }
}