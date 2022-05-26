package model;

/**
 * Enum class for represent the direction where the {@code Disk} can be moved.
 */
public enum Direction {

    /**
     * {@code Direction} up.
     */
    UP(-1, 0),
    /**
     * {@code Direction} down.
     */
    DOWN(1, 0),
    /**
     * {@code Direction} left.
     */
    LEFT(0, -1),
    /**
     * {@code Direction} right.
     */
    RIGHT(0, 1),
    /**
     * {@code Direction} up right.
     */
    UPRIGHT(-1, 1),
    /**
     * {@code Direction} up left.
     */
    UPLEFT(-1, -1),
    /**
     * {@code Direction} down right.
     */
    DOWNRIGHT(1, 1),
    /**
     * {@code Direction} down left.
     */
    DOWNLEFT(1, -1);

    private final int rowChange;
    private final int colChange;

    Direction(int rowChange, int colChange) {
        this.rowChange = rowChange;
        this.colChange = colChange;
    }

    /**
     * @return the change on the row coordinates.
     */
    public int getRowChange() {
        return rowChange;
    }

    /**
     * @return the change on the column coordinates.
     */
    public int getColChange() {
        return colChange;
    }

}
