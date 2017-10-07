package Interface;

import Metadata.SeriesFileFormat;

import java.util.ArrayList;
import java.util.List;

public class Menus {

    public static void createMainMenu(List<SeriesFileFormat> seriesList) {
        System.out.println("Media Manager");
        //TODO: list all the seasons that are within the given folder
        System.out.println("\nList of SeriesFileFormat: ");
        System.out.println("Enter the number of the season you want to watch, it will bring a list of episodes");
        System.out.println("If u just want to play from current episode type the number then ' p'");
        for(int i=1; i<=seriesList.size(); i++) {
            System.out.println(i + ". " + seriesList.get(i-1).getSeasonName());
        }

    }


    public static void firstOpen() {
        System.out.println("Media manager is a tool to track you stored tv shows and better controls over");
        System.out.println("choosing epsiodes and tracking where you are in a series");
        System.out.println("\nFirst can you give the file path of where your series folder is?");
        System.out.println("(This would be the absolute path, for example mines is - ");
        System.out.println("/Users/ChrisCorner/Documents/Films:SeriesFileFormat/SeriesFileFormat");
        //TODO: outwith this method where the data will actually be stored will take the user input
    }

    public static void test() {
        SeriesFileFormat series1 = new SeriesFileFormat("season1", "ep1");
        SeriesFileFormat series2 = new SeriesFileFormat("season2", "ep2");
        SeriesFileFormat series3 = new SeriesFileFormat("season3", "ep3");
        List<SeriesFileFormat> series = new ArrayList<>();
        series.add(series1);
        series.add(series2);
        series.add(series3);
        createMainMenu(series);
    }

    public static void main(String[] args) {
        test();
    }
}
