package metadata;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class SeriesSaved extends AbstractSeriesList {

    public SeriesSaved(String fileName) {
        super(fileName);
    }

    public static SeriesOnFile deserialize(String fileName) {
        SeriesOnFile seriesList = new SeriesOnFile();
        File file = new File("savedData/storedSeriesList.ser");
        if (file.exists()) {
            try {
                FileInputStream fileIn = new FileInputStream(fileName);
                ObjectInputStream in = new ObjectInputStream(fileIn);
                seriesList = (SeriesOnFile) in.readObject();
                in.close();
                fileIn.close();
            } catch (IOException i) {

                i.printStackTrace();
            } catch (ClassNotFoundException c) {
                System.out.println("SeriesList class not found");
                c.printStackTrace();
            }
        }
        return seriesList;
    }
}
