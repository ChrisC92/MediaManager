package Metadata;

import Formatting.NaturalOrderComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Same as the series class however this class will keep all the episodes that is read through the GetSeriesInfo Class
 * in an array list
 */
public class Series {

    private final String series;
    private List<String> episodes;
    private String currentEpisode;

    public Series(String series) {
        this.series = series;
        this.episodes = new ArrayList<>();
    }

    //TODO: null checker for episodes

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

}