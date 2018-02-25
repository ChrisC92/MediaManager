/**
 * TODO: have the user choose the series folder each time the app is opened
 */


package metadata;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SeriesOnFile extends AbstractSeriesList {



    public SeriesOnFile() {
        //TODO: refactor to not use a method
        super(createSeriesList());
    }

    public SeriesOnFile(File filePath) {
        super(extractSeriesOnFile(filePath, createSeriesList()));
    }

    public SeriesOnFile(AbstractSeriesList toCopy) {
        super(toCopy.getSeriesList());
    }

    public SeriesOnFile(File filePath, AbstractSeriesList allSeries) {
        super(extractAndCombine(filePath, allSeries));
    }

    /**
     * Takes the file path and goes through recursively through directories until it reaches a file and then looks
     * to store new SeriesFileFormat into the seriesList variable and if the series is already contained then add
     * the episode into that object
     */
    private static ArrayList<Series> extractSeriesOnFile(File filePath, ArrayList<Series> seriesList)
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

    private List<Series> arrayListSeries() {
        List<Series> toReturn = new ArrayList<>();

        for (Series series : super.getSeriesList()) {
            toReturn.add(series);
        }
        return toReturn;
    }

    /**
     *  Extracts from the filePath that was previously given and then compares with the list thats saved
     *  on file. If the series is saved on file this is then added to the list to be returned
     *  this will allow for the correct updated series to be loaded
     *
     */
    private static ArrayList<Series> extractAndCombine(File filePath, AbstractSeriesList allSeries) {
        ArrayList<Series> toReturn = new ArrayList<>();
        ArrayList<Series> extracted = new ArrayList<>();
        extracted = extractSeriesOnFile(filePath, extracted);

        for(Series series : allSeries.getSeriesList()) {
            if(extracted.contains(series)) {
                toReturn.add(series);
            }
        }

        return toReturn;
    }
    private static ArrayList<Series> createSeriesList() {
        ArrayList<Series> seriesList = new ArrayList<>();
        return seriesList;
    }

}