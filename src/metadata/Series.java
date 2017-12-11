package metadata;

import formattingAndOrdering.NaturalOrderComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Same as the series class however this class will keep all the episodes that is read through the SeriesList Class
 * in an array list
 */
public class Series implements java.io.Serializable {

    private final String series;
    private String seriesFilePath;
    private List<String> episodes;
    private String currentEpisode;

    public Series(String series,String filePath) {
        this.series = series;
        this.episodes = new ArrayList<>();
        this.seriesFilePath = filePath;
    }

    public void addEpisode(String epName) {
            episodes.add(epName);
    }

    public void initialCurrentEpAssign(String epName) {
            currentEpisode = epName;
        }

    private boolean containsEp(String epName) {
        for (String episode : episodes) {
            if (episode.equals(epName)) {
                return true;
            }
        }
        return false;
    }

    public void naturalOrdering() {
        Collections.sort(episodes, new NaturalOrderComparator());
    }

    public String getSeriesName() {
        return series;
    }

    public String getCurrentEpisode() {
        return currentEpisode;
    }

    public String getEpisode(int index) {
        if (index >= episodes.size() || index < 0) {
            System.out.println("Index in valid, returning current episode");
            return currentEpisode;
        }
        return episodes.get(index);
    }

    public String getSeriesFilePath() {
        return seriesFilePath;
    }

    /**
     * This method can be used for when the user wants to choose an episode
     */
    public void setCurrentEpisode(String epName) {
        boolean containsEp = false;
        for (String episode : episodes) {
            if (episode.equals(epName)) {
                currentEpisode = episode;
                containsEp = true;
            }
        }

        if (containsEp) {
            System.out.println("Current episode has been changed to " + epName);
        } else {
            System.out.println(epName + " is not a valid episode. Current episode has not been changed");
        }
    }

    public void setCurrentEpisode(int index) {
        currentEpisode = episodes.get(index-1);
    }


    public void printEpisodes() {
        int index = 1;
        System.out.println("Current episode: " + currentEpisode);
        for(String episode : episodes) {
            System.out.println(index + ". " + episode);
            index += 1;
        }
    }

}