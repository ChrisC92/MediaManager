package ExtractingData;

import Formatting.NaturalOrderComparator;
import Metadata.Series;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.File;
import java.util.*;

/**
 * Scans the given folder and adds the details of series and first episode to a map
 */
public class GetSeriesInfo {

    private List<Series> fullSeries = new ArrayList<>();

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
     *  Takes the file path and goes through recursively through directories until it reaches a file and then looks
     *  to store new SeriesFileFormat into the fullSeries variable and if the series is already contained then add the episode
     *  into that object
     *  TODO: getSeriesInfo() could be optimised
     */
    public List<Series> getSeriesInfo(File filePath) {

        for(File fileEntry : filePath.listFiles()) {
            if(fileEntry.isDirectory()) {
                getSeriesInfo(fileEntry);
            } else {
                String seriesName = fileEntry.getParentFile().getName();
                String episodeName = fileEntry.getName();
                /**
                 * if it does not contain the series then add the series, first ep and make first ep current ep
                 * if it does contain the series add the episode to the list within the series object
                 */
                if(isSeriesInlcuded(seriesName)) {
                    addEpisode(seriesName, episodeName);
                } else {
                    Series firstSeries = new Series(seriesName);
                    firstSeries.addEpisode(episodeName);
                    firstSeries.setCurrentEp(episodeName);
                    fullSeries.add(firstSeries);
                }

            }
        }

        return fullSeries;
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


    private boolean isSeriesInlcuded(String seriesName) {
        for(Series series : fullSeries) {
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
        for(Series series : fullSeries) {
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
        for(Series series : fullSeries) {
            series.naturalOrdering();
        }
    }

    public static void main(String[] args) {
        GetSeriesInfo seriesInfo = new GetSeriesInfo();
        File file = new File("series/");
        File[] files = file.listFiles();
        Arrays.sort(files, new NaturalOrderComparator());
//        List<SeriesFileFormat> test = getSeriesInfo.initialScan(file);
        List<Series> test = seriesInfo.getSeriesInfo(file);
        seriesInfo.sortEpisodes();
        System.out.println("size of series: " + test.size());
    }
}

//TODO: The code does natural ordering for episodes however not for series since theres no arrayList of strings for series
//TODO: Create a method for sensing if this natural ordering will even be neccesary as most series files
        // have the format of S01E11
