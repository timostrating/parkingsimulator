package parkingsimulator.views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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

            FXMLLoader loader = new FXMLLoader(getClass().getResource("../presenters/sample.fxml"));
            loader.setController(controller);
            System.out.println(CompositionRoot.getInstance());
            CompositionRoot.getInstance().stage.setScene(new Scene(loader.load(), 1500, 800));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateView() {

    }
}
