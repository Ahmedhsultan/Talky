package gov.iti.jets.server.presentation.controllers;

import gov.iti.jets.server.Util.Queues.StatsLists;
import gov.iti.jets.server.service.ServerService;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class statisticsController implements Initializable {
    @FXML
    private Pane statisticpane;
    @FXML
    private Pane statistic;
    @FXML
    private Button statusBtn;
    @FXML
    private Button genderBtn;
    @FXML
    private Button countryBtn;

    private XYChart.Series dataSeries1;
    private  BarChart  barChart;

    private ServerService service;

    private  ObservableList<PieChart.Data> statusData, genderData;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setActions();
        service = new ServerService();

    }

public void setActions()
{
    StatsLists.getInstance().getStatusList().addListener(new ListChangeListener<PieChart.Data>() {
        @Override
        public void onChanged(Change<? extends PieChart.Data> change) {
            statusData.clear();
            statusData.addAll(
                    new PieChart.Data("Online",service.getUserStatusStats().getNumOfOnline()),
                    new PieChart.Data("Offline",service.getUserStatusStats().getNumOfOffline())
            );
        }
    });
    StatsLists.getInstance().getGenderList().addListener(new ListChangeListener<PieChart.Data>() {
        @Override
        public void onChanged(Change<? extends PieChart.Data> change) {
            genderData.clear();
            genderData.addAll(
                    new PieChart.Data("Male",service.getGenderStats().getNumOfMales()),
                    new PieChart.Data("Female",service.getGenderStats().getNumOfFemales())
            );
        }
    });
    StatsLists.getInstance().getCountryList().addListener(new ListChangeListener<PieChart.Data>() {
        @Override
        public void onChanged(Change<? extends PieChart.Data> change) {

            Map<String, Long> countryMap = service.getUserCountryStats().getCountryMap();

            for(Map.Entry<String, Long> m : countryMap.entrySet()){
                dataSeries1.getData().add(new XYChart.Data<>(m.getKey(), m.getValue()));
                System.out.println(m.getKey() + "  ===  "+m.getValue());
            }
            barChart.getData().add(dataSeries1);
        }
    });
}

    public void statusStatic(javafx.event.ActionEvent actionEvent) {

        statistic.getChildren().clear();
      statusData = FXCollections.observableArrayList(
                new PieChart.Data("Online",service.getUserStatusStats().getNumOfOnline()),
                new PieChart.Data("Offline",service.getUserStatusStats().getNumOfOffline())
        );
        PieChart pChart = new PieChart(statusData);
        statistic.getChildren().add(pChart);
    }

    public void ganderStatistic(javafx.event.ActionEvent actionEvent) {
        statistic.getChildren().clear();
        genderData =  FXCollections.observableArrayList(
                new PieChart.Data("Male",service.getGenderStats().getNumOfMales()),
                new PieChart.Data("Female",service.getGenderStats().getNumOfFemales())
        );
        PieChart pChart = new PieChart(genderData);
        statistic.getChildren().add(pChart);
    }

    public void countryStatistic(javafx.event.ActionEvent actionEvent) {
        statistic.getChildren().clear();

        CategoryAxis xAxis    = new CategoryAxis();
        xAxis.setLabel("COUNTRIES");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("POPULATION");

          barChart = new BarChart(xAxis, yAxis);

         dataSeries1 = new XYChart.Series();
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
