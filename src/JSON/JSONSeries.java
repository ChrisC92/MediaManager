package JSON;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.DeserializationConfig;
import jdk.internal.org.objectweb.asm.tree.analysis.Value;
import metadata.AbstractSeriesList;
import metadata.SeriesOnFile;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.annotation.JsonTypeInfo;


public class JSONSeries {
    public static void main(String[] args) throws IOException, ParseException {

        ObjectMapper mapper = new ObjectMapper();

        File filePath = new File("/Users/ChrisCorner/Documents/Films_Series/Series");
        AbstractSeriesList seriesList = new SeriesOnFile(filePath);


        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        mapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);
        mapper.setSerializationInclusion(Include.NON_EMPTY);
        String string = mapper.writeValueAsString(seriesList);


        System.out.println(string);

        AbstractSeriesList backToSeries = mapper.readValue(string, SeriesOnFile.class);
        System.out.println("series list: ");
        backToSeries.printSeriesList();
        System.out.print("\nBoolean: " + seriesList.equals(backToSeries));


    }
}
