package parkingsimulator;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    /*
        The main() method launches a new Application View (fxml)
        The application view will create the ApplicationController
        The ApplicationController will create the ApplicationModel
     */

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Parkeer simulator");
        CompositionRoot.init(primaryStage);
        primaryStage.show();
    }

    public static void main(String[] args) {

        launch(args);
    }

}
