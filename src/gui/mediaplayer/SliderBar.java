package gui.mediaplayer;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.layout.StackPane;

import java.io.IOException;


public class SliderBar extends StackPane {

    @FXML
    private Slider slider;
    @FXML
    private ProgressBar progressBar;


    public SliderBar() {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/mediaplayer/SliderBar.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try{
            loader.load();
        } catch (IOException io) {
            io.printStackTrace();
        }
        bindValues();

    }

    private void bindValues() {
        progressBar.prefWidthProperty().bind(slider.widthProperty());
        progressBar.prefHeightProperty().bind(slider.heightProperty());
    }
}
