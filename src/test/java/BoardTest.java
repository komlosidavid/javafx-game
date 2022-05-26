import model.Board;
import model.Direction;
import model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class BoardTest {

    private Board board = new Board();

    @BeforeEach
    void setUp() {
        board.initBoard();
    }

    @Test
    void diskCanBeMovedTest() {
        Optional<User> user = Optional.of(new User(1, ""));
        Optional<User> user2 = Optional.of(new User(2, ""));

        Assertions.assertTrue(board.diskCanBeMoved(1, user, Direction.DOWN));
        Assertions.assertFalse(board.diskCanBeMoved(1, user, Direction.UP));
        Assertions.assertFalse(board.diskCanBeMoved(6, user, Direction.UP));
        Assertions.assertFalse(board.diskCanBeMoved(5, user, Direction.RIGHT));
        Assertions.assertFalse(board.diskCanBeMoved(2, user, Direction.RIGHT));

        Assertions.assertTrue(board.diskCanBeMoved(8, user2, Direction.UP));
        Assertions.assertFalse(board.diskCanBeMoved(1, user2, Direction.DOWN));
        Assertions.assertFalse(board.diskCanBeMoved(6, user2, Direction.DOWN));
        Assertions.assertFalse(board.diskCanBeMoved(10, user2, Direction.RIGHT));
        Assertions.assertFalse(board.diskCanBeMoved(7, user2, Direction.RIGHT));

        Assertions.assertTrue(board.diskCanBeMoved(3, Optional.empty(), Direction.DOWN));
    }

    @Test
    void isGoalStateTest() {
        boolean firstUserWon = true;
        Assertions.assertFalse(board.isGoalState()[0]);
        Assertions.assertNotEquals(board.isGoalState()[1], firstUserWon);
    }

    @Test
    void changeDiskPositionTest() {
        Board newBoard = new Board();
        newBoard.initBoard();
        board.changeDiskPosition(1, Direction.DOWN);
        Assertions.assertNotEquals(board, newBoard);
        Assertions.assertNotEquals(board.getBoard(), newBoard.getBoard());
    }

}
