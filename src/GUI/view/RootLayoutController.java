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
 *  Root layout of the GUI. Provides the basic layout which contains the menu bar and space
 *  where other JavaFX elements can be placed
 */

public class RootLayoutController implements Initializable {
    private Stage stage;
    private MainApp mainApp;
    private static MainLayoutController mainController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    //TODO: add a cancel button
    public void doExit() {
        ButtonType saveButton = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        ButtonType noSaveButton = new ButtonType("Don't Save", ButtonBar.ButtonData.CANCEL_CLOSE);
        String contentText = "Would you like to save?";
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, contentText, saveButton, noSaveButton);
        alert.setTitle("Save");
        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == saveButton) {
            mainController.saveData();
        }

        Platform.exit();
    }

    public void doOpen() {
        DirectoryChooser dirChoice = new DirectoryChooser();
        dirChoice.setTitle("Please Set The FilePath");
        File seriesPath = dirChoice.showDialog(stage);
        mainController.setOnFileSeries(seriesPath);
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
