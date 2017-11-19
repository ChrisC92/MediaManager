package tests;

import extractingData.SeriesList;
import metadata.Series;
import org.junit.Test;

import static org.junit.Assert.*;

import java.io.File;
import java.util.List;

/**
 * This goes through the files in my own series folder and goes through the following tests
 */
public class RealInitialMetadataTests {
    //TODO: Find a better way of dealing with the case which happens with a different folder depth
         // with the IT Crowd files
    @Test
    public void checkFilePath() {
        File filePath = new File("/Users/ChrisCorner/Documents/Films_Series/Series");
        String failString = "The file path does not exist";
        assertEquals(failString, true, filePath.exists());
    }

    @Test
    public void checkSeriesNames() {
        File filePath = new File("/Users/ChrisCorner/Documents/Films_Series/Series");
        SeriesList seriesInfo = new SeriesList(filePath);
        List<Series> seriesList = seriesInfo.getSeriesList();
        seriesInfo.printSeriesNames();
        String expectedS01 = "Adventure Time - Season 7";
        String actualS01 = seriesList.get(0).getSeriesName();
        assertEquals(expectedS01, actualS01);

        String expectedS03 = "Adventure Time S08.2 (360p re-webrip)";
        String actualS03 = seriesList.get(1).getSeriesName();
        assertEquals(expectedS03, actualS03);

        String expectedS02 = "Adventure Time - Season 8";
        String actualS02 = seriesList.get(2).getSeriesName();
        assertEquals( expectedS02, actualS02);

    }

    @Test
    public void checkEpisodes() {
        File filePath = new File("/Users/ChrisCorner/Documents/Films_Series/Series");
        SeriesList seriesInfo = new SeriesList(filePath);
        List<Series> seriesList = seriesInfo.getSeriesList();

        String expectedS01E01 = "S07E01-Bonnie & Neddy.mp4";
        String expectedS01E02 = "S07E02-Varmints.mp4";
        String expectedS01E06 = "S07E06-Marceline The Vampire Queen (1).mp4";
        String actualS01E01 = seriesList.get(0).getCurrentEpisode();
        String actualS01E02 = seriesList.get(0).getEpisode(1);
        String actualS01E06 = seriesList.get(0).getEpisode(5);

        assertEquals(expectedS01E01, actualS01E01);
        assertEquals(expectedS01E02, actualS01E02);
        assertEquals(expectedS01E06, actualS01E06);

        String expectedS03E01 = "Adventure Time S08E15 Orb.mp4";
        String actualS03E01 = seriesList.get(1).getCurrentEpisode();
        assertEquals(expectedS03E01, actualS03E01);

        String expectedS02E01 = "Adventure Time with Finn and Jake - S08E01 - Two Swords (720p.WEB-DL.AAC2.0.H.264) (NTb).mp4-c.mp4";
        String actualS02E01 = seriesList.get(2).getCurrentEpisode();
        assertEquals(expectedS02E01, actualS02E01);


    }


}
