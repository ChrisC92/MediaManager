package storageAndExtraction;

import metadata.SeriesList;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class Serialize {


    public static void serializeList(SeriesList seriesList, String fileName) {
        File checkFile = new File(fileName);
        if (checkFile.exists()) {
            checkFile.delete();
        }
        try {
            FileOutputStream file = new FileOutputStream(fileName);
            ObjectOutputStream out = new ObjectOutputStream(file);
            out.writeObject(seriesList);
            out.close();
            file.close();
        } catch (IOException ex) {
            System.out.println("IO Exception has been caught");
            ex.printStackTrace();
        }
    }
}

