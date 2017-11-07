package extractingData;

import metadata.Series;
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
     */
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
                    String path = fileEntry.getParent();
                    Series firstSeries = new Series(seriesName, path);
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

    public void printSeriesNames() {
        int i = 0;
        for(Series series : fullSeries) {
            System.out.println(i + ": " + series.getSeriesName());
            i += 1;
        }
    }
}

/**
 *
 *  TODO: This will normally take the actual initial folder as a series which isn't needed unless
 *  there are stray episodes in the folder, one way to fix this would be a method to
 *  find this folder and rename it possibly but if its empty delete it
 *  but if there are episoedes rename it to 'Unsorted episodes
 */

//TODO: A problem to fix is when this is initially done and then the folders change, so being able to add
// new series while still retaining data of current episode for the rest