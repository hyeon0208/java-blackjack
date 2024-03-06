package domain;

public class Player extends Gamer {

    public Player(final Name name,Decks decks) {
        super(decks);
        this.name = name;
    }

    @Override
    public void hit(final Decks decks) {
        hand.add(decks.draw());
    }

    public Name getName() {
        return name;
    }
}
