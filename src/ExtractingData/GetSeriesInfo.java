package ExtractingData;

import Metadata.Series;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.File;
import java.util.*;

/**
 * Scans the given folder and adds the details of series and first episode to a map
 */
public class GetSeriesInfo {

    private List<Series> fullSeries = new ArrayList<>();

    public GetSeriesInfo(File filePath) {
        this.fullSeries = this.getSeriesInfo(filePath);
    }

    public List<Series> getSeriesList() {
        return fullSeries;
    }

    /**
     * Takes the file path and goes through recursively through directories until it reaches a file and then looks
     * to store new SeriesFileFormat into the fullSeries variable and if the series is already contained then add the episode
     * into that object
     * TODO: getSeriesInfo() could be optimised
     */
    //TODO: Filter out hidden files for .DS_Store
    private List<Series> getSeriesInfo(File filePath) {
        for (File fileEntry : filePath.listFiles()) {
            if (fileEntry.isDirectory()) {
                getSeriesInfo(fileEntry);
            } else {
                String seriesName = fileEntry.getParentFile().getName();
                String episodeName = fileEntry.getName();
                if(episodeName.equals(".DS_Store")){
                    continue;
                }
                /**
                 * if it does not contain the series then add the series, first ep and make first ep current ep
                 * if it does contain the series add the episode to the list within the series object
                 */
                if (isSeriesInlcuded(seriesName)) {
                    addEpisode(seriesName, episodeName);
                } else {
                    Series firstSeries = new Series(seriesName);
                    firstSeries.addEpisode(episodeName);
                    firstSeries.initialCurrentEpAssign(episodeName);
                    fullSeries.add(firstSeries);
                }

            }
        }
        return fullSeries;
    }

    /**
     * Takes directory name and checks to see if anything in the name indicates it to be a folder
     * containing episodes to a season
     * ** if(contains "season" AND a number(ignore capitals)
     * OR Sxx (xx being some number)
     */
    private boolean isDirASeason(File file) {
        throw new NotImplementedException();
    }


    private boolean isSeriesInlcuded(String seriesName) {
        for (Series series : fullSeries) {
            if (series.getSeriesName().equals(seriesName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Iterates through the list of series and adds the episode to the correct series
     */
    private void addEpisode(String seriesName, String episodeName) {
        for (Series series : fullSeries) {
            if (series.getSeriesName().equals(seriesName)) {
                series.addEpisode(episodeName);
            }
        }
    }


    private void sortEpisodes() {
        for (Series series : fullSeries) {
            series.naturalOrdering();
        }
    }

    private void printList() {
        for(Series series : fullSeries) {
            System.out.println(series.getSeriesName());
        }
    }
}

/**
 *  TODO: This will now bypass any stray epsiodes in the as the continue in the seriesInfo() method
 *  will skip over it
 *
 *  This will normally take the actual initial folder as a series which isn't needed unless
 *  there are stray episodes in the folder, one way to fix this would be a method to
 *  see if the first folder in the array of Series objects is empty and if so delete it
 *  but if there are episoedes rename it to 'Unsorted episodes
 */