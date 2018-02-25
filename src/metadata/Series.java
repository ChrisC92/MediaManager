package metadata;

import ordering.NaturalOrderComparator;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Series implements java.io.Serializable {
    private SimpleStringProperty series;
    private ObservableList<SimpleStringProperty> episodes;
    private SimpleStringProperty currentEpisode;

    public Series(String series) {
        this.series = new SimpleStringProperty(series);
        this.episodes = FXCollections.observableArrayList();
    }

    public void addEpisode(String epName) {
        if (episodes.isEmpty()) {
            currentEpisode = new SimpleStringProperty(epName);
        }
        episodes.add(new SimpleStringProperty(epName));
    }

    public void initialCurrentEpAssign(String epName) {
        currentEpisode = new SimpleStringProperty(epName);
    }

    public void sortEpisodes() {
        Collections.sort(episodes, new NaturalOrderComparator());
    }

    public ObservableList<SimpleStringProperty> getEpisodes() {
        return episodes;
    }

    public String getName() {
        return series.get();
    }

    public String getCurrentEpisode() {
        return currentEpisode.get();
    }

    public String getEpisode(int index) {
        if (index >= episodes.size() || index < 0) {
            System.out.println("Index in valid, returning current episode");
            return currentEpisode.get();
        }
        return episodes.get(index).get();
    }


    /**
     * This method can be used for when the user wants to choose an episode
     */
    public void setCurrentEpisode(String epName) {
        boolean containsEp = false;
        for (SimpleStringProperty episode : episodes) {
            if (episode.get().equals(epName)) {
                currentEpisode = episode;
                containsEp = true;
            }
        }
    }

    public void setCurrentEpisode(int index) {
        currentEpisode = episodes.get(index);
    }


    public void printEpisodes() {
        int index = 1;
        System.out.println("Current episode: " + currentEpisode);
        for (StringProperty episode : episodes) {
            System.out.println(index + ". " + episode);
            index += 1;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Series)) return false;
        Series series1 = (Series) o;
        return Objects.equals(series.get(), series1.series.get());
    }
}