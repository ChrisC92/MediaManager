package menuControls;

import extractingData.GetSeriesInfo;
import metadata.Series;

import java.io.File;
import java.util.List;
import java.util.Scanner;

/**
 *  Class contains all Menues to navigate through the different commands of the program
 */
public class Menus {

    //TODO: ability to delete from folders (artifically for the app and not actually delete the files
    public static void initialMenu(Scanner kb) {
        System.out.println("Media Manager");
        System.out.println("This takes the file path for where your series files are listed");
        System.out.println("And allows for multiple operations on the files including");
        System.out.println("tracking current episode and playing them through a chosen program");
        System.out.println(("(its default will VLC however you can change this"));

        System.out.println("\nPlease copy and paste the file path to the top level of your series");
        System.out.println("folder, for example mines is: ");
        System.out.println("/Users/ChrisCorner/Documents/Films_Series/Series");
        String stringPath = kb.nextLine();

        System.out.println("This will now list the series and confirm wether this has extracted");
        System.out.println("the data correctly - ");
        File filePath = new File(stringPath);
        GetSeriesInfo seriesInfo = new GetSeriesInfo(filePath);
        seriesInfo.printSeriesNames();
    }

    public static void createMainMenu(List<Series> seriesList) {
        System.out.println("Media Manager");
        System.out.println("\nList of SeriesFileFormat: ");
        System.out.println("Enter the number of the season you want to watch, it will bring a list of episodes");
        System.out.println("If u just want to play from current episode type the number then ' p'");

    }


    public static void firstOpen() {
        System.out.println("Media manager is a tool to track you stored tv shows and better controls over");
        System.out.println("choosing epsiodes and tracking where you are in a series");
        System.out.println("\nFirst can you give the file path of where your series folder is?");
        System.out.println("(This would be the absolute path, for example mines is - ");
        System.out.println("/Users/ChrisCorner/Documents/Films:SeriesFileFormat/SeriesFileFormat");
        //TODO: outwith this method where the data will actually be stored will take the user input
    }


    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        initialMenu(kb);
    }
}
