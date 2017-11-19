package uiCommandLine;

import extractingData.SeriesList;
import metadata.Series;

import java.io.File;
import java.util.Scanner;

/**
 * Class contains all Menu's to navigate through the different commands of the program
 */
public class Menus {
    public static SeriesList getFilePath(Scanner kb) {
        System.out.println("Media Manager");
        System.out.println("This takes the file path for where your series files are listed");
        System.out.println("And allows for multiple operations on the files including");
        System.out.println("tracking current episode and playing them through VLC");

        System.out.println("\nPlease copy and paste the file path to the top level of your series");
        System.out.println("folder, for example mines is: ");
        System.out.println("/Users/ChrisCorner/Documents/Films_Series/Series");
        String stringPath = "/Users/ChrisCorner/Documents/Films_Series/Series";

        //String stringPath = kb.nextLine();

        System.out.println("This will now list the series and confirm whether this has extracted");
        System.out.println("the data correctly - ");
        File filePath = new File(stringPath);
        if (filePath.exists()) {
            SeriesList seriesInfo = new SeriesList(filePath);
            seriesInfo.printSeriesNames();
            System.out.println("Has the data been extracted correctly? \n 'yes or no'");
            String userConfirm = "yes";
            //String userConfirm = kb.nextLine();
            if (userConfirm.equals("yes")) {
                return seriesInfo;
            } else {
                System.out.println("Restarting...");
                getFilePath(kb);
            }
        }
        // return is redundant it can't reach this part of the code
        return null;
    }

    public static void mainMenuUI(SeriesList seriesList, Scanner kb) {
        System.out.println("Media Manager");
        System.out.println("\nList of SeriesFileFormat: ");
        System.out.println("Enter the number of the season you want to watch, it will bring a list of episodes");
        seriesList.printSeriesNames();
        System.out.println("Please enter a number");
        int userInput = userInputNumber(kb);
        while (userInput <= 0 || userInput >= seriesList.seriesListSize()) {
            System.out.println("Number is outwith the valid range please re-enter");
            userInput = userInputNumber(kb);
        }
        Series series = seriesList.getSeries(userInput);
        episodeList(series, kb);
        episodeChoice(seriesList, kb);
    }

    public static void episodeList(Series series, Scanner kb) {
        series.printEpisodes();
        System.out.println("Type index number on the left to select an episode or 'current' to play");
        System.out.println("the current episode, typing 'return' will return to the main menu");
    }

    private static int userInputNumber(Scanner kb) {
        while (!kb.hasNextInt()) {
            kb.nextLine();
        }
        return kb.nextInt();
    }

    private static void episodeChoice(SeriesList seriesList, Scanner kb) {
        if (kb.hasNextInt()) {
            //TODO: print the episode name when selected
            System.out.println("playing episode chosen");
        } else if (kb.next().equals("current")) {
            System.out.println("current episode will now be played");
        } else if(kb.next().equals("return")) {
            System.out.println("return to the main menu");
            mainMenuUI(seriesList, kb);
        } else {
            System.out.println("input not recognised please retry");
            episodeChoice(seriesList, kb);
        }
    }


    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        File file = new File("/Users/ChrisCorner/Documents/Films_Series/Series");
        SeriesList seriesList = new SeriesList(file);
        episodeChoice(seriesList, kb);
    }

}
