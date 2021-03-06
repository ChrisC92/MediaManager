package gui.menus;

import gui.MainApp;
import gui.mediaplayer.MediaDisplay;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import metadata.AbstractSeriesList;
import metadata.Series;
import metadata.SeriesOnFile;
import metadata.SeriesSaved;
import saveandload.LoadFromFile;
import saveandload.SaveToFile;

import java.io.File;
import java.util.Optional;

/**
 * Controller class that is linked with the MainLayout.fxml - handles all main interactions with the application apart from the menu bar
 * which is handled by the rootLayout class and .fxml
 */
public class MainLayoutController {


    @FXML
    private TextField textField;

    @FXML
    private Button setCurrentEpisode;

    @FXML
    private Button playCurrentEpisode;

    @FXML
    private Button playSelectedEpisode;

    @FXML
    private ToggleButton onFileButton;
    @FXML
    private ToggleButton allSeriesButton;
    final ToggleGroup buttonGroup = new ToggleGroup();

    @FXML
    private ListView<Series> seriesDisplayed;

    private AbstractSeriesList allSeries;
    private AbstractSeriesList seriesOnFile;

    @FXML
    private ListView<SimpleStringProperty> episodeList;

    private Series currentSeriesSelected;
    private SimpleStringProperty currentEpisodeSelected;
    private File seriesFilePath;

    private MainApp mainApp;
    private Stage stage;

    public MainLayoutController() {
    }

    /**
     * Populates the two seriesLists used - the series currently on file and the allSeries which
     * includes series that have been deleted - sets to allSeries and
     * displays list and initializes any user input
     */
    @FXML
    private void initialize() {
        onFileButton.setToggleGroup(buttonGroup);
        allSeriesButton.setToggleGroup(buttonGroup);
        allSeriesButton.setSelected(true);
        allSeries = new SeriesSaved();

        // list of methods below initializes each class and adds listeners to controls
        initialFileSelect();
        populateSeriesLists(allSeries);
        displayedSeriesInteraction();
        episodeListInteraction();
        setCurrentEpInteraction();
        toggleButtonInteraction();
        playCurrentEpisode();
        playChosenEpisode();
    }

    private void initialFileSelect() {
        if (allSeries.isEmpty()) {
            ButtonType openButton = new ButtonType("Open", ButtonBar.ButtonData.OK_DONE);
            ButtonType closeButton = new ButtonType("Close", ButtonBar.ButtonData.CANCEL_CLOSE);
            String contentText = "Please select your series folder";
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, contentText, openButton, closeButton);
            alert.setTitle("SeriesTracker");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == openButton) {
                DirectoryChooser dirChoice = new DirectoryChooser();
                dirChoice.setTitle("File Path Select");
                File seriesPath = dirChoice.showDialog(stage);
                seriesFilePath = seriesPath;
                setSeriesOnFile(seriesPath);
            } else {
                Platform.exit();
            }
        } else {
            seriesFilePath = new File(LoadFromFile.loadSeriesFilePath());
            seriesOnFile = new SeriesOnFile(seriesFilePath, allSeries);
        }
    }

    private void toggleButtonInteraction() {
        buttonGroup.getSelectedToggle().selectedProperty().addListener(((observable,
                                                                         oldValue, newValue) -> {
            if (!seriesOnFile.isEmpty() || !allSeries.isEmpty())
                if (allSeriesButton.isSelected()) {
                    populateSeriesLists(allSeries);
                } else if (onFileButton.isSelected()) {
                    populateSeriesLists(seriesOnFile);
                }
        }));
    }


    /**
     * Handles pressing of the setCurrentEpisode Button
     */
    private void setCurrentEpInteraction() {
        setCurrentEpisode.setOnAction((event) -> {
            if (currentEpisodeSelected != null && onFileButton.isSelected()) {
                currentSeriesSelected.setCurrentEpisode(currentEpisodeSelected.get());
                textField.clear();
                textField.appendText("Change - " + currentEpisodeSelected.get());
                currentEpisodeSelected = new SimpleStringProperty();
            } else if (allSeriesButton.isSelected()) {
                textField.clear();
                textField.appendText("Setting Current episode must work from the onFile list");
            }
        });
    }

    /**
     * Will start playing from selected series and updates current episode whenever it opens a new one
     */
    private void playCurrentEpisode() {
        playCurrentEpisode.setOnAction((event) -> {

        });
    }

    /**
     * Will play the selected episode however this will not affect the current episode
     */
    private void playChosenEpisode() {
        playSelectedEpisode.setOnAction((event) -> {
            MediaDisplay mediaDisplay = new MediaDisplay();
            if (currentEpisodeSelected != null && onFileButton.isSelected()) {
                StringBuilder sb = new StringBuilder(seriesFilePath.toString());
                sb.append("/");
                sb.append(currentSeriesSelected.getName());
                sb.append("/");
                sb.append(currentEpisodeSelected.getValue());
                try {
                    mediaDisplay.playEpisode(stage, new File(sb.toString()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (allSeriesButton.isSelected()) {
                textField.clear();
                textField.appendText("Unable to open chosen if All Series Button is selected");
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
    private void displayedSeriesInteraction() {
        seriesDisplayed.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue != null) {
                currentSeriesSelected = newValue;
                textField.clear();
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
                textField.appendText(newValue.getCurrentEpisode());
            }
        }));
    }

    private void populateSeriesLists(AbstractSeriesList seriesList) {
        seriesDisplayed.setItems(seriesList.getSeriesList());
        /** Set cell factory allows for rendering each series name correctly */
        seriesDisplayed.setCellFactory(new Callback<ListView<Series>, ListCell<Series>>() {
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

    /**
     * Deletes the file saved and then populates with the seriesOnFile
     */
    void deleteSavedFiles() {
        allSeries = new SeriesSaved();
        populateSeriesLists(seriesOnFile);
        textField.clear();
        textField.appendText("File has been deleted");
    }


    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * Saves the AllSeries list and also the filePath last used by the user to populate onFileList
     */
    void saveData() {
        allSeries = AbstractSeriesList.combineSeries(allSeries, seriesOnFile);
        SaveToFile.saveSeriesToFile(allSeries);
        SaveToFile.saveFilePathToFile(seriesFilePath.getPath());
    }


    public void setSeriesOnFile(File filePath) {
        seriesOnFile = new SeriesOnFile(filePath);
        allSeries = AbstractSeriesList.combineSeries(allSeries, seriesOnFile);
        populateSeriesLists(seriesOnFile);
        onFileButton.setSelected(true);
        textField.clear();
        textField.appendText("Series from file populated");
    }

    public void setStage(Stage primaryStage) {
        this.stage = primaryStage;
    }

}