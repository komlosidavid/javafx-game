package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

/**
 * Starter scene.
 */
public class StartSceneController {

    /**
     * Button to change to gameHistory scene.
     */
    @FXML
    private Button showHistoryButton;

    /**
     * Initialize the scene for the first run.
     * If history.json file not exists,
     * the showHistoryButton will be disabled.
     */
    @FXML
    private void initialize() {
        if (Files.notExists(Path.of("history.json"))) {
            showHistoryButton.setDisable(true);
        }
    }

    /**
     * Function to switch to the second scene.
     * @param event represents the stage event
     * @throws IOException if something goes wrong
     */
    @FXML
    public void switchToSecondScene(javafx.event.ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/views/SecondScene.fxml")));
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Function to switch to the history scene.
     * @param event represents the stage event
     * @throws IOException if something goes wrong
     */
    @FXML
    private void showHistory(javafx.event.ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/views/gameHistory.fxml")));
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
    }

}
