import java.util.*;

public class Game {
    private final Pile drawPile;
    private final Pile turnedDrawPile;

    private final Pile heartSuitPile;
    private final Pile diamondSuitPile;
    private final Pile clubSuitPile;
    private final Pile spadeSuitPile;
    private static Pile [] suits;

    private static Pile[] lanes;
    private int score;


    private static Deck deck;

    public Game() {
        //Set up new Deck, and deal the cards
        deck = new Deck();
        drawPile = new Pile();
        turnedDrawPile = new Pile();

        heartSuitPile = new Pile();
        diamondSuitPile = new Pile();
        clubSuitPile = new Pile();
        spadeSuitPile = new Pile();

        suits = new Pile[]{heartSuitPile, diamondSuitPile, clubSuitPile, spadeSuitPile};

        Pile lane1 = new Pile();
        Pile lane2 = new Pile();
        Pile lane3 = new Pile();
        Pile lane4 = new Pile();
        Pile lane5 = new Pile();
        Pile lane6 = new Pile();
        Pile lane7 = new Pile();

        lanes = new Pile[]{lane1, lane2, lane3, lane4, lane5, lane6, lane7};

        score = 0;

        deal();

        //Start by printing table. Continue until Suit piles are full (Game won)!
        printTable();
        while (heartSuitPile.getSize()!= 13 || diamondSuitPile.getSize()!= 13 || spadeSuitPile.getSize()!= 13 || clubSuitPile.getSize()!= 13) {
            getMove();
            printTable();
        }
        System.out.println("\u001B[32m" +"Thanks For Playing!" + "\u001B[0m");
    }

    public void deal() {
        //7 un-flipped cards in the biggest pile
        int pileSize =7;

        //Deal cards to piles, decreasing size every time
        while (pileSize > 0) {
            for (int i = 0; i < pileSize-1; i++) {
                Card topCard = deck.drawTopCard();
                topCard.setFlipped(false);
                lanes[pileSize-1].addCard(topCard);
            }

            //Flip very top card of each pile
            Card topCard = deck.drawTopCard();
            topCard.setFlipped(true);
            lanes[pileSize-1].addCard(topCard);
            pileSize--;
        }
        //Move Remaining Cards from deck to draw pile
        for (Card card: deck.getCards()){
            card.setFlipped(false);
            drawPile.addCard(card);
        }

        //Deal one card face up to turnedDrawPile
        drawPile.moveTopCardTo(turnedDrawPile, true);
    }

    public void printTable() {
        // Determine the maximum pile size across all lanes
        int maxPileHeight = getMaxPileHeight();

        // Print lanes as vertical columns
        System.out.println("Lanes:");
        for (int i =1; i<=7; i++){
            System.out.print("  " +i + "   ");
        }
        System.out.println("    "); //Print a Gap
        for (int row = 0; row < maxPileHeight; row++) {
            for (int i = 0; i < lanes.length; i++) {
                if (row < lanes[i].getSize()) {
                    // Get the card at this row in the pile
                    Card card = lanes[i].getCards()[row];  // Access card by index
                    if (card.getValue()!= Value.TEN && card.isFlipped()) {
                        System.out.print(formatCard(card) + "  ");
                    }else{
                        System.out.print(formatCard(card) + " ");
                    }
                } else {
                    // Print empty space if no card exists in this row
                    System.out.print("      ");  // Empty slot for missing cards
                }
            }
            System.out.println(); // New line after each row
        }

        // Print suit piles
        System.out.println("-------------------------");
        System.out.println("Hearts: " + formatPile(heartSuitPile));
        System.out.println("Diamonds: " + formatPile(diamondSuitPile));
        System.out.println("Clubs: " + formatPile(clubSuitPile));
        System.out.println("Spades: " + formatPile(spadeSuitPile));
        System.out.println("-------------------------");

        // Print draw pile and turned draw pile
        System.out.println("Size of Draw Pile: " + drawPile.getSize());
        if (turnedDrawPile.getSize() != 0) {
            System.out.println("Top of Turned Draw Pile: " + formatCard(turnedDrawPile.getLastCard()));
        } else {
            System.out.println("Top of Turned Draw Pile: ");
        }

        System.out.println("Current Score: " + score);
    }

    // Method to format a single card with colors
    public String formatCard(Card card) {
        String color = card.isRed()? "\033[31m": "\033[30m";
        if (card.isFlipped()) {
            return color +"[" + card.getValueName() + card.getSuitSymbol() + "]" + "\033[0m"; // "\033[0m" resets color
        } else {
            return "[###]"; // Unflipped card display
        }
    }

    // Method to get the maximum pile height across all lanes
    public int getMaxPileHeight() {
        int maxHeight = 0;
        for (Pile lane : lanes) {
            if (lane.getSize() > maxHeight) {
                maxHeight = lane.getSize();
            }
        }
        return maxHeight;
    }

    // Method to format the entire pile (for suit piles)
    public String formatPile(Pile pile) {
        if (pile.getSize() > 0) {
            return formatCard(pile.getLastCard());
        } else {
            return "[Empty]";
        }
    }


    public void getMove(){
        //prompt and read input from user
        Scanner in = new Scanner(System.in);
        System.out.println("Please Make a Move");
        String input = in.nextLine();

        //If user quits=> exit
        if (Objects.equals(input, "q") || Objects.equals(input, "Q")){
            //exit
            System.out.println("Thanks for playing!");
            System.exit(0);
        }

        //If user draws a new card
        if(Objects.equals(input, "d") || Objects.equals(input, "D")){
            drawCard();
            return;
        }

        //Create move object to store move
        Move move = new Move(input);
        if (move.isValid()){
            //Move from "from" to "to"
            move(move.getFrom(), move.getTo());
        }
        else{
            System.out.println("\u001B[1m" + "\033[31m"+ "Error: Invalid Move" + "\033[0m");
        }
    }

    private void drawCard(){
        if (drawPile.getSize()!=0) {
            //Flip card and move to turnedDrawPile
            drawPile.moveTopCardTo(turnedDrawPile, true);
        }
        else if (turnedDrawPile.getSize()>0){
            //Reset draw pile (un-flip all cards)
            while(turnedDrawPile.getSize()!=0) {
                Card lastCard = (turnedDrawPile.getLastCard());
                lastCard.setFlipped(false);
                drawPile.addCard(lastCard);
                turnedDrawPile.removeLastCard();
            }
            drawPile.moveTopCardTo(turnedDrawPile, true);
        }
        else{
            System.out.println("\u001B[1m" + "\033[31m"+ "Error: Draw Pile Empty" + "\033[0m");
        }
    }

    private boolean isLaneMove(String m){
        //check is move involves a lane
        return m.matches("[1234567]");
    }

    private boolean isSuitMove(String m){
        //check if move involves a suit pile
        return m.matches("[HSCDhscd]");
    }

    public void move(String from, String to){
        //Logic if card is moving from turned draw pile:
        if (Objects.equals(from, "d") || Objects.equals(from, "D")){
            if(isLaneMove(to)){//check if move is to a lane
                //Moving from draw pile to lane
                int laneNumber = Integer.parseInt(to);
                Pile lane = lanes[laneNumber-1];
                //Move top card from turned draw pile to lane
                if (turnedDrawPile.getSize()>0) {
                    //If the draw pile is not empty
                    Card card = turnedDrawPile.getLastCard();
                    if (lane.getSize() > 0) {
                        //If the lane contains cards, ensure that the move is legal
                        Card lastLaneCard = lane.getLastCard();
                        if (card.isRed() != lastLaneCard.isRed() && card.getValue().getIntValue() == lastLaneCard.getValue().getIntValue() - 1) {
                            lane.addCard(card);
                            turnedDrawPile.removeLastCard();
                        }else{
                            System.out.println("\u001B[1m" + "\033[31m"+ "Error: Move not Legal" + "\033[0m");
                        }
                    } else if (card.getValue().getIntValue() == 13) {
                        //If the lane does not contain cards, make sure the draw card is a King
                        lane.addCard(card);
                        turnedDrawPile.removeLastCard();
                    } else {
                        System.out.println("\u001B[1m" + "\033[31m"+ "Error: Can only move King to Empty Pile" + "\033[0m");
                    }
                }
                else{
                    System.out.println("\u001B[1m" + "\033[31m"+ "Error: No Draw Pile Card" + "\033[0m");
                }
            } else if(isSuitMove(to)) {
                //Check if move is to a suit pile
                drawPileToSuitMove(to);
            }
        } else if (isLaneMove(from)){
            //Card moving from lane to somewhere
            int fromLaneNumber = Integer.parseInt(from);

            if (isLaneMove(to)){
                if(!from.equals(to)) {
                    //Moving from one lane to another
                    int toLaneNumber = Integer.parseInt(to);
                    laneToLaneMove(toLaneNumber, fromLaneNumber);

                } else{
                    System.out.println("\u001B[1m" + "\033[31m"+ "Error: Same Pile Entered Twice" + "\033[0m");
                }
            }
            else if (isSuitMove(to)) {
                laneToSuitMove(to, fromLaneNumber);
            }
            else{
                System.out.println("\u001B[1m" + "\033[31m"+ "Error: Cannot Move Card From Lane" + "\033[0m");
            }
        }
        else{
            System.out.println("\u001B[1m" + "\033[31m"+ "Error: Move Type Not Allowed" + "\033[0m");
        }
    }

    public void drawPileToSuitMove(String to){
        //Check if move is to a suit pile
        Suit suit = null;
        switch (to) {
            case "H", "h" -> suit = Suit.HEARTS;
            case "D", "d" -> suit = Suit.DIAMONDS;
            case "C", "c" -> suit = Suit.CLUBS;
            case "S", "s" -> suit = Suit.SPADES;
        }

        Pile suitPile = suits[suit.getIndex()];
        if (turnedDrawPile.getSize()>0) {
            //if turned draw pile is not empty, store last card
            Card card = turnedDrawPile.getLastCard();
            if (suitPile.getSize() > 0) {
                //if suit pile is not empty
                Card topOfSuitPile = suitPile.getLastCard();
                if (card.getSuit() == suit && card.getValue().getIntValue() == topOfSuitPile.getValue().getIntValue() + 1) {
                    //check if move is legal, then move new card to suit pile if so
                    suitPile.addCard(card);
                    turnedDrawPile.removeLastCard();
                    score +=10;
                }
                else{
                    System.out.println("\u001B[1m" + "\033[31m"+ "Error: Move Not Legal" + "\033[0m");
                }

            } else if (card.getSuit() == suit && card.getValue().getIntValue() == 1) {
                //if the suit pile is empty, ensure card is an Ace
                suitPile.addCard(card);
                turnedDrawPile.removeLastCard();
            } else {
                System.out.println("\u001B[1m" + "\033[31m"+ "Error: Move Not Legal" + "\033[0m");
            }
        } else{
            System.out.println("\u001B[1m" + "\033[31m"+ "Error: Pile is Empty" + "\033[0m");
        }
    }
    public void laneToLaneMove(int toLaneNumber, int fromLaneNumber){
        //Logic to handle lane to lane moves
        Pile fromLane = lanes[fromLaneNumber - 1];
        Pile toLane = lanes[toLaneNumber - 1];

        //get list of cards in from lane and length
        Card[] fromLaneCards = fromLane.getCards();
        int fromLaneLength = fromLaneCards.length;
        //movedCards is false unless cards have been moved for that move.
        boolean movedCards = false;

        if (fromLaneLength>0) {
            for (int i = fromLaneLength - 1; i >= 0; i--) {
                //for all flipped cards in "from" pile, check if they can be moved to "to" pile
                if (fromLaneCards[i].isFlipped() && (!movedCards)) {
                    if (toLane.getSize() > 0) {
                        Card topOfToLane = toLane.getLastCard();
                        if ((fromLaneCards[i].isRed() != topOfToLane.isRed() && fromLaneCards[i].getValue().getIntValue() == topOfToLane.getValue().getIntValue() - 1)) {
                            //If move can be made then shift this card and all cards below over.
                            for (int j = i; j <= fromLaneLength - 1; j++) {
                                toLane.addCard(fromLaneCards[j]);
                            }
                            //Now remove from "from" Pile
                            for (int k = 0; k <= fromLaneLength - 1 - i; k++) {
                                fromLane.removeLastCard();
                            }
                            if (fromLane.getSize() > 0) {
                                //flip over card if from lane is not now empty
                                Card newFromLaneLastCard = fromLane.getLastCard();
                                newFromLaneLastCard.setFlipped(true);
                            }
                            movedCards = true; //Keep track of if cards have been moved
                        }
                    } else if (fromLaneCards[i].getValue().getIntValue() == 13) {
                        //Similar logic to handle king moves
                        for (int j = i; j <= fromLaneLength - 1; j++) {
                            toLane.addCard(fromLaneCards[j]);
                        }
                        //Now remove from "from" Pile
                        for (int k = 0; k <= fromLaneLength - 1 - i; k++) {
                            fromLane.removeLastCard();
                        }
                        if (fromLane.getSize() > 0) {
                            Card newFromLaneLastCard = fromLane.getLastCard();
                            newFromLaneLastCard.setFlipped(true);
                        }
                        movedCards = true;
                    }
                }
            }
            if (!movedCards){
                System.out.println("\u001B[1m" + "\033[31m"+ "Error: Move not Legal" + "\033[0m");
            }
            else{
                score +=5;
            }
        } else{
            System.out.println("\u001B[1m" + "\033[31m"+ "Error: Pile is Empty" + "\033[0m");
        }
    }

    public void laneToSuitMove(String to, int fromLaneNumber){
        //Moving from lane to suit pile
        Suit suit = Suit.HEARTS; //Randomly assign to hearts, will be overwritten, but this will avoid nullPointError warnings
        switch (to) {
            case "H", "h" -> suit = Suit.HEARTS;
            case "D", "d" -> suit = Suit.DIAMONDS;
            case "C", "c" -> suit = Suit.CLUBS;
            case "S", "s" -> suit = Suit.SPADES;
        }
        //Moving to Suit pile
        Pile suitPile = suits[suit.getIndex()];
        Pile lane = lanes[fromLaneNumber -1];

        //Check if the lane is not empty
        if (lane.getSize() >0) {
            Card card = lane.getLastCard();
            if (suitPile.getSize() > 0) {
                //Check if the suit pile is not empty
                Card topOfSuitPile = suitPile.getLastCard();
                if (card.getSuit() == suit && card.getValue().getIntValue() == topOfSuitPile.getValue().getIntValue() + 1) {
                    //if move can be made, add to suit pile and remove from lane
                    suitPile.addCard(card);
                    lane.removeLastCard();
                    if (lane.getSize() > 0) {
                        //flip over new top card
                        Card newLast = lane.getLastCard();
                        newLast.setFlipped(true);
                        score += 20;
                    }
                } else{
                    System.out.println("\u001B[1m" + "\033[31m"+ "Error: Move not Legal" + "\033[0m");
                }

            } else if (card.getSuit() == suit && card.getValue().getIntValue() == 1) {
                //If suit pile is empty and the card is an ace, move card to suit pile
                suitPile.addCard(card);
                lane.removeLastCard();
                if (lane.getSize() > 0) {
                    //flip over new top card
                    Card newLast = lane.getLastCard();
                    newLast.setFlipped(true);
                    score +=10;
                }
            } else {
                System.out.println("\u001B[1m" + "\033[31m"+ "Error: Move not Legal" + "\033[0m");
            }
        }
        else{
            System.out.println("\u001B[1m" + "\033[31m"+ "Error: Lane is Empty" + "\033[0m");
        }
    }
}
