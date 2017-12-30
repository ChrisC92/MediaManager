package GUI;

import GUI.view.MainLayoutController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import metadata.SeriesList;
import storageAndExtraction.Deserialize;
import storageAndExtraction.ExtractData;

import java.io.File;
import java.io.IOException;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;

    private SeriesList seriesOnFile;
    private SeriesList seriesExtracted;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("SeriesTracker");
        File file = new File("/Users/ChrisCorner/Documents/Films_Series/Series");
        seriesExtracted = ExtractData.extractSeriesOnFile(file, seriesExtracted);
        //seriesOnFile = Deserialize.Deserialize("savedData/storedSeriesList.ser");
//        setUserAgentStylesheet(STYLESHEET_MODENA);

        initRootLayout();
        showPersonOverView();
    }

    /**
     * RootLayout contains the menu bar UI
     */
    private void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = loader.load();

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows the main layout inside of the root layout
     */
    private void showPersonOverView() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/MainLayout.fxml"));
            AnchorPane mainLayout = loader.load();

            rootLayout.setCenter(mainLayout);

            MainLayoutController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }

}
