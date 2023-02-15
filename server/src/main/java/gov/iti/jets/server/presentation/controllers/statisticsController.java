package gov.iti.jets.server.presentation.controllers;

import gov.iti.jets.common.dto.ConnectionDto;
import gov.iti.jets.common.dto.UserDto;
import gov.iti.jets.server.Util.Queues.ConnectedClientsMap;
import gov.iti.jets.server.Util.Queues.StatsLists;
import gov.iti.jets.server.Util.Queues.UsersList;
import gov.iti.jets.server.entity.User;
import gov.iti.jets.server.entity.statistics.UserStatusStat;
import gov.iti.jets.server.service.ServerService;
import gov.iti.jets.server.service.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

    @FXML
    private Label noOfOnline;

    @FXML
    private Label noOfOofline;
    @FXML
    private ImageView img1;
    @FXML
    private ImageView img2;

    private XYChart.Series dataSeries1;
    private  BarChart  barChart;

    private ServerService service;

    private  ObservableList<PieChart.Data> statusData, genderData;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setActions();
        service = new ServerService();
        statistic.getChildren().clear();
        statusBtn.setStyle("-fx-border-width: 2px 2px 2px 2px; -fx-border-color: purple; -fx-background-radius: 10;");
        UserStatusStat  userStatusStat = service.getUserStatusStats();
        statusData = FXCollections.observableArrayList(
                new PieChart.Data("Online",userStatusStat.getNumOfOnline()),
                new PieChart.Data("Offline",userStatusStat.getNumOfOffline())
        );
        noOfOnline.setText("Online: " + userStatusStat.getNumOfOnline());
        noOfOofline.setText("Offline: " + userStatusStat.getNumOfOffline());
        PieChart pChart = new PieChart(statusData);
        statistic.getChildren().add(pChart);
    }

public void setActions(){
    StatsLists.getInstance().getList().addListener(new ListChangeListener<String>() {
        public void onChanged(ListChangeListener.Change<? extends String> change) {

            if (genderData != null) {
                genderData.clear();
                genderData.addAll(
                        new PieChart.Data("Male", service.getGenderStats().getNumOfMales()),
                        new PieChart.Data("Female", service.getGenderStats().getNumOfFemales())
                );
            }
            if (statusData != null) {

                statusData.clear();
                statusData.addAll(
                        new PieChart.Data("Online", service.getUserStatusStats().getNumOfOnline()),
                        new PieChart.Data("Offline", service.getUserStatusStats().getNumOfOffline())
                );
                if(statusData.isEmpty())
                {
                    System.out.println("empty");
                }
            }
            if (dataSeries1 != null) {
                Map<String, Long> countryMap = service.getUserCountryStats().getCountryMap();

                for (Map.Entry<String, Long> m : countryMap.entrySet()) {
                    dataSeries1.getData().add(new XYChart.Data<>(m.getKey(), m.getValue()));
                    System.out.println(m.getKey() + "  ===  " + m.getValue());
                }
                barChart.getData().add(dataSeries1);
            }
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
        noOfOnline.setText("Online: " + service.getUserStatusStats().getNumOfOnline());
        noOfOofline.setText("Offline: " + service.getUserStatusStats().getNumOfOffline());
        img1.setImage(new Image("image/offlinr-people.png"));
        img2.setImage(new Image("image/online-poeple.png"));
    }

    public void ganderStatistic(javafx.event.ActionEvent actionEvent) {
        statistic.getChildren().clear();
        genderBtn.setStyle("-fx-border-width: 2px 2px 2px 2px; -fx-border-color: purple; -fx-background-radius: 10;");
        statusBtn.setStyle("-fx-background-radius: 10; -fx-background-color: #d2c8ee;");
        countryBtn.setStyle("-fx-background-radius: 10; -fx-background-color: #d2c8ee;");
        genderData =  FXCollections.observableArrayList(
                new PieChart.Data("Male",service.getGenderStats().getNumOfMales()),
                new PieChart.Data("Female",service.getGenderStats().getNumOfFemales())
        );
        PieChart pChart = new PieChart(genderData);
        statistic.getChildren().add(pChart);
        noOfOnline.setText("Male: " + service.getGenderStats().getNumOfMales());
        noOfOofline.setText("Female: " + service.getGenderStats().getNumOfFemales());
        img1.setImage(new Image("image/icons8-standing-man-50.png"));
        img2.setImage(new Image("image/icons8-female-51.png"));
    }

    public void countryStatistic(javafx.event.ActionEvent actionEvent) {
        statistic.getChildren().clear();
        countryBtn.setStyle("-fx-border-width: 2px 2px 2px 2px; -fx-border-color: purple; -fx-background-radius: 10;");
        statusBtn.setStyle("-fx-background-radius: 10; -fx-background-color: #d2c8ee;");
        genderBtn.setStyle("-fx-background-radius: 10; -fx-background-color: #d2c8ee;");
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
        noOfOnline.setText("");
        noOfOofline.setText("");
        img1.setImage(null);
        img2.setImage(null);
    }
}
