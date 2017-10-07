package ExtractingData;

import Formatting.NaturalOrderComparator;
import Metadata.Series;
import Metadata.SeriesList;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.File;
import java.util.*;

/**
 * Scans the given folder and adds the details of series and first episode to a map
 */
public class GetSeriesInfo {

    private List<Series> seriesList = new ArrayList<>();
    private List<SeriesList> fullSeriesList = new ArrayList<>();

    //TODO: find name of each series and then create an object for that series and then add the first episode as the current episode

    /**
     * This method will recursively walk through given folders and list all file names
     * inside of the this folder
     */
    public static void listFiles(File folder) {
        for (final File fileEntry : folder.listFiles()) {
            if (!fileEntry.toString().equals("series/.DS_Store")) {
                if (fileEntry.isDirectory()) {
                    listFiles(fileEntry);
                } else {
                    System.out.println("folder above - " + fileEntry.getParentFile());
                    System.out.println(fileEntry.getName());
                }
            }
        }
    }

    /**
     * TODO:
     * For files walk through then for the first item file encountered make a Series object adding the file
     * then go through until it goes to another directory
     */
    //TODO: try to optimise this method
    /**
     * Creates a list of Series objects that sets the first episode in each folder as first ep in
     * series object
     */
    public List<Series> initialScan(File filePath) {

        for (File fileEntry : filePath.listFiles()) {
            if (fileEntry.isDirectory()) {
                initialScan(fileEntry);
            } else {
                String seriesName = fileEntry.getParentFile().getName();
                if(!isSeriesIncluded(seriesName)){
                    Series toAdd = new Series(seriesName, fileEntry.getName());
                    seriesList.add(toAdd);
                }
            }
        }

        return seriesList;
    }

    /**
     *  Method works similarily to the one above however adds all episodes in with the SeriesList class
     */
    public List<SeriesList> getInitialInfo(File filePath) {

        for(File fileEntry : filePath.listFiles()) {
            if(fileEntry.isDirectory()) {
                getInitialInfo(fileEntry);
            } else {
                String seriesName = fileEntry.getParentFile().getName();
                String episodeName = fileEntry.getName();
                /**
                 * if it does not contain the series then add the series, first ep and make first ep current ep
                 * if it does contain the series add the episode to the list within the series object
                 */
                if(isSeriesInlcudedList(seriesName)) {
                    addEpisode(seriesName, episodeName);
                } else {
                    SeriesList firstSeries = new SeriesList(seriesName);
                    firstSeries.addEpisode(episodeName);
                    firstSeries.setCurrentEp(episodeName);
                    fullSeriesList.add(firstSeries);
                }

            }
        }

        return fullSeriesList;
    }

    /**
     * Takes directory name and checks to see if anything in the name indicates it to be a folder
     * containing episodes to a season
     * ** if(contains "season" AND a number(ignore capitals)
     * OR Sxx (xx being some number)
     */
    private boolean isDirASeason(File file) {
        throw new NotImplementedException();
    }

    /**
     *  Iterates through the list of series and checks if the series is already contained
     *  in the list and returns true if it does
     */
    private boolean isSeriesIncluded(String seriesName) {
        for(Series series : seriesList) {
            if(series.getSeasonName().equals(seriesName)) {
                return true;
            }
        }
        return false;
    }

    private boolean isSeriesInlcudedList(String seriesName) {
        for(SeriesList series : fullSeriesList) {
            if(series.getSeriesName().equals(seriesName)) {
                return true;
            }
        }
        return false;
    }

    /**
     *  Iterates through the list of series and adds the episode to the correct series
     */
    private void addEpisode(String seriesName, String episodeName) {
        for(SeriesList series : fullSeriesList) {
            if(series.getSeriesName().equals(seriesName)) {
                series.addEpisode(episodeName);
            }
        }
    }


// TODO: delete this method when no longer testing file  path names
    /** Change the method to print different File paths */
    private static void printFile(File filePath) {
        String toString = filePath.toString();
        System.out.println(toString);
        File parent = filePath.getParentFile();
        System.out.println(parent.getName());
    }

    private void sortEpisodes() {
        for(SeriesList series : fullSeriesList) {
            series.naturalOrdering();
        }
    }

    public static void main(String[] args) {
        GetSeriesInfo seriesInfo = new GetSeriesInfo();
        File file = new File("series/");
        File[] files = file.listFiles();
        Arrays.sort(files, new NaturalOrderComparator());
//        List<Series> test = seriesInfo.initialScan(file);
        List<SeriesList> test = seriesInfo.getInitialInfo(file);
        seriesInfo.sortEpisodes();
        System.out.println("size of series: " + test.size());
    }
}

//TODO: The code does natural ordering for epsisodes however not for series since theres no arrayList of strings for series