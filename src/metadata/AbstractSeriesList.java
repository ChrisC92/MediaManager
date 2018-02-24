package metadata;

import com.fasterxml.jackson.annotation.JsonProperty;
import ordering.SeriesNatOrderComparator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Abstract class for the SeriesList, with all base components the subclasses alter the ways in which the data is retrieved
 * to populate the List
 */
public abstract class AbstractSeriesList implements Serializable {
    @JsonProperty("SeriesList")
    private transient ObservableList<Series> seriesList;

    protected AbstractSeriesList() {
        seriesList = FXCollections.observableArrayList();
    }

    protected AbstractSeriesList(ObservableList<Series> seriesList) {
        this.seriesList = seriesList;
    }

    public ObservableList<Series> getSeriesList() {
        return seriesList;
    }

    public int size() {
        return seriesList.size();
    }

    public void sortSeries() {
        Collections.sort(seriesList, new SeriesNatOrderComparator());
    }

    public void printSeriesList() {
        int i = 1;
        for (Series series : seriesList) {
            System.out.println(i + ": " + series.getName());
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
        sortSeries();
    }

    public void removeSeries(int index) {
        seriesList.remove(index);
    }

    /**
     * Combines and returns on SeriesList when given the list saved on file and the file extracted
     */
    public static AbstractSeriesList combineSeries(AbstractSeriesList seriesSaved, AbstractSeriesList onFile) {
        AbstractSeriesList allListCopy = new SeriesOnFile(onFile);
        if (!(seriesSaved.isEmpty())) {
            allListCopy.getSeriesList().removeAll(seriesSaved.getSeriesList());
            allListCopy.getSeriesList().addAll(seriesSaved.getSeriesList());
            allListCopy.sortSeries();
            Collections.sort(allListCopy.getSeriesList(), new SeriesNatOrderComparator());
        }
        return allListCopy;
    }

    public boolean containsSeries(String seriesName) {
        for (Series series : seriesList) {
            if (series.getName().equals(seriesName)) {
                return true;
            }
        }
        return false;
    }

    public boolean isEmpty() {
        if (seriesList.size() == 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof AbstractSeriesList)) {
            return false;
        }
        AbstractSeriesList compare = (AbstractSeriesList) other;
        if (this.size() != compare.size()) {
            return false;
        }
        for (Series series : seriesList) {
            if (!(compare.containsSeries(series.getName()))) {
                return false;
            }
        }
        return true;
    }

    private List<Series> arrayListSeries() {
        List<Series> toReturn = new ArrayList<>();

        for (Series series : seriesList) {
            toReturn.add(series);
        }
        return toReturn;
    }

    public void setSeriesList(ObservableList<Series> seriesList) {
        this.seriesList = seriesList;
    }

    public static void serializeList(AbstractSeriesList seriesList, String fileName) {
        try {
            FileOutputStream file = new FileOutputStream(fileName);
            ObjectOutputStream out = new ObjectOutputStream(file);

            out.reset();
            out.writeObject(seriesList);
            out.close();
            file.close();
        } catch (IOException ex) {
            System.out.println("IO Exception has been caught");
            ex.printStackTrace();
        }
    }


}
