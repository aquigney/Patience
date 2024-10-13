public class Pile {
    protected final Card[] cards;
    private int pileSize;

    public Pile() {
        //list of cards of max length 52
        cards = new Card[52];
        pileSize = 0;
    }

    public void addCard(Card card) {
        //add a card to the pile
        cards[pileSize] = card;
        pileSize++;
    }

    public void removeLastCard() {
        //move last card from the pile if possible
        if (pileSize > 0) {
            pileSize--;
        }
        else{
            System.out.println("Empty Pile");
        }
    }

    public String toString(){
        //convert pile to string of cards
        String returnString = "";
        for (int i = 0; i < pileSize; i++) {
            returnString += cards[i].toString() + " ";
        }
        return returnString;
    }

    public Card getLastCard() {
        //return last card in pile
        return cards[pileSize-1];
    }

    public int getSize(){
        //return size of pile
        return pileSize;
    }

    public Card[] getCards(){
        //get list of cards in the pile
        Card[] temp = new Card[pileSize];
        for (int i=0; i< pileSize; i++){
            temp[i] = cards[i];
        }
        return temp;
    }

    public void moveTopCardTo(Pile targetPile, boolean flipped) {
        //Move top (or last) card of pile to another target pile
        Card topCard = this.getLastCard();
        topCard.setFlipped(flipped);
        targetPile.addCard(topCard);
        this.removeLastCard();
    }
}
