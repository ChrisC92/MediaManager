package metadata;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SeriesOnFile extends AbstractSeriesList implements Serializable {

    public SeriesOnFile() {
    }

    public SeriesOnFile(File filePath) {
        super(filePath);
    }

    public SeriesOnFile(AbstractSeriesList toCopy ) {
        super(FXCollections.observableArrayList(toCopy.getSeriesList()));
    }

    /**
     * Takes the file path and goes through recursively through directories until it reaches a file and then looks
     * to store new SeriesFileFormat into the seriesList variable and if the series is already contained then add
     * the episode into that object
     */
    public static ObservableList<Series> extractSeriesOnFile(File filePath, ObservableList<Series> seriesList)
            throws NullPointerException {

        for (File fileEntry : filePath.listFiles()) {
            if (fileEntry.isDirectory()) {
                extractSeriesOnFile(fileEntry, seriesList);
            } else {
                String seriesName = fileEntry.getParentFile().getName();
                String episodeName = fileEntry.getName();
                if (episodeName.equals(".DS_Store")) {
                    continue;
                }
                /**
                 * if it does not contain the series then add the series, first ep and make first ep current ep
                 * if it does contain the series add the episode to the list within the series object
                 */
                if (isSeriesIncluded(seriesName, seriesList)) {
                    addEpisode(seriesName, episodeName, seriesList);
                } else {
                    Series firstSeries = new Series(seriesName);
                    firstSeries.addEpisode(episodeName);
                    firstSeries.initialCurrentEpAssign(episodeName);
                    seriesList.add(firstSeries);
                }

            }
        }
        sortAllEpisodes(seriesList);
        return seriesList;
    }

    private static boolean isSeriesIncluded(String seriesName, List<Series> seriesList) {
        if (seriesList == null) {
            return false;
        }
        for (Series series : seriesList) {
            if (series.getName().equals(seriesName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Iterates through the list of series and adds the episode to the correct series
     */
    private static void addEpisode(String seriesName, String episodeName, List<Series> seriesList) {
        for (Series series : seriesList) {
            if (series.getName().equals(seriesName)) {
                series.addEpisode(episodeName);
            }
        }
    }

    private static void sortAllEpisodes(List<Series> seriesList) {
        for (Series series : seriesList) {
            series.sortEpisodes();
        }
    }

    /**
     * Performs custom serialization of this instance.
     * Automatically is invoked by Java when this instance is serialized
     */
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();

        out.writeObject(arrayListSeries());
    }

    /**
     * Performs custom deserialization of this instance
     * Automatically is invoked by Java during deserialization
     */
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        ArrayList<Series> extracted = (ArrayList<Series>) in.readObject();
        super.setSeriesList(FXCollections.observableArrayList(extracted));
    }

    private List<Series> arrayListSeries() {
        List<Series> toReturn = new ArrayList<>();

        for (Series series : super.getSeriesList()) {
            toReturn.add(series);
        }
        return toReturn;
    }
}