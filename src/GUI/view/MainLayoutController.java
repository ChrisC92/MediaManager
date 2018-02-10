package GUI.view;

import GUI.MainApp;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import metadata.*;

import java.io.File;
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

    //TODO: delete button

    @FXML
    private ToggleButton onFileButton;
    @FXML
    private ToggleButton allButton;
    final ToggleGroup buttonGroup = new ToggleGroup();

    @FXML
    private ListView<Series> seriesDisplayed;

    private AbstractSeriesList allSeries;
    private AbstractSeriesList onFileSeries;

    @FXML
    private ListView<SimpleStringProperty> episodeList;

    private Series currentSeriesSelected;
    private SimpleStringProperty currentEpisodeSelected;

    private MainApp mainApp;
    private Stage primaryStage;

    public MainLayoutController() {
    }

    @FXML
    private void initialize() {
        onFileButton.setToggleGroup(buttonGroup);
        allButton.setToggleGroup(buttonGroup);
        allButton.setSelected(true);

        allSeries = new SeriesSaved("savedData/storedSeriesList.ser");
        populateSeriesLists(allSeries);
        displayedSeriesInteraction();
        episodeListInteraction();
        setCurrentEpInteraction();
        toggleButtonInteraction();
        initialFileSelect();
    }

    private void initialFileSelect() {
        if (onFileSeries == null) {
            ButtonType openButton = new ButtonType("Open", ButtonBar.ButtonData.OK_DONE);
            ButtonType closeButton = new ButtonType("Close", ButtonBar.ButtonData.CANCEL_CLOSE);
            String contentText = "Please select your series folder";
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, contentText, openButton, closeButton);
            alert.setTitle("SeriesTracker");
            Optional<ButtonType> result = alert.showAndWait();

            if(result.isPresent()&& result.get() == openButton) {
                DirectoryChooser dirChoice = new DirectoryChooser();
                dirChoice.setTitle("FilePath Select");
                File seriesPath = dirChoice.showDialog(primaryStage);
                onFileSeries = new SeriesOnFile(seriesPath);
            } else {
                Platform.exit();
            }
        }
    }

    private void toggleButtonInteraction() {
        buttonGroup.getSelectedToggle().selectedProperty().addListener(((observable,
                                                                         oldValue, newValue) -> {
            if (!onFileSeries.isEmpty() || !allSeries.isEmpty())
                if (allButton.isSelected()) {
                    System.out.println("allButton selected");
                    System.out.println("allButton - " + allButton.isSelected());
                    populateSeriesLists(allSeries);
                } else if (onFileButton.isSelected()) {
                    System.out.println("onFile button selected");
                    System.out.println("onFile button - " + onFileButton.isSelected());
                    populateSeriesLists(onFileSeries);
                }
        }));
    }


    /**
     * Handles pressing of the setCurrentEpisode Button
     */
    private void setCurrentEpInteraction() {
        setCurrentEpisode.setOnAction((event) -> {
            if (currentEpisodeSelected != null) {
                currentSeriesSelected.setCurrentEpisode(currentEpisodeSelected.get());
                bottomTextField.clear();
                bottomTextField.appendText(currentEpisodeSelected.get());
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
    private void displayedSeriesInteraction() {
        seriesDisplayed.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
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


    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public void saveData() {
        String fileName = "savedData/storedSeriesList.ser";
        AbstractSeriesList.serializeList(allSeries, fileName);
    }

    public void setOnFileSeries(File filePath) {
        onFileSeries = new SeriesOnFile(filePath);
        //allSeries = AbstractSeriesList.combineSeries(allSeries, onFileSeries);
        populateSeriesLists(onFileSeries);
        bottomTextField.clear();
        bottomTextField.appendText("Series from file populated");

    }

    public void setStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
}
