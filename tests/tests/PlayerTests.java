package tests;

import metadata.AbstractSeriesList;
import metadata.SeriesOnFile;

import java.io.File;


public class PlayerTests {

    public static void main(String[] args) {
        File filePath = new File("/Users/ChrisCorner/Documents/Films_Series/Series");
        AbstractSeriesList seriesList = new SeriesOnFile(filePath);

    }

}
