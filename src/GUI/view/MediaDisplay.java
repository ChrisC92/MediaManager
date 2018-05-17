package GUI.view;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;

public class MediaDisplay {

    private final File testEp = new File("/Users/ChrisCorner/Documents/Films_Series/Series/Adventure Time - Season 7/S07E01-Bonnie & Neddy.mp4");


    public static void openDisplay(Stage stage, File episode) {
        MediaPlayer mediaPlayer;
        try {
            Group root = new Group();
            Scene scene = new Scene(root, 600, 400);

            Media media = new Media(episode.toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setAutoPlay(true);

            MediaController mediaController = new MediaController(mediaPlayer);
            scene.setRoot(mediaController);

            stage.setScene(scene);
            stage.sizeToScene();
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
