import java.util.ArrayList;
import java.util.List;

public class Player {

    private final List<Card> hand;
    private boolean repeatCard = false;
    private Card repeatAsk;

    public Player() {
        this.hand = new ArrayList<>();
    }

    public List<Card> getHand() {
        return hand;
    }

    public void takeCard(Card card) {
        hand.add(card);
        sortHand();
    }

    public boolean hasCard(String ranked) {
        for (Card c : hand) {
            if (c.getRank().equals(ranked)) {
                return true;    // yes, they have the card
            }
        }

        return false;   // no, they don't
    }

    private void sortHand() {
        hand.sort((a, b) -> {
            if (Card.getOrderedRank(a.getRank()) == Card.getOrderedRank(b.getRank())) {
                return Card.getOrderedSuit(a.getSuit()) - Card.getOrderedSuit(b.getSuit());     // order by suit if
            }                                                                                   // ranks are the same
            return Card.getOrderedRank(a.getRank()) - Card.getOrderedRank(b.getRank());         // otherwise, by rank
        });
    }


    public void clearList(){
        hand.clear();
    }


}