package views;

import engine.Simulator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("../views/charts.fxml"));
        primaryStage.setTitle("Parkeer simulator");
        primaryStage.setScene(new Scene(root, 600, 475));
        primaryStage.show();
    }



    public static void main(String[] args) {

        new Thread(() -> new Simulator().run()).start();
        launch(args);
        
    }
}
