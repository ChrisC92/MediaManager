package gui.mediaplayer;


import gui.mediaplayer.util.FormatTime;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
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
    private Button fullScreen;

    @FXML
    private Label playTime;

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

    @FXML
    private HBox mediaControl;


    private boolean atEndOfMedia = false;

    private MediaDisplay mainApp;

    private FadeTransition fadeTransition;

    private Duration endTime;


    @FXML
    public void initialize() {
        fadeTransition = new FadeTransition(Duration.millis(2000), mediaControl);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
        fadeTransition.setCycleCount(1);

        // list of methods below initializes each class and adds listeners to controls
        playPause();
        mute();
        setFullScreen();
        stop();
    }

    protected void updateTime() {
        if (playTime != null && timeSlider != null && volumeSlider != null) {
            Platform.runLater(() -> {
                Duration currentTime = mediaPlayer.getCurrentTime();
                playTime.setText(FormatTime.formatTime(currentTime, endTime));
            });
        }
    }

    // sets both the volume and the playTime sliders
    private void setSliderValues() {
        Duration stopTime = media.getDuration();
        timeSlider.setMax(stopTime.toSeconds());
        timeSlider.setMin(0);
        timeSlider.setValue(1);
        volumeSlider.setMin(0.0);
        volumeSlider.setMax(1.0);
        volumeSlider.setValue(1.0);
        timeSlider.valueProperty().addListener((ObservableValue<? extends Number> observable,
                                                Number oldValue, Number newValue) -> {
            if (timeSlider.isPressed()) {
                double dur = newValue.intValue() * 1000;
                mediaPlayer.seek(new Duration(dur));
                Duration currentTime = mediaPlayer.getCurrentTime();
                playTime.setText(FormatTime.formatTime(currentTime, endTime));
            }
        });

        volumeSlider.valueProperty().addListener((ObservableValue<? extends Number> observable,
                                                  Number oldValue, Number newValue) -> {
            if (volumeSlider.isPressed()) {
                mediaPlayer.setVolume(newValue.doubleValue());
                volumeSlider.setValue(newValue.doubleValue());
            }
        });
    }

    @FXML
    private void playPause() {
        play.setOnAction(event -> {
            MediaPlayer.Status status = mediaPlayer.getStatus();

            if (status == MediaPlayer.Status.UNKNOWN || status == MediaPlayer.Status.HALTED) {
                // don't do anything in these states
                return;
            }

            if (status == MediaPlayer.Status.PAUSED
                    || status == MediaPlayer.Status.READY
                    || status == MediaPlayer.Status.STOPPED) {
                //rewind if at end of media
                if (atEndOfMedia) {
                    mediaPlayer.seek(mediaPlayer.getStartTime());
                    atEndOfMedia = false;
                }
                mediaPlayer.play();
            } else {
                mediaPlayer.pause();
            }
        });
    }

    @FXML
    private void stop() {

        stop.setOnAction(event -> {
            MediaPlayer.Status status = mediaPlayer.getStatus();
            if (status == MediaPlayer.Status.UNKNOWN || status == MediaPlayer.Status.HALTED) {
                // don't do anything in these states
                return;
            }
            if (atEndOfMedia) {
                mediaPlayer.seek(mediaPlayer.getStartTime());
                atEndOfMedia = false;
            }
            if (status == MediaPlayer.Status.PAUSED
                    || status == MediaPlayer.Status.READY
                    || status == MediaPlayer.Status.PLAYING) {
                mediaPlayer.stop();
            }
        });
    }

    @FXML
    private void mute() {
        muteVolume.setOnAction(event -> {
            if(mediaPlayer.getVolume() != 0) {
                mediaPlayer.setVolume(0);
                volumeSlider.setValue(0);
            } else {
                mediaPlayer.setVolume(0.5);
                volumeSlider.setValue(0.5);
            }
        });
    }

    @FXML
    private void setFullScreen() {
        fullScreen.setOnAction(event -> {
            mainApp.getPrimaryStage().setFullScreen(true);
        });
    }

    void playEpisode() {
        File file = mainApp.getTestEp();
        media = new Media(file.toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaView.setMediaPlayer(mediaPlayer);

        mediaPlayer.setOnReady(() -> {
            endTime = mediaPlayer.getStopTime();
            mediaPlayer.setAutoPlay(true);
            mediaPlayer.currentTimeProperty().addListener(observable -> {
                updateTime();
            });
            mediaPlayer.play();
            setSliderValues();
            mediaView.setPreserveRatio(false);
            mediaView.autosize();
        });

    }


    public void setMainApp(MediaDisplay main) {
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




/*
    fullscreen toggle
    primaryStage.setFullScreen(!primaryStage.isFullScreen());
 */