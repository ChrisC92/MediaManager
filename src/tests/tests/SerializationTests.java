package tests;

import metadata.AbstractSeriesList;
import metadata.Series;
import metadata.SeriesOnFile;
import metadata.SeriesSaved;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
        AbstractSeriesList seriesList = new SeriesOnFile(path);
        String fileName = "savedData/storedSeriesList.ser";
        seriesList.serializeList(seriesList, fileName);
        File seriesFile = new File("savedData/storedSeriesList.ser");
        assertEquals(seriesFile.exists(), true);
    }

    @Test
    public void deserializeTest() {
        File path = new File("/Users/ChrisCorner/Documents/Films_Series/Series");
        AbstractSeriesList seriesList = new SeriesOnFile(path);
        String fileName = "savedData/storedSeriesList.ser";
        seriesList.serializeList(seriesList, fileName);
        AbstractSeriesList deserialize = new SeriesSaved(fileName);
        String list1Series1 = seriesList.getSeries(0).getName();
        String list2Series1 = deserialize.getSeries(0).getName();
        assertEquals(list1Series1, list2Series1);
        String list1S01E01 = seriesList.getSeries(0).getCurrentEpisode();
        String list2S01E01 = deserialize.getSeries(0).getCurrentEpisode();
        assertEquals(list1S01E01, list2S01E01);
        String list1Series2 = seriesList.getSeries(1).getName();
        String list2Series2 = deserialize.getSeries(1).getName();
        assertEquals(list1Series2, list2Series2);
    }

    @Test
    public void addExtraSeries() {
        File path = new File("/Users/ChrisCorner/Documents/Films_Series/Series");
        AbstractSeriesList seriesList = new SeriesOnFile(path);
        String fileName = "savedData/storedSeriesList.ser";
        AbstractSeriesList.serializeList(seriesList, fileName);
        AbstractSeriesList deserialize = new SeriesSaved(fileName);
        Series addExtraStartTest = new Series("*AAAA");
        addExtraStartTest.addEpisode("pilot");
        Series addExtraEndTest = new Series("ZaddExtra test at end");
        addExtraEndTest.addEpisode("pilot");
        deserialize.addSeries(addExtraEndTest);
        deserialize.addSeries(addExtraStartTest);
    }

    @Test
    public void deserializeEmptyFile() {
        String fileName = "savedData/storedSeriesList.ser";
        File file = new File(fileName);
        file.delete();
        AbstractSeriesList seriesOnFile = new SeriesSaved(fileName);
        assertTrue(seriesOnFile.isEmpty());
    }
}
