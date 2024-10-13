public enum Suit {
    HEARTS("H", "♥",  true),
    DIAMONDS("D", "♦",true),
    CLUBS("C", "♣", false),
    SPADES("S", "♠", false);

    private final String shortName;
    private final String symbol;
    private final boolean isRed;

    Suit(String shortName, String symbol, boolean isRed) {
        this.shortName = shortName;
        this.symbol = symbol;
        this.isRed = isRed;
    }

    public String getShortName() {
        //return short name (i.e. "C" for clubs)
        return shortName;
    }

    public String getSymbol() {
        //return symbol associated with suit
        return symbol;
    }

    public boolean isRed() {
        //return true if the card is red
        return isRed;
    }

    public int getIndex(){
        //return the associated index of the suit pile of this suit
        return switch (this) {
            case HEARTS -> 0;
            case DIAMONDS -> 1;
            case CLUBS -> 2;
            case SPADES -> 3;
        };
    }
}