package metadata;

import javafx.collections.ObservableList;
import saveandload.LoadFromFile;

public class SeriesSaved extends AbstractSeriesList {

    public SeriesSaved() {
        super(LoadFromFile.loadSeriesList().getSeriesList());
    }

    // Test constructor that can be deleted
    public SeriesSaved(ObservableList<Series> seriesList) {
        super(seriesList);
    }

}
