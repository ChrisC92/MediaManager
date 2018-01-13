package GUI.view;

import GUI.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import metadata.*;

import java.io.File;

/**
 *  Main layout
 *
 */
public class MainLayoutController {


    @FXML
    private Label currentEpisode;

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

    @FXML
    private ListView<Series> onFileDisplay;

    @FXML
    private ListView<Series> savedFileDisplay;


    private MainApp mainApp;

    private AbstractSeriesList seriesSaved;

    private AbstractSeriesList seriesOnFile;

    public MainLayoutController() {
    }

    @FXML
    private void initialize() {
        populateLists();
    }


    private void populateLists() {
        //TODO: refactor to allow use of user selecting file paths for series
        seriesSaved = new SeriesSaved("savedData/storedSeriesList.ser");
        File filePath = new File("/Users/ChrisCorner/Documents/Films_Series/Series");
        seriesOnFile = new SeriesOnFile(filePath);
        seriesSaved = AbstractSeriesList.combineSeries(seriesSaved, seriesOnFile);

        savedFileDisplay.setItems(seriesSaved.getSeriesList());
        onFileDisplay.setItems(seriesOnFile.getSeriesList());
        savedFileDisplay.disabledProperty();
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
}
