package Metadata;

import Formatting.NaturalOrderComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Same as the series class however this class will keep all the episodes that is read through the GetSeriesInfo Class
 * in an array list
 */
public class Series {

    private final String series;
    private List<String> episodes;
    private String currentEp;

    public Series(String series) {
        this.series = series;
        this.episodes = new ArrayList<>();
    }

    public void addEpisode(String epName) {
        episodes.add(epName);
    }

    public void setCurrentEp(String epName) {
        boolean containsEp = false;
        for(String episode : episodes) {
            if(episode.equals(epName)) {
                currentEp = episode;
                containsEp = true;
            }
        }

        if(containsEp) {
            System.out.println("Current episode has been changed to " + epName);
        } else {
            System.out.println(epName + " is not a valid episode. Current episode has not been changed");
        }
    }

    private boolean containsEp(String epName) {
        for(String episode : episodes) {
            if(episode.equals(epName)) {
                return true;
            }
        }
        return false;
    }

    public void naturalOrdering() {
        Collections.sort(episodes, new NaturalOrderComparator());
    }

    //TODO: might be worth just making instance variables public
    public String getSeriesName() {
        return series;
    }

    public String getCurrentEp() {
        return currentEp;
    }

}
