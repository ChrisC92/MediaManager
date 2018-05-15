package GUI;

import GUI.menus.MainLayoutController;
import GUI.menus.RootLayoutController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    private RootLayoutController rootController;
    private MainLayoutController mainController;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("SeriesTracker");

        setUserAgentStylesheet(STYLESHEET_MODENA);

        initRootLayout();
        initMainLayout();
        linkToRootController();
    }

    /**
     * RootLayout contains the menu bar UI Scene object
     */
    private void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("menus/RootLayout.fxml"));
            rootLayout = loader.load();

            RootLayoutController rootController = loader.getController();
            rootController.setMainApp(this);
            this.rootController = rootController;
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.setOnHidden(e -> rootController.doExit());
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows the main layout inside of the root layout
     */
    private void initMainLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("menus/MainLayout.fxml"));
            AnchorPane mainLayout = loader.load();
            rootLayout.setCenter(mainLayout);
            MainLayoutController mainController = loader.getController();
            this.mainController = mainController;
            mainController.setStage(primaryStage);
            mainController.setMainApp(this);
            RootLayoutController.setMainController(mainController);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void linkToRootController() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("menus/RootLayout.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            ((RootLayoutController) fxmlLoader.getController()).setStage(primaryStage);
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }

}
