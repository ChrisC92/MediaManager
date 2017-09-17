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

    private List<Series> seriesList = new ArrayList<>();

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

        for (final File fileEntry : filePath.listFiles()) {
            if (fileEntry.isDirectory()) {
                initialScan(fileEntry);
            } else {
                String seriesName = fileEntry.getParentFile().getName();
                if(!isSeriesIncluded(seriesList, seriesName)){
                    Series toAdd = new Series(seriesName, fileEntry.getName());
                    seriesList.add(toAdd);
                }
            }
        }

        return seriesList;
    }

    /**
     * takes directory name and checks to see if anything in the name indicates it to be a folder
     * containing episodes to a season
     * ** if(contains "season" AND a number(ignore capitals)
     * OR Sxx (xx being some number)
     */
    private boolean isDirASeason(File file) {
        throw new NotImplementedException();
    }

    /** Iterates through the list of series and checks if the series is already contained
     *  in the list and returns true if it does
     */
    private boolean isSeriesIncluded(List<Series> seriesList, String seriesName) {
        for(Series series : seriesList) {
            if(series.getSeasonName().equals(seriesName)) {
                return true;
            }
        }
        return false;
    }


// TODO: delete this method when no longer testing file  path names
    /** Change the method to print different File paths */
    private static void printFile(File filePath) {
        String toString = filePath.toString();
        System.out.println(toString);
        File parent = filePath.getParentFile();
        System.out.println(parent.getName());
    }

    public static void main(String[] args) {
        GetSeriesInfo seriesInfo = new GetSeriesInfo();
        File file = new File("series/");
        File[] files = file.listFiles();
        List<Series> test = seriesInfo.initialScan(file);
        Collections.sort(test, new NaturalOrderComparator());
        System.out.println("size of series: " + test.size());
    }
}