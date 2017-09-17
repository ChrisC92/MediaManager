import static org.junit.Assert.*;

import ExtractingData.GetSeriesInfo;
import Metadata.Series;
import org.junit.Test;
import java.io.File;
import java.util.List;


/**
 * The following tests are used to check that classes take the correct and expected data from the files
 */
public class MetadataTests {

    /** When the app initially scans the folder and creates a list of different programs
     *  it should have each folder as a series object with first episode as the instance variable
     */

    /** When taking the initial data it takes the first then the last from the directories and
     *  then iterates normally through
     */
    @Test
    public void checkInitialSeasons() {
        GetSeriesInfo series = new GetSeriesInfo();
        File filePath = new File("series/");
        List<Series> listSeries = series.initialScan(filePath);
        String s01Name = listSeries.get(0).getSeasonName();
        String expS01Name = "The series of 1";
        assertEquals(expS01Name, s01Name);
        String s02Name = listSeries.get(1).getSeasonName();
        String expS02Name = "The series of 2";
        assertEquals(expS02Name, s02Name);
    }

    @Test
    public void checkInitialEpisodes() {
        GetSeriesInfo series = new GetSeriesInfo();
        File filePath = new File("series/");
        List<Series> listSeries = series.initialScan(filePath);
        String s01ep01Name = listSeries.get(0).currentEp();
        String expEpName = "episode: 1";
        assertEquals(expEpName, s01ep01Name);
        String s02e01Name = listSeries.get(1).currentEp();
        assertEquals(expEpName, s02e01Name);
    }

    //TODO: find way to save details when program closes for use at another time
}
