package uiCommandLine;

import metadata.SeriesList;

import java.io.File;
import java.util.Scanner;

/**
 * Class will display the menu's from the Menu class and allow the user to make actions
 * from the options provided
 */

public class InputHandler {

    public boolean firstOpen;
    private SeriesList seriesList;

    public InputHandler() {
        firstOpen = initialOpenCheck();
    }

    public void getSeriesList(Scanner kb) {
        if (firstOpen) {
            seriesList = Menus.getFilePath(kb);
            firstOpen = false;
            mainMenu(kb);
        }
    }

    public void mainMenu(Scanner kb) {
        Menus.mainMenuUI(seriesList, kb);
    }

    private boolean initialOpenCheck() {
        File seriesFile = new File("savedData/storedSeriesList.ser");
        return seriesFile.exists();
    }


    public static void main(String[] args) {
        InputHandler ih = new InputHandler();

    }
}
