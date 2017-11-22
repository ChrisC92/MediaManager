package metadata;

import extractingData.getSeriesInfo;
import metadata.Series;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.File;
import java.util.*;

/**
 * Scans the given folder and adds the details to series as Series objects and stores these in a list
 */
public class SeriesList {

    private List<Series> seriesList = new ArrayList<>();

    public SeriesList(File filePath) {
        this.seriesList = getSeriesInfo.getSeriesInfo(filePath);
    }

    public List<Series> getSeriesList() {
        return seriesList;
    }


    public int seriesListSize() {
        return seriesList.size();
    }

    private void sortEpisodes() {
        for (Series series : seriesList) {
            series.naturalOrdering();
        }
    }

    public void printSeriesNames() {
        int i = 1;
        for (Series series : seriesList) {
            System.out.println(i + ": " + series.getSeriesName());
            i += 1;
        }
    }

    public void printEpisodeList(int index) {
        seriesList.get(index).printEpisodes();
    }

    public Series getSeries(int userInput) {
        return seriesList.get(userInput-1);
    }
}

/**
 * TODO: This will normally take the actual initial folder as a series which isn't needed unless
 * there are stray episodes in the folder, one way to fix this would be a method to
 * find this folder and rename it possibly but if its empty delete it
 * but if there are episoedes rename it to 'Unsorted episodes
 */

//TODO: A problem to fix is when this is initially done and then the folders change, so being able to add
// new series while still retaining data of current episode for the rest