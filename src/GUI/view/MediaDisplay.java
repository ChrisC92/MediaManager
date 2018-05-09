package GUI.view;

import metadata.AbstractSeriesList;
import metadata.SeriesOnFile;

import java.io.File;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class MediaDisplay extends Application {

    private final File testEp = new File("/Users/ChrisCorner/Documents/Films_Series/Series/Adventure Time - Season 7/S07E01-Bonnie & Neddy.mp4");
    private MediaPlayer mediaPlayer;

    public AbstractSeriesList getList() {
        File filePath = new File("/Users/ChrisCorner/Documents/Films_Series/Series");
        AbstractSeriesList seriesList = new SeriesOnFile(filePath);
        return seriesList;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Media Player");
        Group root = new Group();
        Scene scene = new Scene(root, 540, 241);

        Media media = new Media(testEp.toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);

        MediaController mediaController = new MediaController(mediaPlayer);
        scene.setRoot(mediaController);

        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
