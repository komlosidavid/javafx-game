package controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import table.Winner;
import table.WinnerRepository;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Controller to handle and display game history.
 */
public class GameHistoryController {

    /**
     * TableView to display game history on screen.
     */
    @FXML
    private TableView<Winner> table;
    /**
     * TableColumn to display winner name.
     */
    @FXML
    private TableColumn<Winner, String> winner;
    /**
     * TableColumn to display winner color.
     */
    @FXML
    private TableColumn<Winner, String> color;
    /**
     * TableColumn to display winner moves.
     */
    @FXML
    private TableColumn<Winner, Integer> moves;
    /**
     * TableColumn to display the date when the game was played.
     */
    @FXML
    private TableColumn<Winner, Date> date;

    /**
     * Initialize history scene for the first run.
     * @throws IOException if something goes wrong
     */
    @FXML
    private void initialize() throws IOException {
        winner.setCellValueFactory(new PropertyValueFactory<>("winnerName"));
        color.setCellValueFactory(new PropertyValueFactory<>("winnerColor"));
        moves.setCellValueFactory(new PropertyValueFactory<>("winnerMoves"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));

        var repo = new WinnerRepository();
        repo.loadFromFile(new File("history.json"));
        List<Winner> winners = repo.getWinners();

        ObservableList<Winner> observableList = FXCollections.observableArrayList();
        observableList.addAll(winners);
        table.setItems(observableList);
    }

    /**
     * Function to start a new game.
     * @throws IOException if there is a problem with opening another scene
     */
    @FXML
    public void newGame() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/SecondScene.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) table.getScene().getWindow();
        stage.setTitle("Game History");
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * Function to quit from the game.
     */
    @FXML
    private void quit() {
        Platform.exit();
    }

}
