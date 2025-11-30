package src.util;

public enum Direction {
    HORIZONTAL(0, 1),
    VERTICAL(1, 0),
    POSITIVE_DIAGONAL(1, 1),
    NEGATIVE_DIAGONAL(1, -1);

    public final int x, y;
    Direction(int x, int y) {
        this.x = x; 
        this.y = y;
    }
}