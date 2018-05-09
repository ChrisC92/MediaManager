package GUI.menus;

import GUI.MainApp;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import metadata.*;
import xStream.LoadFromFile;
import xStream.SaveToFile;


import java.io.*;
import java.util.Optional;

/**
 * Controller class that is linked with the MainLayout.fxml - handles all main interactions with the application apart from the menu bar
 * which is handled by the rootLayout class and .fxml
 */
public class MainLayoutController {


    @FXML
    private TextField bottomTextField;

    @FXML
    private Button setCurrentEpisode;

    @FXML
    private ToggleButton onFileButton;
    @FXML
    private ToggleButton allButton;
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
        allButton.setToggleGroup(buttonGroup);
        allButton.setSelected(true);
        allSeries = new SeriesSaved();

        initialFileSelect();
        populateSeriesLists(allSeries);
        displayedSeriesInteraction();
        episodeListInteraction();
        setCurrentEpInteraction();
        toggleButtonInteraction();
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
        //TODO: either handle when null or prevent being null
        buttonGroup.getSelectedToggle().selectedProperty().addListener(((observable,
                                                                         oldValue, newValue) -> {
            if (!seriesOnFile.isEmpty() || !allSeries.isEmpty())
                if (allButton.isSelected()) {
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
                bottomTextField.clear();
                bottomTextField.appendText("Change - " + currentEpisodeSelected.get());
                currentEpisodeSelected = new SimpleStringProperty();
            } else if (allButton.isSelected()) {
                bottomTextField.clear();
                bottomTextField.appendText("Setting Current episode must work from the onFile list");
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
                bottomTextField.clear();

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
                bottomTextField.appendText(newValue.getCurrentEpisode());
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
        bottomTextField.clear();
        bottomTextField.appendText("File has been deleted");
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
        bottomTextField.clear();
        bottomTextField.appendText("Series from file populated");
    }

    public void setStage(Stage primaryStage) {
        this.stage = primaryStage;
    }

}