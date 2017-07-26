package sample;

import javafx.application.Application;
import javafx.stage.*;

public class MainApp extends Application {
    private Model model;

    @Override
    public void start(Stage primaryStage) throws Exception{
        model = new Model();
        model.setPrimaryStage(primaryStage);
        model.showMainWindow();
    }

    public Model getModel() {
        return model;
    }

    public static void main(String[] args) {
        launch(args);
    }

}
