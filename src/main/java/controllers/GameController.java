package controllers;

import model.*;
import javafx.beans.property.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;


import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import org.tinylog.Logger;
import table.Winner;
import table.WinnerRepository;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static java.lang.String.valueOf;

/**
 * Class for handle game backend on game scene.
 */
public class GameController {

    /**
     * Label to display username on scene.
     */
    @FXML
    private Label firstUserLabel;
    /**
     * Label to display username on scene.
     */
    @FXML
    private Label secondUserLabel;
    /**
     * TextField to display moves on scene.
     */
    @FXML
    private TextField numberOfMovesFieldForFirstUser;
    /**
     * TextField to display moves on scene.
     */
    @FXML
    private TextField numberOfMovesFieldForSecondUser;
    /**
     * Grid pane where the game is displayed.
     */
    @FXML
    private GridPane grid;
    /**
     * Property for count number of moves.
     */
    private final IntegerProperty numberOfMovesForFirstUser = new SimpleIntegerProperty(0);
    /**
     * Property for count number of moves.
     */
    private final IntegerProperty numberOfMovesForSecondUser = new SimpleIntegerProperty(0);
    /**
     * {@code Board} object to store game data.
     */
    private final Board board = new Board();
    /**
     * Boolean property to check whether the first user is on turn.
     */
    private boolean isFirstUserTurn = true;
    /**
     * Array to store selected disks.
     */
    private final List<Node> selectedDisks = new ArrayList<>();

    /**
     * Initialize game scene for the first run.
     */
    @FXML
    private void initialize() {
        board.initBoard();
        makeBindingsToCalculateNumberOfMoves();
        initGrid();
        checkWhichUserMoves();
    }

    /**
     * Initialize the users to display their names.
     * @param firstUser represents the first user
     * @param secondUser represents the second user
     */
    public void initUsers(User firstUser, User secondUser) {
        firstUserLabel.setText(firstUser.userName());
        firstUserLabel.getStyleClass().add("active-player");
        secondUserLabel.setText(secondUser.userName());
    }

    /**
     * Create binding to display number of moves on the screen.
     */
    private void makeBindingsToCalculateNumberOfMoves() {
        numberOfMovesFieldForFirstUser.textProperty().bind(numberOfMovesForFirstUser.asString());
        numberOfMovesFieldForSecondUser.textProperty().bind(numberOfMovesForSecondUser.asString());
    }

    /**
     * Initialize Grid pane for the first run.
     */
    private void initGrid() {
        for (var row = 0; row < grid.getRowCount(); row++) {
            for (var col = 0; col < grid.getColumnCount(); col++) {
                var pane = new StackPane();
                var disk = new Circle(30);
                if (row == 0) {
                    disk.setId(valueOf(row + col + 1));
                    disk.setFill(Color.BLUE);
                    grid.add(disk, col, row);
                }
                else if (row == 1 && col == 1 ||
                    row == 1 && col == 3 ||
                    row == 3 && col == 1 ||
                    row == 3 && col == 3) {
                    pane.getStyleClass().add("blackpane");
                    grid.add(pane, col, row);
                }
                else if (row == 4) {
                    disk.setId(valueOf(row+1 + col+1));
                    disk.setFill(Color.RED);
                    grid.add(disk, col, row);
                }
                disk.setOnMouseClicked(this::handleMouseClick);
                GridPane.setHalignment(disk, HPos.CENTER);
            }
        }
    }

    /**
     * Handle event when the mouse was clicked on a {@code Circle} object.
     * @param mouseEvent identifies the mouse event
     */
    @FXML
    private void handleMouseClick(MouseEvent mouseEvent) {
        var gridElement = (Node) mouseEvent.getSource();
        var row = GridPane.getRowIndex(gridElement);
        var col = GridPane.getColumnIndex(gridElement);
        Circle currentCircle = (Circle) gridElement;
        if (currentCircle.getFill() == Color.GREEN) {
            makeCircleUnselected(currentCircle);
        }
        else {
            if (selectedDisks.size() == 2) {
                Logger.warn("You have two disk selected already. Try to make a move.");
                showAlert("Figyelem!",
                        "Már van kiválasztva két korongod. Próbálj meg velük lépni."
                );
            }
            else {
                makeCircleSelected(gridElement);
                Logger.debug("Grid element clicked on ({},{}) with id of {}.",
                        row, col, gridElement.getId());
            }
        }
    }

    /**
     * Handle move to {@code Direction} DOWN.
     * @throws IOException if something goes wrong
     */
    @FXML
    private void moveDown() throws IOException {
        makeMovement(Direction.DOWN);
    }

    /**
     * Handle move to {@code Direction} UP.
     * @throws IOException if something goes wrong
     */
    @FXML
    private void moveUp() throws IOException {
        makeMovement(Direction.UP);
    }

    /**
     * Handle move to {@code Direction} LEFT.
     * @throws IOException if something goes wrong
     */
    @FXML
    private void moveLeft() throws IOException {
        makeMovement(Direction.LEFT);
    }

    /**
     * Handle move to {@code Direction} RIGHT.
     * @throws IOException if something goes wrong
     */
    @FXML
    private void moveRight() throws IOException {
        makeMovement(Direction.RIGHT);
    }

    /**
     * Handle move to {@code Direction} DOWNLEFT.
     * @throws IOException if something goes wrong
     */
    @FXML
    private void moveDownLeft() throws IOException {
        makeMovement(Direction.DOWNLEFT);
    }

    /**
     * Handle move to {@code Direction} UPLEFT.
     * @throws IOException if something goes wrong
     */
    @FXML
    private void moveUpLeft() throws IOException {
        makeMovement(Direction.UPLEFT);
    }

    /**
     * Handle move to {@code Direction} DOWNRIGHT.
     * @throws IOException if something goes wrong
     */
    @FXML
    private void moveDownRight() throws IOException {
        makeMovement(Direction.DOWNRIGHT);
    }

    /**
     * Handle move to {@code Direction} UPRIGHT.
     * @throws IOException if something goes wrong
     */
    @FXML
    private void moveUpRight() throws IOException {
        makeMovement(Direction.UPRIGHT);
    }

    /**
     * Create movement to the selected {@code Direction}.
     * @param direction the selected {@code Direction} enum
     * @throws IOException if something goes wrong
     */
    private void makeMovement(Direction direction) throws IOException {
        if (selectedDisks.size() == 2) {
            int diskId1 = Integer.parseInt(selectedDisks.get(0).getId());
            int diskId2 =  Integer.parseInt(selectedDisks.get(1).getId());
            if (board.diskCanBeMoved(diskId1, Optional.empty(), direction) &&
                board.diskCanBeMoved(diskId2, Optional.empty(), direction)) {

                selectedDisks.set(0, changeCirclePositionOnGrid(selectedDisks.get(0), direction));
                selectedDisks.set(1, changeCirclePositionOnGrid(selectedDisks.get(1), direction));

                makeCirclesUnselected();

                board.changeDiskPosition(diskId1, direction);
                board.changeDiskPosition(diskId2, direction);

                updateMovesCounter();
                handleGameOver(board);

                Logger.debug("Moved to direction {}", direction);

            }
            else {
                Logger.warn("One of the selected circle cannot be moved to this direction." +
                        "Try to reselect one of them.");
                showAlert("Rossz lépés!",
                        "Az egyik kiválasztott korongod nem mozoghat ebbe az irányba.\n" +
                                "Próbálj meg egy másikat választani."
                );
            }
        }
        else {
            Logger.warn("You have to select two circles from the board.");
            showAlert("Hiba!",
                    "Két korongot kell kiválasztanod."
            );
        }
    }

    /**
     * Update count of moves which is shown on scene.
     */
    private void updateMovesCounter() {
        if (!isFirstUserTurn) {
            numberOfMovesForFirstUser.set(numberOfMovesForFirstUser.get() + 1);
        }
        else {
            numberOfMovesForSecondUser.set(numberOfMovesForSecondUser.get() + 1);
        }
    }

    /**
     * Change the selected {@code Circle} object on the Grid pane.
     * @param element the selected {@code Circle} object
     * @param direction the selected {@code Direction} enum
     * @return a {@code Node} object as the new {@code Circle}
     */
    private Node changeCirclePositionOnGrid(Node element, Direction direction) {
        grid.getChildren().remove(element);
        var newCircle = new Circle(30);
        newCircle.setId(element.getId());
        newCircle.setFill(Color.GREEN);
        grid.add(newCircle,
                getColOfElement(element) + direction.getColChange(),
                getRowOfElement(element) + direction.getRowChange());
        GridPane.setHalignment(newCircle, HPos.CENTER);
        return newCircle;
    }

    /**
     * Make the {@code Circle} object to be selected.
     * If it is selected, the {@code Circle} object {@code Color} changes to green.
     * @param element the selected {@code Circle} object
     */
    private void makeCircleSelected(Node element) {
        grid.getChildren().remove(element);
        var newCircle = new Circle(30);
        newCircle.setId(element.getId());
        newCircle.setFill(Color.GREEN);
        grid.add(newCircle, getColOfElement(element), getRowOfElement(element));
        GridPane.setHalignment(newCircle, HPos.CENTER);
        newCircle.setOnMouseClicked(this::handleMouseClick);
        selectedDisks.add(newCircle);
    }

    /**
     * Make the {@code Circle} object to be unselected.
     * If it is selected, the {@code Circle} object {@code Color} changes back to original.
     * @param element the selected {@code Circle} object
     */
    private void makeCircleUnselected(Node element) {
        var row = GridPane.getRowIndex(element);
        var col = GridPane.getColumnIndex(element);
        grid.getChildren().remove(element);
        var newCircle = new Circle(30);
        newCircle.setId(element.getId());
        if (Integer.parseInt(newCircle.getId()) <= 5) {
            newCircle.setFill(Color.BLUE);
        }
        else {
            newCircle.setFill(Color.RED);
        }
        newCircle.setOnMouseClicked(this::handleMouseClick);
        grid.add(newCircle, col, row);
        GridPane.setHalignment(newCircle, HPos.CENTER);

        if (getCircleObjectFromArray(element).isPresent()) {
            selectedDisks.remove(getCircleObjectFromArray(element).get());
        }
    }

    /**
     * Handle when one of the players have a winner position.
     * @param board Represents the {@code Board} where the game is held
     * @throws IOException if something goes wrong
     */
    private void handleGameOver(Board board) throws IOException {
        if (board.isGoalState()) {
            Winner winner = new Winner();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            LocalDateTime now = LocalDateTime.now();
            if (board.isFirstUserWon()) {
                winner.setWinnerName(firstUserLabel.getText());
                winner.setWinnerColor("Blue");
                winner.setWinnerMoves(numberOfMovesForFirstUser.get());
                winner.setDate(dtf.format(now));
            }
            else {
                winner.setWinnerName(secondUserLabel.getText());
                winner.setWinnerColor("Red");
                winner.setWinnerMoves(numberOfMovesForSecondUser.get());
                winner.setDate(dtf.format(now));
            }
            setWinner(winner);
            var alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Game Over");
            alert.setContentText(board.isFirstUserWon() ?
                    firstUserLabel.getText() + " nyert." :
                    secondUserLabel.getText() + " nyert.");
            alert.showAndWait()
                    .filter(res -> res == ButtonType.OK)
                    .ifPresent(res -> {
                        try {
                            openGameHistory();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
        }
    }

    /**
     * Set up winner data, and create history.json file.
     * @param winner the {@code Winner} of the game
     * @throws IOException if there is a problem with file writing
     */
    private void setWinner(Winner winner) throws IOException {
        var repository = new WinnerRepository();
        File file = new File("history.json");
        if (file.exists()) {
            repository.loadFromFile(file);
        }
        var finalWinner = Winner.builder()
                .winnerName(winner.getWinnerName())
                .winnerColor(winner.getWinnerColor())
                .winnerMoves(winner.getWinnerMoves())
                .date(winner.getDate())
                .build();
        repository.add(finalWinner);
        repository.saveToFile(file);
    }

    /**
     * Switch to gameHistory scene.
     * @throws IOException if something goes wrong
     */
    private void openGameHistory() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/gameHistory.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) numberOfMovesFieldForFirstUser.getScene().getWindow();
        stage.setTitle("Game History");
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * Get the row of the element on the Grid pane.
     * @param element the selected element.
     * @return an Integer as rowIndex
     */
    private Integer getRowOfElement(Node element) {
        return GridPane.getRowIndex(element);
    }

    /**
     * Get the column of the element on the Grid pane.
     * @param element the selected element.
     * @return an Integer as columnIndex
     */
    private Integer getColOfElement(Node element) {
        return GridPane.getColumnIndex(element);
    }

    /**
     * Function to get the selected {@code Circle} object from the Grid pane.
     * @param element the selected element
     * @return an Optional of {@code Node}
     */
    private Optional<Node> getCircleObjectFromArray(Node element) {
       return selectedDisks.stream()
               .filter(Objects::nonNull)
               .filter(child -> Objects.equals(child.getId(), element.getId()))
               .findFirst();
    }

    /**
     * Make all the selected circles unselected.
     * The circles color will be their original.
     */
    private void makeCirclesUnselected() {
        var circle1 = selectedDisks.get(0);
        var circle2 = selectedDisks.get(1);

        makeCircleUnselected(circle1);
        makeCircleUnselected(circle2);

        changeTurn();
    }

    /**
     * Function to change to the other user turn.
     */
    private void changeTurn() {
        selectedDisks.clear();
        isFirstUserTurn = !isFirstUserTurn;
        checkWhichUserMoves();
        if (isFirstUserTurn) {
            firstUserLabel.getStyleClass().add("active-player");
            secondUserLabel.getStyleClass().remove("active-player");
        }
        else {
            firstUserLabel.getStyleClass().remove("active-player");
            secondUserLabel.getStyleClass().add("active-player");
        }

    }

    /**
     * Show an alert box when the user has a wrong move, or something to be warned of.
     *
     * @param alertHeaderTitle the header title of the alert box
     * @param alertMessage     the message of the alert box
     */
    private void showAlert(String alertHeaderTitle, String alertMessage) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(alertHeaderTitle);
        alert.setContentText(alertMessage);
        alert.showAndWait();
    }

    /**
     * Function to check which user should move.
     * Make the other user circles disabled during the time
     * while one user is moving.
     */
    private void checkWhichUserMoves() {
        if (isFirstUserTurn) {

        }
        grid.getChildren().stream()
                .filter(Objects::nonNull)
                .filter(child -> child instanceof Circle)
                .forEach(child -> {
                    if (isFirstUserTurn) {
                        if (Integer.parseInt(child.getId()) > 5) {
                            child.setDisable(true);
                            child.getStyleClass().add("disabled");
                        } else {
                            child.setDisable(false);
                            child.getStyleClass().remove("disabled");
                        }
                    } else {
                        if (Integer.parseInt(child.getId()) <= 5) {
                            child.setDisable(true);
                            child.getStyleClass().add("disabled");
                        } else {
                            child.setDisable(false);
                            child.getStyleClass().remove("disabled");
                        }
                    }

                });
    }
}
