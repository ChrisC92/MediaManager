package saveandload;

import com.thoughtworks.xstream.XStream;
import metadata.AbstractSeriesList;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


/**
 *  Uses the XStream classes to convert the object into XML that is then saved to the file
 */
public class SaveToFile {

    public static void saveSeriesToFile(AbstractSeriesList seriesList)  {
        String filePath = "savedData/savedSeriesList.txt";
        File onFile = new File(filePath);

        if(onFile.exists()) {
            onFile.delete();
        }
        XStream xStream = new XStream();
        String seriesToXML = xStream.toXML(seriesList.arrayListSeries());
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            writer.write(seriesToXML);
            writer.close();
        } catch(IOException io) {
            io.printStackTrace();
        }
    }

    public static void saveFilePathToFile(String filePath) {
        String fileName = "savedData/savedFilePath.txt";
        File onFile = new File(fileName);
        if(onFile.exists()) {
            onFile.delete();
        }
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            writer.write(filePath);
            writer.close();
        } catch(IOException io) {
            io.printStackTrace();
        }
    }

}
