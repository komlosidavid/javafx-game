package model;

/**
 * Represent a(n) {@code Disk} record.
 * @param id the id of the {@code Disk} for identification
 * @param userId the id of the {@code User} who owns the disk
 * @param position the {@code Position} of the disk
 */
public record Disk(int id, int userId, Position position) {

    /**
     * Get the actual position of the {@code Disk}.
     * @return a {@code Position} object
     */
    public Position getPosition() {
        return this.position;
    }

    /**
     * Set the {@code Position} of the disk.
     * @param direction the new {@code Direction} of the disk
     */
    public void setPosition(Direction direction) {
        if (this.position.getRow() + direction.getRowChange() < 0 ||
                this.position.getRow() + direction.getRowChange() > 4) {
            throw new ArrayIndexOutOfBoundsException();
        }
        if (this.position.getCol() + direction.getColChange() < 0 ||
                this.position.getCol() + direction.getColChange() > 4) {
            throw new ArrayIndexOutOfBoundsException();
        }
        this.position.setRow(direction.getRowChange());
        this.position.setCol(direction.getColChange());
    }

}
