package GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * Class of application to start the game.
 */
public class GameBoardApplication extends Application {

    /**
     * Starter function to show the first stage.
     * @param stage the shown stage of the application
     * @throws IOException if something goes wrong
     */
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/views/StartScene.fxml")));
        stage.setTitle("Board Game");
        stage.setScene(new Scene(root));
        stage.show();
    }

}
