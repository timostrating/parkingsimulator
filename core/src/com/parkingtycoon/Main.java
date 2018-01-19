package com.parkingtycoon;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * The Main-class is used to start the application.
 *
 */

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Parkeer simulator");
        CompositionRoot.init(primaryStage);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
