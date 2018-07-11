package gui.mediaplayer;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.File;
import java.util.Queue;

public class MediaDisplay {

    private Stage primaryStage;

    public void playEpisode(Stage stage, File episode) throws Exception {
        primaryStage = stage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/mediaplayer/MediaController.fxml"));
        BorderPane pane = loader.load();
        Scene scene = new Scene(pane, 650, 400);
        primaryStage.setScene(scene);
        MediaController controller = loader.getController();
        bindSize(controller, scene);
        controller.setMainApp(this);
        controller.playEpisode(episode);
        primaryStage.show();
    }

    public static void playPlaylist(Stage stage, Queue playlist) {
        //TODO: complete playPlaylist method
    }


    private static void bindSize(MediaController controller, Scene scene) {
        controller.timerSliderWidthProperty().bind(scene.widthProperty().subtract(380));
        controller.mediaViewWidthProperty().bind(scene.widthProperty());
        controller.mediaViewHeightProperty().bind(scene.heightProperty().subtract(46));
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }
}
