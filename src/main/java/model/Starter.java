package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;

/**
 * A class for start the game from terminal.
 */
public class Starter {

    /**
     * A reader class for read input from console.
     */
    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    /**
     * Represents the first {@code User}.
     */
    private User userOne;
    /**
     * Represents the second {@code User}.
     */
    private User userTwo;
    /**
     * Represents the {@code Board} object where the {@code Disk} are stored.
     */
    private final Board board = new Board();

    /**
     * Initialize the users name.
     * @throws IOException if there is problem with the reader
     */
    private void initUsers() throws IOException {
        System.out.println("Adja meg az első játékos nevét:");
        String tempForUserName = this.reader.readLine();
        this.userOne = new User(1, tempForUserName);
        System.out.println("Adja meg a második játékos nevét:");
        tempForUserName = this.reader.readLine();
        this.userTwo = new User(2, tempForUserName);
    }

    /**
     * Function to get the chosen move for the {@code User} from the terminal.
     * @param user represents the user which is try to move
     * @throws IOException if there is problem with the reader
     */
    private void getUserMove(User user) throws IOException {
        String move = "";
        System.out.println(this.board.toString());
        System.out.println(user.userName() + " következik:");
        System.out.println("Adja meg a mozgatni kívánt korong ID-ját:");
        String disk1 = reader.readLine();
        try {
            Integer.parseInt(disk1);
        } catch (IllegalArgumentException e) {
            System.err.println("Nem megengedett korong");
        }
        System.out.println("Adja meg a mozgatni kívánt második korong ID-ját:");
        String disk2 = reader.readLine();
        try {
            Integer.parseInt(disk2);
        } catch (IllegalArgumentException e) {
            System.err.println("Nem megengedett korong");
        }
        int parsedDisk1 = Integer.parseInt(disk1);
        int parsedDisk2 = Integer.parseInt(disk2);
        System.out.println("""
                   Adja meg, hogy merre szeretne lépni:
                   1 -> Fel
                   2 -> Le
                   3 -> Balra
                   4 -> Jobbra
                   5 -> Fel jobbra
                   6 -> Fel balra
                   7 -> Le jobbra
                   8 -> Le balra
                    """);
        move = reader.readLine();
        int parsedMove = Integer.parseInt(move);
        Direction direction = switch (parsedMove) {
            case 1 -> Direction.UP;
            case 2 -> Direction.DOWN;
            case 3 -> Direction.LEFT;
            case 4 -> Direction.RIGHT;
            case 5 -> Direction.UPRIGHT;
            case 6 -> Direction.UPLEFT;
            case 7 -> Direction.DOWNRIGHT;
            case 8 -> Direction.DOWNLEFT;
            default -> null;
        };
        assert direction != null;
        if (this.board.diskCanBeMoved(parsedDisk1, Optional.of(user), direction) &&
                this.board.diskCanBeMoved(parsedDisk2, Optional.of(user), direction)) {
            board.changeDiskPosition(parsedDisk1, direction);
            board.changeDiskPosition(parsedDisk2, direction);
        } else {
            System.err.println("""
                        [HIBA]: Nem megengedett lépés!
                        Olyan mezőre nem lehet lépni, ami már foglalt, a korong a mezőn kívülre esne,
                        vagy a mozgatni kívánt korong nem a játékoshoz tartozik!
                        """);
        }
    }

    /**
     * Method to start the game from terminal.
     * @throws IOException if there is problem with the reader
     */
    public void startGame() throws IOException {
        this.initUsers();
        this.board.initBoard();
        while (true) {
            boolean playerOneTurn = true;
            if (playerOneTurn) {
                getUserMove(this.userOne);
            }
            if (board.isGoalState()[0]) {
                System.out.println("A játék véget ért!");
                if (board.isGoalState()[1]) {
                    System.out.println(userOne.userName() + " nyert.");
                }
                else {
                    System.out.println(userTwo.userName() + " nyert.");
                }
                break;
            }
            playerOneTurn = false;

            if (!playerOneTurn) {
                getUserMove(this.userTwo);
            }
            if (board.isGoalState()[0]) {
                System.out.println("A játék véget ért!");
                if (board.isGoalState()[1]) {
                    System.out.println(userOne.userName() + " nyert.");
                }
                else {
                    System.out.println(userTwo.userName() + " nyert.");
                }
                break;
            }
            playerOneTurn = true;

        }
    }

    /**
     * Function to start the game on terminal.
     * @param args none
     * @throws IOException if something goes wrong
      */
    public static void main(String[] args) throws IOException {
        Starter starter = new Starter();
        starter.startGame();
    }
}

