package model;

/**
 * Represents a position in row and column.
 */
public class Position {

    private int row;
    private int col;

    /**
     * Creates a {@code Position} object.
     * @param row the row coordinate of the position.
     * @param col the col coordinate of the position.
     */
    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * @return the row coordinate of the position.
     */
    public int getRow() {
        return row;
    }

    /**
     * Set the row coordinate of the position.
     * @param row the row coordinate of the position
     */
    public void setRow(int row) {
        this.row += row;
    }

    /**
     * @return the col coordinate of the position.
     */
    public int getCol() {
        return col;
    }

    /**
     * Set the col coordinate of the position.
     * @param col the col coordinate of the position
     */
    public void setCol(int col) {
        this.col += col;
    }
}
