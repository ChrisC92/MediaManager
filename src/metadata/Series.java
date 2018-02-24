package metadata;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("Series")
    private transient SimpleStringProperty series;
    @JsonProperty("EpisodesList")
    private transient ObservableList<SimpleStringProperty> episodes;
    @JsonProperty("CurrentEpisode")
    private transient SimpleStringProperty currentEpisode;

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

    public boolean currentEpEquals(Series compare) {
        if (this.currentEpisode.equals(compare.currentEpisode)) {
            return true;
        }
        return false;
    }

    /**
    @Override
    public String toString() {
        return series.get();
    }
     TODO: might need to delete */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Series)) return false;
        Series series1 = (Series) o;
        return Objects.equals(series.get(), series1.series.get());
    }

    /**
     * Performs custom serialization of this instance.
     * Automatically is invoked by Java when this instance is serialized
     */
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();

        out.writeObject(series.get());
        List<String> toSerialize = episodesAsStrings();
        out.writeObject(toSerialize);
        out.writeObject(currentEpisode.get());
    }


    private List<String> episodesAsStrings() {
        List<String> toReturn = new ArrayList<>();

        for (StringProperty episode : episodes) {
            toReturn.add(episode.get());
        }
        return toReturn;
    }

    /**
     * Performs custom deserialization of this instance
     * Automatically is invoked by Java during deserialization
     */
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();

        series = new SimpleStringProperty((String) in.readObject());
        List<String> episodeStrings =  (List<String>)in.readObject();
        episodes = episodesAsSimpleStrings(episodeStrings);
        currentEpisode = new SimpleStringProperty((String) in.readObject());
    }

    private ObservableList<SimpleStringProperty> episodesAsSimpleStrings(List<String> episodeStrings) {
        ObservableList<SimpleStringProperty> episodesReturn = FXCollections.observableArrayList();
        for(String episode : episodeStrings) {
            episodesReturn.add(new SimpleStringProperty(episode));
        }
        return episodesReturn;
    }

}