package xStream;


import com.thoughtworks.xstream.XStream;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import metadata.AbstractSeriesList;
import metadata.Series;
import metadata.SeriesOnFile;
import metadata.SeriesSaved;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 *  Takes the file path and loads the text from the file and converts to designated format
 */
public class LoadFromFile {

    public static AbstractSeriesList loadSeriesList() {
        String filePath = "savedData/savedSeriesList.txt";
        File file = new File(filePath);
        if (file.exists()) {
            String XMLSeries = stringFromFile(filePath);
            XStream xStream = new XStream();
            List<Series> arraySeries = (List<Series>) xStream.fromXML(XMLSeries);
            AbstractSeriesList seriesList = new SeriesOnFile(FXCollections.observableList(arraySeries));
            return seriesList;
        } else {
            AbstractSeriesList seriesList = new SeriesOnFile();
            return seriesList;
        }
    }


    public static String loadSeriesFilePath() {
        String filePath = "savedData/savedFilePath.txt";
        return stringFromFile(filePath);
    }

    private static String stringFromFile(String filePath) {
        StringBuilder stringFromFile = new StringBuilder();
        String line;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));

            while((line = reader.readLine()) != null) {
                stringFromFile.append(line);
            }
            reader.close();
        } catch(IOException io) {
            io.printStackTrace();
        }
        return stringFromFile.toString();
    }



}
