package tests;

import metadata.Series;
import metadata.SeriesList;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class TestDataSeriesList {


    @Test
    public void testEqualsMethod() {
        File filePath = new File("series/");
        SeriesList seriesSaved = new SeriesList(filePath);
        SeriesList seriesSavedCOPY = new SeriesList(filePath);
        SeriesList biggerList = biggerListSimple();
        SeriesList smallerList = smallerListSimple();
        SeriesList sameSizeList = sameSizeListSimple();

        assertTrue(seriesSaved.equals(seriesSavedCOPY));
        assertTrue(seriesSaved.equals(seriesSaved));
        assertFalse(seriesSaved.equals(biggerList));
        assertFalse(seriesSaved.equals(smallerList));
        assertFalse(seriesSaved.equals(sameSizeList));
    }



    @Test
    public void combineBiggerFileSimple() {
        File filePath = new File("series/");
        SeriesList seriesSaved = new SeriesList(filePath);
        SeriesList extractedSeries = biggerListSimple();
        // combinedList will replace seriesSaved but for now it won't
        SeriesList combinedList = SeriesList.combineSeries(seriesSaved, extractedSeries);

        // comparing extractedSeries with seriesSaved
        assertEquals(15, combinedList.size());

        String seriesCheck1 = extractedSeries.getSeries(0).getName();
        String seriesCheck2 = extractedSeries.getSeries(3).getName();
        String seriesCheck3 = extractedSeries.getSeries(8).getName();
        String seriesCheck4 = extractedSeries.getSeries(13).getName();
        assertTrue(combinedList.containsSeries(seriesCheck1));
        assertTrue(combinedList.containsSeries(seriesCheck2));
        assertTrue(combinedList.containsSeries(seriesCheck3));
        assertTrue(combinedList.containsSeries(seriesCheck4));

        assertTrue(extractedSeries.equals(combinedList));
    }


    @Test
    public void combineSmallerFileSimple() {
        File filePath = new File("series/");
        SeriesList seriesSaved = new SeriesList(filePath);
        SeriesList extractedSeries = smallerListSimple();
        SeriesList combinedList = SeriesList.combineSeries(seriesSaved, extractedSeries);
        // comparing extractedSeries with seriesSaved
        assertTrue(seriesSaved.size() == combinedList.size());

        assertTrue(seriesSaved.equals(combinedList));
    }

    @Test
    public void combineSameSizeFile() {
        File filePath = new File("series/");
        SeriesList seriesSaved = new SeriesList(filePath);
        SeriesList extractedSeries = sameSizeListSimple();
        //SeriesList combinedList = SeriesList.combineSeries(extractedSeries, seriesSaved);
        SeriesList combinedList = SeriesList.combineSeries(seriesSaved, extractedSeries);

        assertTrue(combinedList.size()==14);

        String expected1 = "The series of 1";
        String expected6 = "The series of 6";
        String expected14 = "The series of 101";
        String combined1 = combinedList.getSeries(0).getName();
        String combined6 = combinedList.getSeries(5).getName();
        String combined14 = combinedList.getSeries(13).getName();
        assertEquals(expected1, combined1);
        assertEquals(expected6, combined6);
        assertEquals(expected14, combined14);
    }

    @Test
    public void combineWithDifferentCurrentEpisode() {
        File filePath = new File("series/");
        SeriesList seriesSaved = changeCurrentEpisode();
        SeriesList extractedSeries = new SeriesList(filePath);
        SeriesList combinedList = SeriesList.combineSeries(seriesSaved, extractedSeries);

        String episodeCheck1 = "episode: 3";
        String combineCheck1 = combinedList.getSeries(0).getCurrentEpisode();
        String episodeCheck2 = "episode: 5";
        String combineCheck2 = combinedList.getSeries(3).getCurrentEpisode();

        assertEquals(episodeCheck1, combineCheck1);
        assertEquals(episodeCheck2, combineCheck2);

        assertTrue(combinedList.equals(seriesSaved));
    }

    @Test
    public void combineComplex() {
        File filePath = new File("series/");
        SeriesList seriesSaved = changeCurrentEpisode();
        SeriesList extractedSeries = sameSizeListSimple();
        SeriesList combinedList = SeriesList.combineSeries(seriesSaved, extractedSeries);

        String seriesCheck1A = "The series of 1";
        String seriesCheck1B = combinedList.getSeries(0).getName();
        String seriesCheck2A = "The series of 4";
        String seriesCheck2B = combinedList.getSeries(3).getName();
        String seriesCheck3A = "The series of 13";
        String seriesCheck3B = combinedList.getSeries(12).getName();
        assertEquals(seriesCheck1A, seriesCheck1B);
        assertEquals(seriesCheck2A, seriesCheck2B);
        assertEquals(seriesCheck3A, seriesCheck3B);

        String epCheck1A = "episode: 3";
        String epCheck1B = combinedList.getSeries(0).getCurrentEpisode();
        String epCheck2A = "episode: 5";
        String epCheck2B = combinedList.getSeries(3).getCurrentEpisode();
        String epCheck3A = "the pilot of 101";
        String epCheck3B = combinedList.getSeries(13).getCurrentEpisode();
        assertEquals(epCheck1A, epCheck1B);
        assertEquals(epCheck2A, epCheck2B);
        assertEquals(epCheck3A, epCheck3B);
    }

    private static SeriesList biggerListSimple() {
        File filePath = new File("series/");
        SeriesList seriesList = new SeriesList(filePath);

        Series toAdd1 = new Series("The series of 101");
        Series toAdd2 = new Series("The series of 13");
        Series toAdd3 = new Series("Am at the front");
        toAdd1.addEpisode("the pilot to 101");
        toAdd2.addEpisode("the pilot to 13");
        toAdd3.addEpisode("front pilot");

        seriesList.getSeries(0).setCurrentEpisode(3);
        //Expected Series Of 1 current ep = episode:4
        seriesList.getSeries(2).setCurrentEpisode(5);
        //Expected Series Of 3 current ep = episode 6

        seriesList.addSeries(toAdd1);
        seriesList.addSeries(toAdd2);
        seriesList.addSeries(toAdd3);


        return seriesList;
    }

    private static SeriesList smallerListSimple() {
        File filePath = new File("series/");
        SeriesList seriesList = new SeriesList(filePath);

        seriesList.removeSeries(0);
        seriesList.removeSeries(3);
        return seriesList;
    }

    private static SeriesList sameSizeListSimple() {
        File filePath = new File("series/");
        SeriesList seriesList = new SeriesList(filePath);

        Series toAdd1 = new Series("The series of 101");
        Series toAdd2 = new Series("The series of 13");
        toAdd1.addEpisode("the pilot of 101");
        toAdd2.addEpisode("the pilot of 13");

        seriesList.removeSeries(1);
        seriesList.removeSeries(4);
        seriesList.addSeries(toAdd1);
        seriesList.addSeries(toAdd2);

        return seriesList;
    }

    private static SeriesList changeCurrentEpisode() {
        File filePath = new File("series/");
        SeriesList seriesList = new SeriesList(filePath);

        seriesList.getSeries(0).setCurrentEpisode(2);
        seriesList.getSeries(3).setCurrentEpisode(4);
        seriesList.getSeries(8).setCurrentEpisode(8);
        return seriesList;

    }
}
