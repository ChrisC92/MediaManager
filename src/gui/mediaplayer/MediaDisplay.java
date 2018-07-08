package gui.mediaplayer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.File;
import java.util.Queue;

public class MediaDisplay extends Application {

    private File testEp = new File("/Users/ChrisCorner/Documents/Films_Series/Series/Adventure Time - Season 7/S07E01-Bonnie & Neddy.mp4");

    @Override
    public void start(Stage stage) throws Exception {
        Stage primaryStage = stage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/mediaplayer/MediaController.fxml"));
        BorderPane pane = loader.load();
        Scene scene = new Scene(pane, 650, 400);
        primaryStage.setScene(scene);
        MediaController controller = loader.getController();
        bindSize(controller, scene);
        controller.setMainApp(this);
        controller.playEpisode();
        primaryStage.show();
    }

    private void setTestEp(File testEp) {
        this.testEp = testEp;
    }

    private void bindSize(MediaController controller, Scene scene) {
        controller.timerSliderWidthProperty().bind(scene.widthProperty().subtract(300));
        controller.mediaViewWidthProperty().bind(scene.widthProperty());
        controller.mediaViewHeightProperty().bind(scene.heightProperty().subtract(70));
    }


    public static void main(String[] args) {
        launch(args);
    }

    public File getTestEp() {
        return testEp;
    }

    public static void playEpisode(Stage stage, File episode){
        // TODO: once code is completed and tested implement to static method
    }

    public static void playPlaylist(Stage stage, Queue playlist) {
        //TODO: complete playPlaylist method
    }
}
