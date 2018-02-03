package GUI.view;

import GUI.MainApp;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;
import metadata.*;

import java.io.File;

/**
 * Controller class that is linked with the MainLayout.fxml - handles all main interactions with the application apart from the menu bar
 * which is handled by the rootLayout class and .fxml
 */
public class MainLayoutController {


    @FXML
    private TextField currentEpisode;

    @FXML
    private Button setCurrentEpisode;

    @FXML
    private Button delete;
    //TODO: complex delete could keep what is deleted in another file and prevents it from being re-added when the program extracts data

    final ToggleGroup buttonGroup = new ToggleGroup();
    @FXML
    private ToggleButton onFileButton;
    @FXML
    private ToggleButton allButton;

    @FXML
    private ListView<Series> onFileDisplay;
    private AbstractSeriesList seriesOnFile;

    @FXML
    private ListView<Series> savedDisplay;
    private AbstractSeriesList seriesSaved;

    @FXML
    private ListView<SimpleStringProperty> episodeList;

    private Series currentSeriesSelected;
    private SimpleStringProperty currentEpisodeSelected;

    private MainApp mainApp;

    public MainLayoutController() {
    }

    @FXML
    private void initialize() {
        populateSeriesLists();
        savedSeriesInteraction();
        episodeListInteraction();
        setCurrentEpInteraction();
        toggleButtonInteraction();
    }

    private void toggleButtonInteraction() {
        onFileButton.setToggleGroup(buttonGroup);
        allButton.setToggleGroup(buttonGroup);
    }

    /**
     * Handles pressing of the setCurrentEpisode Button
     */
    private void setCurrentEpInteraction() {
        setCurrentEpisode.setOnAction((event) -> {
            if (currentEpisodeSelected != null) {
                currentSeriesSelected.setCurrentEpisode(currentEpisodeSelected.get());
                currentEpisode.clear();
                currentEpisode.appendText(currentEpisodeSelected.get());
                currentEpisodeSelected = new SimpleStringProperty();
            }
        });
    }

    /**
     * Handles interactions with the episodeLists and also the setCurrentEpisode button
     * Sets the newValue as the current episode if the setCurrentEpisode button is selected
     */
    private void episodeListInteraction() {
        episodeList.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
                currentEpisodeSelected = newValue;
        }));
    }

    /**
     * Handles any mouse click on the list of saved series and displays the list of episodes on the
     * bottom
     */
    private void savedSeriesInteraction() {
        savedDisplay.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
            currentSeriesSelected = newValue;
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
                            if (episode == null) {
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

    private void populateSeriesLists() {
        //TODO: refactor to allow use of user selecting file paths for series
        seriesSaved = new SeriesSaved("savedData/storedSeriesList.ser");
        File filePath = new File("/Users/ChrisCorner/Documents/Films_Series/Series");
        seriesOnFile = new SeriesOnFile(filePath);
        seriesSaved = AbstractSeriesList.combineSeries(seriesSaved, seriesOnFile);

        savedDisplay.setItems(seriesSaved.getSeriesList());
        /** Set cell factory allows for rendering each series name correctly */
        savedDisplay.setCellFactory(new Callback<ListView<Series>, ListCell<Series>>() {
            @Override
            public ListCell<Series> call(ListView<Series> param) {
                return new ListCell<Series>() {
                    @Override
                    public void updateItem(Series series, boolean empty) {
                        super.updateItem(series, empty);
                        if (series == null) {
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
