package view;

import domain.card.Card;
import domain.gamer.Dealer;
import domain.gamer.Gamer;
import domain.gamer.Player;
import domain.gamer.Players;
import domain.result.PlayerResults;
import domain.result.Result;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class OutputView {
    private static final int INITIAL_CARD_COUNT = 2;
    private static final int DEALER_HIT_CONDITION = 16;

    private OutputView() {

    }

    public static void printInitialCardsMessage(final Dealer dealer, final Players players) {
        printHandOutMessage(dealer, players);
        printDealerCard(dealer);
        printPlayersCards(players);
        printNewLine();
    }

    private static void printHandOutMessage(final Dealer dealer, final Players players) {
        String dealerName = dealer.getName();
        StringJoiner playerNames = new StringJoiner(",");
        for (Player player : players.getPlayers()) {
            playerNames.add(player.getName());
        }
        String message = String.format(System.lineSeparator() + "%s와 %s에게 %d장을 나누었습니다.", dealerName, playerNames,
                INITIAL_CARD_COUNT);
        System.out.println(message);
    }

    private static void printDealerCard(final Dealer dealer) {
        System.out.println(dealer.getName() + ": " + makeCardInfo(dealer.openFirstCard()));
    }

    private static void printPlayersCards(final Players players) {
        for (Player player : players.getPlayers()) {
            printAllCards(player);
        }
    }

    public static void printAllCards(final Player player) {
        String cardInfos = String.join(", ", makeCardInfos(player.getCardsInHand()));
        String message = String.format("%s카드: %s", player.getName(), cardInfos);
        System.out.println(message);
    }

    public static void printDealerHit(final Dealer dealer) {
        String dealerName = dealer.getName();
        String message = String.format("%s는 %d이하라 한장의 카드를 더 받았습니다.", dealerName, DEALER_HIT_CONDITION);
        System.out.println(message);
    }

    public static void printCardsAndResult(final Dealer dealer, final Players players) {
        StringBuilder builder = new StringBuilder();
        builder.append(makeCardsAndResultMessage(dealer))
                .append(System.lineSeparator());

        for (Player player : players.getPlayers()) {
            builder.append(makeCardsAndResultMessage(player))
                    .append(System.lineSeparator());
        }
        System.out.println(builder);
    }

    private static String makeCardsAndResultMessage(final Gamer gamer) {
        String gamerName = gamer.getName();
        int totalScore = gamer.calculateTotalScore();
        String cardInfos = String.join(", ", makeCardInfos(gamer.getCardsInHand()));
        return String.format("%s 카드: %s - 결과: %d", gamerName, cardInfos, totalScore);
    }

    private static List<String> makeCardInfos(final List<Card> cards) {
        List<String> cardInfos = new ArrayList<>();
        for (Card card : cards) {
            cardInfos.add(makeCardInfo(card));
        }
        return cardInfos;
    }

    private static String makeCardInfo(final Card card) {
        String symbol = card.getSymbolValue();
        String rank = card.getRankValue();
        return rank + symbol;
    }

    public static void printFinalGameResult(final PlayerResults playerResults) {
        System.out.println("## 최종 승패");
        printDealerResult(playerResults);
        printPlayerResults(playerResults);
    }

    private static void printDealerResult(final PlayerResults playerResults) {
        int winCount = playerResults.findWinCount();
        int loseCount = playerResults.findLoseCount();
        int tieCount = playerResults.findTieCount();
        String message = String.format("딜러: %d승 %d패 %d무", loseCount, winCount, tieCount);
        System.out.println(message);
    }

    private static void printPlayerResults(final PlayerResults playerResults) {
        Set<Entry<Player, Result>> resultEntrySet = playerResults.getResults().entrySet();
        String resultString = resultEntrySet.stream()
                .map(entry -> String.format("%s: %s", entry.getKey().getName(), entry.getValue().getName()))
                .collect(Collectors.joining(System.lineSeparator()));
        System.out.println(resultString);
    }

    public static void printNewLine() {
        System.out.println();
    }
}
