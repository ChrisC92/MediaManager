package xStream;


import com.thoughtworks.xstream.XStream;
import metadata.AbstractSeriesList;
import metadata.Series;
import metadata.SeriesOnFile;
import metadata.SeriesSaved;

import java.io.File;

public class XStreamTest {


    public static void main(String[] args) {
        File filePath = new File("/Users/ChrisCorner/Documents/Films_Series/Series");
        AbstractSeriesList seriesList = new SeriesOnFile(filePath);

        XStream xstream = new XStream();

        xstream.alias("SeriesFromFile", SeriesOnFile.class);
        xstream.alias("SeriesSaved", SeriesSaved.class);
        xstream.alias("Series", Series.class);

        String xml = xstream.toXML(seriesList);

        SeriesOnFile backToPojo = (SeriesOnFile) xstream.fromXML(xml);
    }
}
