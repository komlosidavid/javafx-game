package controllers;

import model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * Controller of the second scene.
 */
public class SecondSceneController {

    /**
     * TextField to get the first username.
     */
    @FXML
    private TextField firstUserTextField;
    /**
     * TextField to get the second username.
     */
    @FXML
    private TextField secondUserTextField;

    /**
     * Function to show alert box when there is a problem
     * with given usernames.
     * @param alertMessage message of the shown alert
     */
    private void showAlertBox(String alertMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(alertMessage);
        alert.show();
    }

    /**
     * Function to switch to game scene.
     * @param event used to identify actual scene
     * @throws IOException is something goes wrong
     */
    @FXML
    public void switchToGameScene(javafx.event.ActionEvent event) throws IOException {

        String firstUserTextFieldContent = firstUserTextField.getText();
        String secondUserTextFieldContent = secondUserTextField.getText();

        User firstUser;
        User secondUser;

        if (firstUserTextFieldContent.trim().length() == 0) {
            showAlertBox("A játékosnév mezők nem maradhatnak üresen!");
        }
        else if (secondUserTextFieldContent.trim().length() == 0) {
            showAlertBox("A játékosnév mezők nem maradhatnak üresen!");
        }
        else if (firstUserTextFieldContent.trim().equals(secondUserTextFieldContent.trim())) {
            showAlertBox("A két játékos neve nem egyezhet meg.");
        }
        else {
            firstUser = new User(1, firstUserTextFieldContent.trim());
            secondUser = new User(2, secondUserTextFieldContent.trim());

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Objects.requireNonNull(getClass().getResource("/views/GameScene.fxml")));
            Parent root = loader.load();

            GameController controller = loader.getController();
            controller.initUsers(firstUser, secondUser);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        }
    }

}
