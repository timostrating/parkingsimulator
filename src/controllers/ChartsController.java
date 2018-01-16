package controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

/**
 * Created by Hilko on 16-1-2018.
 */
public class ChartsController {

	@FXML
	public PieChart pieChart;
	public BarChart barChart;

	@FXML
	public void setValues() {

		ObservableList<PieChart.Data> pieDate = pieChart.getData();

		pieDate.add(new PieChart.Data("hans", .2));
		pieDate.add(new PieChart.Data("frederik", .6));

		XYChart.Series dataSeries = new XYChart.Series();

		dataSeries.setName("Rode auto's");

		dataSeries.getData().add(new XYChart.Data("", 400));

		barChart.getData().add(dataSeries);






	}

}
