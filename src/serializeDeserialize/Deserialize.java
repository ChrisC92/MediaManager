package serializeDeserialize;

import metadata.SeriesList;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class Deserialize {

    public static SeriesList Deserialize(String fileName) {
        SeriesList seriesList = null;
        try {
            FileInputStream fileIn = new FileInputStream(fileName);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            seriesList = (SeriesList) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
        } catch (ClassNotFoundException c) {
            System.out.println("SeriesList class not found");
            c.printStackTrace();
        }
        return seriesList;
    }
}
