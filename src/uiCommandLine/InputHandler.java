package uiCommandLine;

import extractingData.SeriesList;

import java.io.File;
import java.util.Scanner;

/**
 * Class will display the menu's from the Menu class and allow the user to make actions
 * from the options provided
 */

public class InputHandler {

    private boolean firstOpen;
    private SeriesList seriesList;

    public InputHandler() {
        //TODO: get the value for this boolean from file of where information will be stored
        firstOpen = true;
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

    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        InputHandler userInput = new InputHandler();
        userInput.getSeriesList(kb);
    }
}
