import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Poker {


        private final String[] SUITS = { "C", "D", "H", "S" };
        private final String[] RANKS = { "A", "2", "3", "4", "5", "6", "7", "8", "9", "T", "J", "Q", "K" };


        private char whoseTurn;
        private final Player player;
        private List<Card> deck;
        private final Scanner in;
        private String temp = " ";
        public int turn = 1;
        public int chips = 25;
        public int bet = 0;
        public boolean pStand = false;
        public int swapCount = 0;
        public String swapOne;
        public String swapTwo;
        public String swapThree;
        public boolean swapCheck = false;
        public String activeCard;

        public boolean royalFlush = false;
        public boolean straightFlush = false;
        public boolean fourKind = false;
        public boolean fullHouse = false;
        public boolean flush = false;
        public boolean straight = false;
        public boolean threeKind = false;
        public boolean twoPair = false;
        public boolean pair = false;
        public int maxCount = 0;
        private boolean dups = false;
        public boolean validPair = false;






        public Poker() {
                this.whoseTurn = 'P';
                this.player = new Player();
                this.in = new Scanner(System.in);

        }

        public void play() {

                while (true) {
                        whoseTurn = 'P';
                        pStand = false;
                        turn = 1;
                        player.clearList();
                        swapCount = 0;
                        maxCount = 0;
                        flush = false;
                        straight = false;
                        dups = false;
                        validPair = false;
                        swapCheck = false;
                        temp = "";

                        if (chips > 0) {
                                do {
                                        System.out.println("\nHow many chips do you want to bet? (25 Max)");
                                        System.out.println("You currently have: " + chips);
                                        bet = in.nextInt();
                                } while (bet > chips || bet <= 0 || bet > 25);
                        } else {
                                do {
                                        System.out.println("You ran out of chips do you want to buy back in 25? (Yes or No)");
                                        temp = in.nextLine();
                                } while (!temp.toLowerCase().equals("yes") && !temp.toLowerCase().equals("no"));

                                if (temp.equals("no")) {
                                        System.out.println("Ok have a great day!");
                                        break;
                                }
                        }

                        if (temp.equals("yes")) {
                                chips = 25;
                                do {
                                        System.out.println("\nHow many chips do you want to bet? (25 Max)");
                                        System.out.println("You currently have: " + chips);
                                        bet = in.nextInt();
                                } while (bet > chips || bet <= 0 || bet > 25);
                        }

                        shuffleAndDeal();

                        while (true) {
                                if (!pStand) {
                                      takeTurn(false);
                                }
                                        if (pStand) {
                                                while (player.getHand().size() < 5) {
                                                        player.takeCard(deck.remove(0));
                                                }

                                               System.out.println("Current hand: " + player.getHand());
                                                flushCheck();
                                                straightCheck();
                                                duplicateChecks();
                                                highPair();
                                                if(straight && flush && player.hasCard("A")){
                                                        System.out.println("You got a ROYAL FLUSH");
                                                        chips += bet*100;
                                                        break;
                                                }else if(straight && flush){
                                                        System.out.println("You got a STRAIGHT FLUSH");
                                                        chips += bet*50;
                                                        break;
                                                }else if(maxCount == 4){
                                                        System.out.println("You got 4 OF A KIND");
                                                        chips += bet*25;
                                                        break;
                                                }else if(maxCount == 3 && dups == true){
                                                        System.out.println("You got a FULL HOUSE");
                                                        chips += bet*15;
                                                        break;
                                                }else if(flush == true){
                                                        System.out.println("You got a FLUSH");
                                                        chips += bet*10;
                                                        break;
                                                }else if(straight == true){
                                                        System.out.println("You got a STRAIGHT");
                                                        chips += bet*5;
                                                        break;
                                                }else if(maxCount == 3 && dups == false){
                                                        System.out.println("You got a THREE OF A KIND");
                                                        chips += bet*3;
                                                        break;
                                                }else if(maxCount == 2 && dups == true){
                                                        System.out.println("You got a TWO PAIR");
                                                        chips += bet*2;
                                                        break;
                                                }else if(maxCount == 2 && validPair == true){
                                                        System.out.println("You got a HIGH RANKED PAIR");
                                                        chips += bet*2;
                                                        break;
                                                }else{
                                                        System.out.println("You got a LOSING HAND");
                                                        chips -= bet;
                                                        break;
                                                }


                                        }









                                }

                        }
                }

       public void flushCheck() {
               for( int i = 1; player.getHand().size() > i ; i++) {
                       if(!player.getHand().get(i).getSuit().equals(player.getHand().get(0).getSuit())){
                               flush = false;
                               break;
                       }else{
                               flush = true;
                       }
               }
       }

       public void straightCheck(){
               for(int i = 0; player.getHand().size() > i ; i++) {
                       if(Card.getOrderedRank(player.getHand().get(i).getRank())+1 != Card.getOrderedRank(player.getHand().get(i+1).getRank())){
                               straight = false;
                               break;
                       }else{
                               straight = true;
                       }
               }

       }

        public void duplicateChecks(){
                for(int i = 0; player.getHand().size() > i ; i++){
                        int tempCount = 0;
                        for(int ii = 0; player.getHand().size() > ii ; ii++){
                                if(Card.getOrderedRank(player.getHand().get(i).getRank()) == Card.getOrderedRank(player.getHand().get(ii).getRank())){
                                       tempCount++;
                                }
                        }
                        if(maxCount < tempCount){
                                maxCount = tempCount;
                        }
                }
        }

        public void highPair() {
                for (int i = 1; player.getHand().size() > i; i++) {
                                if (Card.getOrderedRank(player.getHand().get(i).getRank()) == Card.getOrderedRank(player.getHand().get(i-1).getRank())) {
                                        if (Card.getOrderedRank(player.getHand().get(i).getRank()) > 10) {
                                                validPair = true;
                                                break;
                                        }
                                }
                        }
                }

        public void numOfDuplicates(){
                if(Card.getOrderedRank(player.getHand().get(0).getRank()) == Card.getOrderedRank(player.getHand().get(1).getRank()) || Card.getOrderedRank(player.getHand().get(1).getRank()) == Card.getOrderedRank(player.getHand().get(2).getRank()) && Card.getOrderedRank(player.getHand().get(2).getRank()) == Card.getOrderedRank(player.getHand().get(3).getRank()) || Card.getOrderedRank(player.getHand().get(3).getRank()) == Card.getOrderedRank(player.getHand().get(4).getRank())) {
                       dups = true;
                }

        }


        public void takeTurn(boolean cpu) {
                        if(!pStand) {
                                System.out.println("Current hand: " + player.getHand());
                                System.out.println("What will you do (Swap or Stand)");

                                while (!temp.toLowerCase().equals("swap") && !temp.toLowerCase().equals("stand")){
                                        temp = in.nextLine();
                                }

                                if (temp.toLowerCase().equals("swap")) {
                                        System.out.println("What cards do you want to swap? (Max 3)");
                                        System.out.println("Current hand: " + player.getHand());

                                        while(!swapCheck){
                                                System.out.println("First card to swap: ");
                                                activeCard = in.nextLine().toUpperCase();
                                                Check();
                                        }
                                        swapCheck = false;

                                        while(!swapCheck){
                                                System.out.println("Second card to swap: (Type 'no' if done swapping)");
                                                System.out.println("Current hand: " + player.getHand());
                                                activeCard = in.nextLine().toUpperCase();
                                                Check();
                                        }

                                        swapCheck = false;

                                        while(!swapCheck){
                                                System.out.println("Third card to swap: (Type 'no' if done swapping)");
                                                System.out.println("Current hand: " + player.getHand());
                                                activeCard = in.nextLine().toUpperCase();
                                                Check();
                                        }


                                } else if (temp.toLowerCase().equals("stand")) {
                                        pStand = true;
                                        temp = " ";
                                }
                        }
        }

        public void Check(){
                for( int i = 1; player.getHand().size() >= i /*MIGHT HAVE TO BE i-1*/; i++){
                        String temp = player.getHand().subList(i-1, i).toString();
                        temp = temp.substring(1,3);
                        if(temp.equals(activeCard)){
                                player.getHand().remove(i-1);
                                swapCount++;
                                swapCheck = true;
                                if(swapCount == 3){
                                        pStand = true;
                                }
                        }else if(activeCard.toUpperCase().equals("NO")){
                                swapCheck = true;
                                pStand = true;
                        }
                }
        }

        public void getScores(int j){
        }



        public void shuffleAndDeal() {
                deck = null;
                if (deck == null) {
                        initializeDeck();
                }
                Collections.shuffle(deck);  // shuffles the deck

                while (player.getHand().size() < 5) {
                        player.takeCard(deck.remove(0));
                }
        }


        private void initializeDeck() {
                deck = new ArrayList<>(52);

                for (String suit : SUITS) {
                        for (String rank : RANKS) {
                                deck.add(new Card(rank, suit));
                        }
                }
        }

        public static void main(String[] args) {
                System.out.println("\nWELCOME TO POKER");
                System.out.println("Your goal is to WIN BIG");

                new Poker().play();

        }

}

