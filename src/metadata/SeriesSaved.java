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
        super(SeriesSaved.deserialize(fileName).getSeriesList());
    }

    public static AbstractSeriesList deserialize(String fileName) {
        AbstractSeriesList seriesList = new SeriesOnFile();
        File file = new File("savedData/storedSeriesList.ser");
        if (file.exists()) {
            try {
                FileInputStream fileIn = new FileInputStream(fileName);
                ObjectInputStream in = new ObjectInputStream(fileIn);
                seriesList = (AbstractSeriesList) in.readObject();
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

    private static ObservableList<Series> createSeriesList() {
        ObservableList<Series> seriesList = FXCollections.observableArrayList();
        return seriesList;
    }
}
