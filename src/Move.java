public class Move {
    private final String from;
    private final String to;

    public Move(String input) {
        if (input.length() == 2) {
            from = input.substring(0, 1);
            to = input.substring(1, 2);
        } else {
            from = null;
            to = null;
        }
    }

    public boolean isValid() {
        //Check if move is valid
        return from != null && to != null && isLegalMove(from) && isLegalMove(to);
    }

    private boolean isLegalMove(String m) {
        //return true is move is legal. Other checks need to see is move is possible.
        return m.matches("[1234567PHSCDphscd]");
    }

    public String getFrom() {
        //return the "from" part of the move
        return from;
    }

    public String getTo() {
        //return the "to" part of the move
        return to;
    }
}
