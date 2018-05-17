package createTestFiles;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This class will create a number of text documents in the given folder that will increment episodes and season to
 * replicate what the classes will be working with.
 */
public class CreateTestDocsBasic {

    /**
     * Creates a directory in the given directory and with the given name for directory
     */
    private static void createDirectory(String dirName) {
        StringBuilder path = new StringBuilder("series/");
        path.append(dirName);
        try {
            File filePath = new File(path.toString());
            boolean success = filePath.mkdir();
            if (success) {
                System.out.println("Directory " + dirName + " has been created");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a text document in the given directory with the file name, also passed into the arguemnt
     * is the contents to the file
     */
    private static void createTextDocs(String fileName, String dir) {
        String file = "series/";
        if (!dir.isEmpty()) {
            file = file + dir + "/";
        }
        file = file + fileName;
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write("This is an episode");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This class creates 10 different directories and fills with a randomised number of episodes (8-15)
     * to be used in testing class
     */
    public static void createDirAndFiles() {
        for (int i = 1; i <= 12; i++) {
            String seriesName = "The series of " + i;
            createDirectory(seriesName);
            int numberOfEps = ThreadLocalRandom.current().nextInt(8, 16);
            for (int j = 1; j <= numberOfEps; j++) {
//                if (numberOfEps > 9) {
//                    fileName = writeName("episode:", j);
//                    createTextDocs(fileName, seriesName);
//                } else {
                    String fileName = "episode: " + j;
                    createTextDocs(fileName, seriesName);
                }
            }
        }
//    }

    private static String writeName(String name, int epNum) {
        if (epNum < 10) {
            return name + "0" + epNum;
        }
        return name + epNum;
    }


    public static void main(String[] args) {
        createDirAndFiles();
    }
}

