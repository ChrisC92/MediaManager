package metadata;

import extractingData.extractData;

import java.io.File;
import java.util.*;

public class SeriesList implements java.io.Serializable {

    private List<Series> seriesList;

    public SeriesList(File filePath) {
        seriesList = new ArrayList<>();
        this.seriesList = extractData.getSeriesInfo(filePath, seriesList);
        //TODO: might be a wasteful method call, find a way to check if needed, remove or keep
        // if the serialized item does not exist then this is needed
        sortEpisodes();
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

    public void printSeriesList() {
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
        return seriesList.get(userInput);
    }

    public void addSeries(Series toAdd) {
        seriesList.add(toAdd);
        sortEpisodes();
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