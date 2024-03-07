package domain;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class BlackJackGame {
    private static final int INITIAL_CARD_COUNT = 2;
    private final Decks decks;

    public BlackJackGame(final Decks decks) {
        this.decks = decks;
    }

    public void prepareCards(Gamers gamers) {
        for (Gamer gamer : gamers.getGamers()) {
            drawTwoCardsForGamer(gamer);
        }
    }

    private void drawTwoCardsForGamer(final Gamer gamer) {
        for (int count = 0; count < INITIAL_CARD_COUNT; count++) {
            gamer.hit(decks.draw());
        }
    }

    public boolean succeededGiving(Gamer gamer) {
        if (!gamer.isStay()) {
            gamer.hit(decks.draw());
            return true;
        }
        return false;
    }

    public PlayerResults findPlayerResult(Gamers gamers){
        Dealer dealer = gamers.findDealer();
        Map<Player, Result> playerResults = new HashMap<>();
        for (Player player : gamers.findPlayers()) {
            Result playerResult = isPlayerWin(dealer, player);
            playerResults.put(player,playerResult);
        }
        return new PlayerResults(playerResults);
    }

    private Result isPlayerWin(Dealer dealer, Player player) {
        if (!player.isBust() && dealer.isBust()) {
            return Result.WIN;
        }

        if (!dealer.isBlackJack()) {
            if (player.calculateTotalScore() == dealer.calculateTotalScore() && player.isBlackJack()) {
                return Result.WIN;
            }

            if (player.calculateTotalScore() > dealer.calculateTotalScore()) {
                return Result.WIN;
            }
        }

        if (player.isBlackJack() && dealer.isBlackJack()) {
            return Result.TIE;
        }
        if (player.calculateTotalScore() == dealer.calculateTotalScore() && !player.isBlackJack() && !dealer.isBlackJack()) {
            return Result.TIE;
        }
        return Result.LOSE;
    }
}