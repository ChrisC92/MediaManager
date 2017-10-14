package Interface;

import Metadata.Series;

import java.util.ArrayList;
import java.util.List;

public class Menus {

    public static void createMainMenu(List<Series> seriesList) {
        System.out.println("Media Manager");
        //TODO: list all the seasons that are within the given folder
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

    }
}
