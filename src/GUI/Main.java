package GUI;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.control.ListView;

import java.io.IOException;

import static javafx.application.Application.launch;

/**
 *  This class uses JavaFX to create an interface displaying the series lists and its contained episodes
 *
 */
public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("layout.fxml"));
        primaryStage.setTitle("Series Tracker");
        primaryStage.setScene(new Scene(root, 600, 300));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
