package gui.mediaplayer;


import javafx.beans.property.DoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

import java.io.File;

public class MediaController {

    @FXML
    private Button play;

    @FXML
    private Button stop;

    @FXML
    private Button muteVolume;

    @FXML
    private Label time;

    @FXML
    private Slider timeSlider;

    @FXML
    private Slider volumeSlider;

    @FXML
    private Media media;

    @FXML
    private MediaPlayer mediaPlayer;

    @FXML
    private MediaView mediaView;


    private boolean endOfMedia = false;

    private MediaDisplay mainApp;


    @FXML
    public void initialize() {

    }

    // sets both the volume and the time sliders
    private void setSliderValues(MediaPlayer mp) {
        Duration stopTime = mp.getStopTime();
        timeSlider.setMax(stopTime.toSeconds());
        timeSlider.setMin(0.0);
        volumeSlider.setMin(0.0);
        volumeSlider.setMax(1.0);
    }

    @FXML
    private void playPause() {
        play.setOnAction(event -> {

        });
    }

    @FXML
    private void stop() {
        stop.setOnAction(event -> {

        });
    }

    @FXML
    private void mute() {
        muteVolume.setOnAction(event -> {

        });
    }

    public void playEpisode() {
        File file = mainApp.getTestEp();
        media = new Media(file.toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaView.setMediaPlayer(mediaPlayer);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.play();
        mediaView.setPreserveRatio(false);
        mediaView.autosize();
    }


    public void setMainApp(MediaDisplay main ) {
        this.mainApp = main;
    }


    public DoubleProperty timerSliderWidthProperty() {
        return timeSlider.prefWidthProperty();
    }

    public DoubleProperty mediaViewHeightProperty() {
        return mediaView.fitHeightProperty();
    }

    public DoubleProperty mediaViewWidthProperty() {
        return mediaView.fitWidthProperty();
    }

}
