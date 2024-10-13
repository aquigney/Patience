public enum Value {
    ACE("A", 1),
    TWO("2", 2),
    THREE("3", 3),
    FOUR("4", 4),
    FIVE("5", 5),
    SIX("6", 6),
    SEVEN("7", 7),
    EIGHT("8", 8),
    NINE("9", 9),
    TEN("10", 10),
    JACK("J", 11),
    QUEEN("Q", 12),
    KING("K", 13);

    private final String name;
    private final int value;

    Value(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        //return name of value as a string
        return name;
    }

    public int getIntValue() {
        //return value as an integer (A=1, K=13)
        return value;
    }
}