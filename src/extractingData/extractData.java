package extractingData;

import metadata.Series;

import java.io.File;
import java.util.List;

/**
 *  This class takes scans a given folder from the file path and creates a Series object for each new Series and
 *  adding all epsiodes and defaulting the initial episode as the current and address these to an arrayList
 */
public class extractData {


    /**
     * Takes the file path and goes through recursively through directories until it reaches a file and then looks
     * to store new SeriesFileFormat into the seriesList variable and if the series is already contained then add
     * the episode into that object
     */
    public static List<Series> getSeriesInfo(File filePath, List<Series> seriesList) throws NullPointerException {
        for (File fileEntry : filePath.listFiles()) {
            if (fileEntry.isDirectory()) {
                getSeriesInfo(fileEntry, seriesList);
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
                    //TODO: REVIEW
                    String path = fileEntry.getParent();
                    Series firstSeries = new Series(seriesName, path);
                    firstSeries.addEpisode(episodeName);
                    firstSeries.initialCurrentEpAssign(episodeName);
                    seriesList.add(firstSeries);
                }

            }
        }
        return seriesList;
    }

    private static boolean isSeriesIncluded(String seriesName, List<Series> seriesList) {
        if(seriesList == null) {
            return false;
        }
        for (Series series : seriesList) {
            if (series.getSeriesName().equals(seriesName)) {
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
            if (series.getSeriesName().equals(seriesName)) {
                series.addEpisode(episodeName);
            }
        }
    }
}
