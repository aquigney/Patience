public class Card {

    private final Suit suit;
    private final Value value;
    private boolean flipped;

    public Card(Suit suit, Value value, boolean flipped) {
        this.suit = suit;
        this.value = value;
        this.flipped = flipped;
    }

    public Suit getSuit() {
        //return the suit of the card
        return suit;
    }

    public Value getValue() {
        //return the value of the card
        return value;
    }

    public String getValueName() {
        //return the value name as a string
        return value.getName();
    }

    @Override
    public String toString() {
        //Convert the Card to a string representation
        if (flipped) {
            return value.getName() + suit.getShortName();
        } else {
            return "X";  // Hidden face of card
        }
    }

    public boolean isFlipped() {
        //returns true if the card is flipped up
        return flipped;
    }

    public void setFlipped(boolean flipped) {
        //set the card flipped property (true is flipped up)
        this.flipped = flipped;
    }

    public boolean isRed() {
        //returns true if card is red
        return suit.isRed();
    }

    public String getSuitSymbol() {
        //Return the symbol of the suit
        return suit.getSymbol();
    }
}
