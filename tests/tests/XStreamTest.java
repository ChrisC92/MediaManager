package tests;


import com.thoughtworks.xstream.XStream;
import metadata.AbstractSeriesList;
import metadata.SeriesOnFile;
import metadata.SeriesSaved;
import saveandload.LoadFromFile;
import saveandload.SaveToFile;

import java.io.File;

public class XStreamTest {


    public static void main(String[] args) {
        AbstractSeriesList test = writeToFile();
    }

    public static AbstractSeriesList writeToFile() {
        File filePath = new File("/Users/ChrisCorner/Documents/Films_Series/Series");
        AbstractSeriesList onFile = new SeriesOnFile(filePath);
        AbstractSeriesList seriesFile = new SeriesSaved(onFile.getSeriesList());
        AbstractSeriesList.combineSeries(onFile, seriesFile);
        SaveToFile.saveSeriesToFile(seriesFile);

        AbstractSeriesList fromFile = LoadFromFile.loadSeriesList();
        return fromFile;
    }



    public static void writeToXmlRealData() {
        File filePath = new File("/Users/ChrisCorner/Documents/Films_Series/Series");
        AbstractSeriesList seriesList = new SeriesOnFile(filePath);

        XStream xstream = new XStream();

        String xml = xstream.toXML(seriesList);

        SeriesOnFile backToPojo = (SeriesOnFile) xstream.fromXML(xml);
    }

    public static void writeToXmlTestData() {
        File filePath = new File("/Users/ChrisCorner/Documents/Films_Series/Series");
        AbstractSeriesList seriesList = new SeriesOnFile(filePath);

        XStream xstream = new XStream();

        String xml = xstream.toXML(seriesList);

        SeriesOnFile backToPojo = (SeriesOnFile) xstream.fromXML(xml);
    }
}