import java.lang.Math;

public class Deck extends Pile {
    public Deck() {
        // Create a new deck with 52 cards
        super(); // Call the constructor of Pile to initialize the pile
        for (Suit suit : Suit.values()) {
            for (Value value : Value.values()) {
                // Add each card to the pile (which is now the deck)
                this.addCard(new Card(suit, value, true));
            }
        }
        shuffle(); // Shuffle the deck after it's created
    }

    public void shuffle() {
        // Shuffle the deck directly on the cards array in Pile
        int size = this.getSize(); // Get the size of the pile (52 for a full deck)
        // Since we are inheriting the cards array, we need to shuffle it directly.
        // The 'cards' array is protected in the Pile class, so you can access it directly.
        for (int i = 0; i < size; i++) {
            int randomCardPosition = (int) (size * Math.random());
            // Swap cards at position i and randomCardPosition
            Card temp = super.cards[randomCardPosition];
            super.cards[randomCardPosition] = super.cards[i];
            super.cards[i] = temp;
        }
    }

    public Card drawTopCard() {
        // Draw the top card (last card in the pile)
        Card topCard = this.getLastCard();
        this.removeLastCard(); // Remove the top card from the pile
        return topCard;
    }
}
