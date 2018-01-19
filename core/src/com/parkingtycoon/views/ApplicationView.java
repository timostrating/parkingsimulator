package com.parkingtycoon.views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.parkingtycoon.CompositionRoot;
import com.parkingtycoon.controllers.BaseController;
import com.parkingtycoon.models.ApplicationModel;

import java.io.IOException;
import java.net.URL;

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

            FXMLLoader loader = new FXMLLoader(new URL("file", "", "core/src/com/parkingtycoon/presenters/application.fxml"));
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
