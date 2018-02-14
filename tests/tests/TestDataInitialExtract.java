package tests;

import metadata.AbstractSeriesList;
import metadata.Series;
import metadata.SeriesOnFile;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.junit.Assert.assertEquals;


/**
 * The following tests are used to check that classes take the correct and expected data from the files
 */
public class TestDataInitialExtract {

    /** When the app initially scans the folder and creates a list of different programs
     *  it should have each folder as a series object with first episode as the instance variable
     */

    /** When taking the initial data it takes the first then the last from the directories and
     *  then iterates normally through
     */
    @Test
    public void checkInitialSeasons() {
        File filePath = new File("series/");
        boolean test = filePath.exists();
        AbstractSeriesList series = new SeriesOnFile(filePath);
        List<Series> listSeries = series.getSeriesList();
        String s01Name = listSeries.get(0).getName();
        String expS01Name = "The series of 1";
        assertEquals(expS01Name, s01Name);
        String s02Name = listSeries.get(1).getName();
        String expS02Name = "The series of 10";
        assertEquals(expS02Name, s02Name);
    }

    @Test
    public void checkInitialEpisodes() {
        File filePath = new File("series/");
        AbstractSeriesList series = new SeriesOnFile(filePath);
        List<Series> listSeries = series.getSeriesList();
        String s01ep01Name = listSeries.get(0).getCurrentEpisode();
        String expEpName = "episode: 1";
        assertEquals(expEpName, s01ep01Name);
        String s02e01Name = listSeries.get(1).getCurrentEpisode();
        assertEquals(expEpName, s02e01Name);
    }
}
