package src;

public record Factor(
    int row,
    int column,
    int value
) {
    public String toString() {
        return String.format("row: %d | column: %d | value: %d", row + 1, column + 1, value);
    }
};
