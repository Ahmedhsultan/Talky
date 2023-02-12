package gov.iti.jets.server.presentation.controllers;

import gov.iti.jets.server.service.ServerService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.chart.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.Arrays;
import java.util.Map;
import java.util.ResourceBundle;

public class statisticsController implements Initializable {
    @FXML
    private Pane statisticpane;
    @FXML
    private Pane statistic;
    private ServerService service;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        service = new ServerService();

    }


    public void statusStatic(javafx.event.ActionEvent actionEvent) {
        statistic.getChildren().clear();
        ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList(
                new PieChart.Data("Online",service.getUserStatusStats().getNumOfOnline()),
                new PieChart.Data("Offline",service.getUserStatusStats().getNumOfOffline())
        );
        PieChart pChart = new PieChart(pieData);
        statistic.getChildren().add(pChart);
    }

    public void ganderStatistic(javafx.event.ActionEvent actionEvent) {
        statistic.getChildren().clear();
        ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList(
                new PieChart.Data("Male",service.getGenderStats().getNumOfMales()),
                new PieChart.Data("Female",service.getGenderStats().getNumOfFemales())
        );
        PieChart pChart = new PieChart(pieData);
        statistic.getChildren().add(pChart);
    }

    public void countryStatistic(javafx.event.ActionEvent actionEvent) {
        statistic.getChildren().clear();

        CategoryAxis xAxis    = new CategoryAxis();
        xAxis.setLabel("COUNTRIES");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("POPULATION");

        BarChart  barChart = new BarChart(xAxis, yAxis);

        XYChart.Series dataSeries1 = new XYChart.Series();
        dataSeries1.setName("2023");


        Map<String, Long> countryMap = service.getUserCountryStats().getCountryMap();

        for(Map.Entry<String, Long> m : countryMap.entrySet()){
            dataSeries1.getData().add(new XYChart.Data<>(m.getKey(), m.getValue()));
            System.out.println(m.getKey() + "  ===  "+m.getValue());
        }
        barChart.getData().add(dataSeries1);

        statistic.getChildren().add(barChart);
    }
}
