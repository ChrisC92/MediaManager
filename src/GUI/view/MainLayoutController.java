package GUI.view;

import GUI.MainApp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import metadata.Series;
import metadata.SeriesList;
import storageAndExtraction.ExtractData;

import java.io.File;

public class MainLayoutController {



    @FXML
    private Label currentEpisode;

    @FXML
    private Button setCurrentEpisode;

    @FXML
    private ToggleButton onFileButton;

    @FXML
    private ToggleButton AllButton;

    @FXML
    private ListView<Series> seriesListed;

    @FXML
    private Slider slider;

    private MainApp mainApp;


    public MainLayoutController() {
    }

    @FXML
    private void initialize() {
        populateSeriesListed();
    }

    private void populateSeriesListed() {
        File filePath = new File("/Users/ChrisCorner/Documents/Films_Series/Series");
        SeriesList extract = new SeriesList();
        extract = ExtractData.extractSeriesOnFile(filePath, extract);
        ObservableList<Series> oSeriesList = FXCollections.observableArrayList(extract.getSeriesList());
        seriesListed.setItems(oSeriesList);
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
}
