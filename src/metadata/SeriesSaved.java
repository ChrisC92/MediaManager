package metadata;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import xStream.LoadFromFile;

import java.io.*;

public class SeriesSaved extends AbstractSeriesList {

    public SeriesSaved() {
        super(LoadFromFile.loadSeriesList().getSeriesList());
    }

    // Test constructor that can be deleted
    public SeriesSaved(ObservableList<Series> seriesList) {
        super(seriesList);
    }

}
