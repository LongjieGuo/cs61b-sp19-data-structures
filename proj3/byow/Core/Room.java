package byow.Core;

public class Room {
    private Position topLeft;
    private Position bottomRight;

    public Room(Position tL, Position bR) {
        topLeft = tL;
        bottomRight = bR;
    }

    public Position getTopLeft() {
        return topLeft;
    }

    public Position getBottomRight() {
        return bottomRight;
    }
}
