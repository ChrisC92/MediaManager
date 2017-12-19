package metadata;

import formattingAndOrdering.NaturalOrderComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Same as the series class however this class will keep all the episodes that is read through the SeriesList Class
 * in an array list
 */
public class Series implements java.io.Serializable {

    private final String series;
    private List<String> episodes;
    private String currentEpisode;

    public Series(String series) {
        this.series = series;
        this.episodes = new ArrayList<>();
    }

    public void addEpisode(String epName) {
        if (episodes.isEmpty()) {
            currentEpisode = epName;
        }
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

    public void sortEpisodes() {
        Collections.sort(episodes, new NaturalOrderComparator());
    }

    public String getName() {
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

    public void setCurrentEpisode(int index) {
        currentEpisode = episodes.get(index);
    }


    public void printEpisodes() {
        int index = 1;
        System.out.println("Current episode: " + currentEpisode);
        for (String episode : episodes) {
            System.out.println(index + ". " + episode);
            index += 1;
        }
    }

    public boolean currentEpEquals(Series compare) {
        if (this.currentEpisode.equals(compare.currentEpisode)) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return series;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Series)) return false;
        Series series1 = (Series) o;
        return Objects.equals(series, series1.series);
    }

}