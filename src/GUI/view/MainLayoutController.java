package GUI.view;

import GUI.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import metadata.Series;
import metadata.SeriesList;
import storageAndExtraction.ExtractData;

import java.io.File;

public class MainLayoutController {

    @FXML
    private SeriesList listOnFile;

    @FXML
    private SeriesList allFiles;

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
        File filePath = new File("/Users/ChrisCorner/Documents/Films_Series/Series");
        //SeriesList extract = new SeriesList(filePath);
        //seriesListed =
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
}
