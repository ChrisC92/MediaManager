package GUI.view;

import GUI.MainApp;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;
import metadata.*;

import java.io.File;
import java.security.SecureRandom;

/**
 *  Main layout
 *
 */
public class MainLayoutController {


    @FXML
    private TextField currentEpisode;

    @FXML
    private Button setCurrentEpisode;

    @FXML
    private Button goBackList;

    @FXML
    private Button delete;
    //TODO: complex delete could keep what is deleted in another file and prevents it from being re-added when the program extracts data

    @FXML
    private ToggleButton onFileButton;

    @FXML
    private ToggleButton AllButton;

    private AbstractSeriesList seriesOnFile;

    @FXML
    private ListView<Series> savedFileDisplay;
    private AbstractSeriesList seriesSaved;
    
    @FXML
    private ListView<SimpleStringProperty> episodeList;

    private MainApp mainApp;

    public MainLayoutController() {
    }

    @FXML
    private void initialize() {
        populateSeriesSaved();
        
        savedFileDisplay.setOnMouseClicked((evt) -> mouseClickedList() );

    }

    /**
     *  Takes the users click, retreives the episode list for the given series (newValue)
     */
    private void mouseClickedList() {
        savedFileDisplay.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
            currentEpisode.clear();
            episodeList.setItems(newValue.getEpisodes());
            /** Set cell factory to render the episode names */
            episodeList.setCellFactory(new Callback<ListView<SimpleStringProperty>, ListCell<SimpleStringProperty>>() {
                @Override
                public ListCell<SimpleStringProperty> call(ListView<SimpleStringProperty> param) {
                    return new ListCell<SimpleStringProperty>() {
                        @Override
                        public void updateItem(SimpleStringProperty episode, boolean empty) {
                            super.updateItem(episode, empty);
                            if(episode == null) {
                                setText(null);
                            } else {
                                setText(episode.getValue());
                            }
                        }
                    };
                }
            });
            currentEpisode.appendText(newValue.getCurrentEpisode());

        }));

    }


    private void populateEpisodeList(AbstractSeriesList seriesSelected ) {

    }
    //TODO: unsure if it is correct to make method to be used in lambda expression
    private void savedDisplayListener() {
    }


    private void changeLabel(Series seriesSelected) {
        currentEpisode.appendText(seriesSelected.getName());
    }



    private void populateSeriesSaved() {
        //TODO: refactor to allow use of user selecting file paths for series
        seriesSaved = new SeriesSaved("savedData/storedSeriesList.ser");
        File filePath = new File("/Users/ChrisCorner/Documents/Films_Series/Series");
        seriesOnFile = new SeriesOnFile(filePath);
        seriesSaved = AbstractSeriesList.combineSeries(seriesSaved, seriesOnFile);

        savedFileDisplay.setItems(seriesSaved.getSeriesList());
        /** Set cell factory allows for rendering each series name correctly */
        savedFileDisplay.setCellFactory(new Callback<ListView<Series>, ListCell<Series>>() {
            @Override
            public ListCell<Series> call(ListView<Series> param) {
                return new ListCell<Series>() {
                    @Override
                    public void updateItem(Series series, boolean empty) {
                        super.updateItem(series, empty);
                        if(series == null) {
                            setText(null);
                        } else {
                            setText(series.getName());
                        }
                    }
                };
            }
        });
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public void saveData() {
        String fileName = "savedData/storedSeriesList.ser";
        AbstractSeriesList.serializeList(seriesSaved, fileName);
    }

    public void setSeriesOnFile(File filePath) {
        seriesOnFile = new SeriesOnFile(filePath);
    }

    private void selectFromSeriesOnFile() {

    }
}
