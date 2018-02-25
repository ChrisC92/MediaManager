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
        this.episodes = new ArrayList<>();
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

    private boolean containsEp(String epName) {
        for (SimpleStringProperty episode : episodes) {
            if (episode.get().equals(epName)) {
                return true;
            }
        }
        return false;
    }

    public void sortEpisodes() {
        Collections.sort(episodes, new NaturalOrderComparator());
    }

    public ArrayList<SimpleStringProperty> getEpisodes() {
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

    public boolean currentEpEquals(Series compare) {
        if (this.currentEpisode.equals(compare.currentEpisode)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Series)) return false;
        Series series1 = (Series) o;
        return Objects.equals(series.get(), series1.series.get());
    }
}