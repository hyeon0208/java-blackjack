package controller;

import domain.BlackJackGame;
import domain.Deck;
import domain.Gamers;
import domain.PlayerResults;
import domain.Players;
import domain.ShuffledCardsGenerator;
import domain.gamer.Dealer;
import domain.gamer.Gamer;
import domain.gamer.Name;
import domain.gamer.Player;
import java.util.List;
import java.util.function.Supplier;
import view.InputView;
import view.OutputView;

public class BlackJackController {

    public void start() {
        Gamers gamers = readGamers();
        Deck deck = createDeck();
        BlackJackGame blackJackGame = new BlackJackGame(deck);
        blackJackGame.prepareCards(gamers);
        OutputView.printInitialCardsMessage(gamers);
        handOutCard(blackJackGame, gamers);
        OutputView.printCardsAndResult(gamers);
        PlayerResults playerResults = blackJackGame.findPlayerResult(gamers);
        OutputView.printFinalGameResult(playerResults);
    }

    private Gamers readGamers() {
        List<String> names = InputView.readPlayerNames();
        Players players = createPlayers(names);
        Dealer dealer = new Dealer();
        return Gamers.of(players, dealer);
    }

    private Deck createDeck() {
        ShuffledCardsGenerator shuffledCardsGenerator = new ShuffledCardsGenerator();
        return Deck.from(shuffledCardsGenerator.generate());
    }

    private Players createPlayers(final List<String> names) {
        List<Player> players = names.stream()
                .map(name -> new Player(new Name(name)))
                .toList();
        return new Players(players);
    }

    private void handOutCard(final BlackJackGame blackJackGame, final Gamers gamers) {
        List<Player> players = gamers.findPlayers();
        askPlayersHit(blackJackGame, players);

        Dealer dealer = gamers.findDealer();
        askDealerHit(blackJackGame, dealer);
    }

    private void askPlayersHit(final BlackJackGame blackJackGame, final List<Player> players) {
        for (Player player : players) {
            askSelection(blackJackGame, player);
        }
    }

    private void askSelection(final BlackJackGame blackJackGame, final Player player) {
        while (!player.isBust() && isRetry(blackJackGame, player)) {
            OutputView.printAllCards(player);
        }
    }

    private boolean isRetry(final BlackJackGame blackJackGame, final Player player) {
        if (!InputView.readSelectionOf(player)) {
            return false;
        }
        return tryGiveCard(blackJackGame, player);
    }

    private void askDealerHit(final BlackJackGame blackJackGame, final Dealer dealer) {
        while (tryGiveCard(blackJackGame, dealer)) {
            OutputView.printDealerHit(dealer);
        }
    }

    private boolean tryGiveCard(final BlackJackGame blackJackGame, final Gamer gamer) {
        try {
            blackJackGame.giveCard(gamer);
            return true;
        } catch (IllegalArgumentException exception) {
            return false;
        }
    }
}
