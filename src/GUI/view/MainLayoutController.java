package GUI.view;

import GUI.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import metadata.Series;
import metadata.SeriesList;
import storageAndExtraction.Deserialize;
import storageAndExtraction.ExtractData;
import storageAndExtraction.Serialize;

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

    private SeriesList storedSeries;

    private SeriesList seriesOnFile;

    public MainLayoutController() {
    }

    @FXML
    private void initialize() {
        populateLists();
    }


    private void populateLists() {
        //TODO: refactor to allow use of user selecting file paths for series
        storedSeries = Deserialize.Deserialize("savedData/storedSeriesList.ser");
        File filePath = new File("/Users/ChrisCorner/Documents/Films_Series/Series");
        seriesOnFile = ExtractData.extractSeriesOnFile(filePath, seriesOnFile);
        storedSeries = SeriesList.combineSeries(storedSeries, seriesOnFile);

        savedFileDisplay.setItems(storedSeries.getSeriesList());
        onFileDisplay.setItems(seriesOnFile.getSeriesList());
        savedFileDisplay.disabledProperty();
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public void saveData() {
        String fileName = "savedData/storedSeriesList.ser";
        Serialize.serializeList(storedSeries,fileName);
    }

    public void setSeriesOnFile(File filePath) {
        seriesOnFile = new SeriesList();
        seriesOnFile = ExtractData.extractSeriesOnFile(filePath, seriesOnFile);
    }
}
