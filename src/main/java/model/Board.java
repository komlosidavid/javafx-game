package model;

import java.util.*;

/**
 * Represent a {@code Board} object,
 * where the game will be held.
 */
public class Board {

    /**
     * The maximum number of rows.
     */
    private final int MAX_NUMBER_OF_ROWS = 5;
    /**
     * The maximum number of columns.
     */
    private final int MAX_NUMBER_OF_COLS = 5;
    /**
     * Represents the back end of the board,
     * where the disks will be stored during the game.
     */
    private final Disk[][] board = new Disk[MAX_NUMBER_OF_ROWS][MAX_NUMBER_OF_COLS];

    /**
     * @return the board where the disks are stored.
     */
    public Disk[][] getBoard() {
        return board;
    }

    /**
     * Initialize the empty {@code Board} for the first run.
     */
    public void initBoard() {
        for (var i = 0; i < MAX_NUMBER_OF_ROWS; i++) {
            for (var j = 0; j < MAX_NUMBER_OF_COLS; j++) {
                if (i == 0) {
                    board[i][j] = new Disk((i+1)+(j), 1, new Position(i, j));
                }
                if ((i == 1 && j == 1) ||
                        (i == 1 && j == 3) ||
                        (i == 3 && j == 1) ||
                        (i == 3 && j == 3)) {
                    board[i][j] = new Disk(0, 0, new Position(i, j));
                }
                if (i == 4) {
                    board[i][j] = new Disk((i+1)+(j+1), 2, new Position(i, j));
                }
            }
        }
    }

    /**
     * Function to check whether the last move had an affect
     * to the game result.
     * @return a boolean array
     * The first element of the array represents if the state is a goal state,
     * the second element represents if the first user is the winner. If it is, it should
     * be true.
     */
    public boolean isGoalState() {
        if (calculateGoalState()[0]) {
            return true;
        }
        return false;
    }

    /**
     * Check if the first user is the winner.
     * @return true if the first user won, false if the second user won
     */
    public boolean isFirstUserWon() {
        if (calculateGoalState()[1]) {
            return true;
        }
        return false;
    }

    /**
     * Calculate if it is goal state after a move.
     * @return a boolean array index of 0 represents whether it is goal state
     * Array index of 1 represents wether the first user won or not
     */
    private boolean[] calculateGoalState() {
        boolean goalState = false;
        List<Integer> rowsForBlueDisks = new ArrayList<>();
        List<Integer> rowsForRedDisks = new ArrayList<>();

        Arrays.stream(getBoard())
                .flatMap(Arrays::stream)
                .filter(Objects::nonNull)
                .forEach(child -> {
                    if (child.id() > 5) {
                        rowsForRedDisks.add(child.getPosition().getRow());
                    }
                    else if (child.id() != 0) {
                        rowsForBlueDisks.add(child.getPosition().getRow());
                    }
                });

        boolean firstUserWon = false;

        if (rowsForBlueDisks.stream().allMatch(e -> e == 4)) {
            goalState = true;
            firstUserWon = true;
        }
        else if (rowsForRedDisks.stream().allMatch(e -> e == 0)) {
            goalState = true;
        }

        return new boolean[]{goalState, firstUserWon};
    }

    /**
     * Get the {@code Disk} from the {@code Board}.
     * @param diskId the id of the {@code Disk} for search
     * @return an Optional {@code Disk} object which is selected
     */
    private Optional<Disk> getDiskFromBoard(int diskId) {
        return Arrays.stream(getBoard())
                .flatMap(Arrays::stream)
                .filter(Objects::nonNull)
                .filter(child -> child.id() == diskId)
                .findFirst();
    }

    /**
     * Check whether the selected {@code Disk} object can be moved to the selected {@code Direction}.
     * @param diskId represents the identifier of the {@code Disk} object.
     * @param user represents the {@code User} record. User parameter can be empty.
     * @param direction represents the chosen {@code Direction} where the {@code Disk} want to be moved.
     * @return true, if the {@code Disk} can be moved or false if not.
     */
    public boolean diskCanBeMoved(int diskId, Optional<User> user, Direction direction) {
        var currentDisk = getDiskFromBoard(diskId);
        if (currentDisk.isPresent()) {
            Position currentPositionOfDisk = currentDisk.get().position();
            if (user.isPresent() && user.get().userId() != currentDisk.get().userId()) {
                return false;
            }
            if ((currentPositionOfDisk.getRow() + direction.getRowChange()) < 0 ||
                    (currentPositionOfDisk.getRow() + direction.getRowChange() > 4)) {
                return false;
            }
            if ((currentPositionOfDisk.getCol() + direction.getColChange()) < 0 ||
                    (currentPositionOfDisk.getCol() + direction.getColChange() > 4)) {
                return false;
            }
            var newRowPosition = currentPositionOfDisk.getRow() + direction.getRowChange();
            var newColPosition = currentPositionOfDisk.getCol() + direction.getColChange();

            return getBoard()[newRowPosition][newColPosition] == null;
        }
        return false;
    }

    /**
     * Method to change the selected {@code Disk} object inside the {@code Board}.
     * @param diskId represents the id of the chosen {@code Disk}
     * @param direction represents the direction where the {@code Disk} will be moved
     */
    public void changeDiskPosition(int diskId, Direction direction) {
        var disk = getDiskFromBoard(diskId);
        if (disk.isPresent()) {
            var newPosition = new Position(disk.get().position().getRow() + direction.getRowChange(),
                    disk.get().position().getCol() + direction.getColChange());

            getBoard()[disk.get().getPosition().getRow()][disk.get().getPosition().getCol()] = null;

            disk.get().setPosition(direction);
            getBoard()[newPosition.getRow()][newPosition.getCol()] = disk.get();
        }
    }

    /**
     * Over-riding the toString() function to show board on terminal.
     * @return the formatted string of the board
     */
    @Override
    public String toString() {
        String result = "";
        for (var i = 0; i < MAX_NUMBER_OF_ROWS; i++) {
            for (var j = 0; j < MAX_NUMBER_OF_COLS; j++) {
                if (getBoard()[i][j] != null) {
                    result += "ID: " + getBoard()[i][j].id() +
                            " " +
                            "Row: " + getBoard()[i][j].position().getRow() +
                            " - " +
                            "Col: " + getBoard()[i][j].position().getCol() +
                            " || ";
                }
                else {
                    result += getBoard()[i][j] + "----------------- || ";
                }
            }
            result += "\n";
        }
        return result;
    }

}
