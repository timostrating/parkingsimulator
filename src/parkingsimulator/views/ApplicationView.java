package parkingsimulator.views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import parkingsimulator.CompositionRoot;
import parkingsimulator.controllers.BaseController;
import parkingsimulator.models.ApplicationModel;

import java.io.IOException;

/**
 * Created by Hilko on 18-1-2018.
 */
public class ApplicationView extends BaseView<ApplicationModel> {

    public ApplicationView(ApplicationModel model, BaseController controller) {
        super(model, controller);
    }

    @Override
    protected void createPresenter(BaseController controller) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("../presenters/application.fxml"));
            loader.setController(controller);

            Stage stage = CompositionRoot.getInstance().stage;
            stage.setScene(new Scene(loader.load(), 1500, 800));


            stage.setOnCloseRequest(event -> model.isSimulatorRunning = false);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateView() {

    }
}
