package tests;

import extractingData.extractData;
import metadata.Series;
import metadata.SeriesList;
import org.junit.Test;
import serializeDeserialize.Deserialize;
import serializeDeserialize.Serialize;

import static org.junit.Assert.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *  Tests for both Serializing and Deserializing the SeriesList Object
 *
 *  The goals of serializing will also be to create two SeriesLists -
 *      - one that holds only what is stored on the computer at this point in time
 *      - second to hold even data for series that have been deleted
 *
 */
public class SerializationTests {


    @Test
    public void serializeTest() {
        File path = new File("/Users/ChrisCorner/Documents/Films_Series/Series");
        SeriesList seriesList = new SeriesList(path);
        String fileName = "savedData/storedSeriesList.ser";
        Serialize.serializeList(seriesList, fileName);
        File seriesFile = new File("savedData/storedSeriesList.ser");
        assertEquals(seriesFile.exists(), true);
    }

    @Test
    public void deserializeTest() {
        File path = new File("/Users/ChrisCorner/Documents/Films_Series/Series");
        SeriesList seriesList = new SeriesList(path);
        String fileName = "savedData/storedSeriesList.ser";
        Serialize.serializeList(seriesList, fileName);
        SeriesList deserialize = Deserialize.Deserialize(fileName);
        String list1Series1 = seriesList.getSeries(0).getSeriesName();
        String list2Series1 = deserialize.getSeries(0).getSeriesName();
        assertEquals(list1Series1, list2Series1);
        String list1S01E01 = seriesList.getSeries(0).getCurrentEpisode();
        String list2S01E01 = deserialize.getSeries(0).getCurrentEpisode();
        assertEquals(list1S01E01, list2S01E01);
        String list1Series2 = seriesList.getSeries(1).getSeriesName();
        String list2Series2 = deserialize.getSeries(1).getSeriesName();
        assertEquals(list1Series2, list2Series2);
    }

    @Test
    public void addExtraSeries() {
        File path = new File("/Users/ChrisCorner/Documents/Films_Series/Series");
        SeriesList seriesList = new SeriesList(path);
        String fileName = "savedData/storedSeriesList.ser";
        Serialize.serializeList(seriesList, fileName);
        SeriesList deserialize = Deserialize.Deserialize(fileName);
        Series addExtraStartTest = new Series("*AAAA", "pilot");
        Series addExtraEndTest = new Series("ZaddExtra test at end", "pilot");
        deserialize.addSeries(addExtraEndTest);
        deserialize.addSeries(addExtraStartTest);
        deserialize.printSeriesList();


    }

    @Test
    public void removeSeries() {

    }
}
