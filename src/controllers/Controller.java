package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.ScatterChart;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;

import java.util.function.DoubleToIntFunction;

public class Controller {

	public Slider slider;

    @FXML
	public void hoi(ActionEvent actionEvent) {
		System.out.println("hoi");
	}

	@FXML
	public void hoi2() {

    	int v = (int)slider.getValue();

    	System.out.println(v);


	}
}
