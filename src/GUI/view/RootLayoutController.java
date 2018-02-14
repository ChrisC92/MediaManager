package GUI.view;

import GUI.MainApp;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


/**
 * Root layout of the GUI. Provides the basic layout which contains the menu bar and space
 * where other JavaFX elements can be placed
 */

public class RootLayoutController implements Initializable {
    private Stage stage;
    private MainApp mainApp;
    private static MainLayoutController mainController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void doExit() {
        ButtonType saveButton = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        ButtonType noSaveButton = new ButtonType("Don't Save", ButtonBar.ButtonData.CANCEL_CLOSE);
        String contentText = "Would you like to save?";
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, contentText, saveButton, noSaveButton);
        alert.setTitle("Save");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == saveButton) {
            mainController.saveData();
        }
        Platform.exit();
    }

    public void doOpen() {
        DirectoryChooser dirChoice = new DirectoryChooser();
        dirChoice.setTitle("Please Set The FilePath");
        File seriesPath = dirChoice.showDialog(stage);
        mainController.setSeriesOnFile(seriesPath);
    }

    public void doClearSaved() {
        ButtonType confirmClear = new ButtonType("Clear", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        String confirmText = "Are you sure you want to delete the saved file?";
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, confirmText, confirmClear, cancel);
        alert.setTitle("Clear File");
        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == confirmClear) {
            File fileAll = new File("savedData/allSeriesList.ser");
            fileAll.delete();
            mainController.deleteSavedFiles();
        }
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public static void setMainController(MainLayoutController mainController) {
        RootLayoutController.mainController = mainController;
    }

}
