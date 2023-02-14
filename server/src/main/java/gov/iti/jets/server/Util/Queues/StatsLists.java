package gov.iti.jets.server.Util.Queues;

import gov.iti.jets.server.service.ServerService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;

public class StatsLists{
//    private ObservableList<PieChart.Data> statusList;
//    private   ObservableList<PieChart.Data> genderList;
//    private   ObservableList<PieChart.Data> countryList;
    private  ObservableList<String> list;


    private static StatsLists statsLists;
    public  static StatsLists getInstance(){
        synchronized (StatsLists.class){
        if(statsLists == null) {
            statsLists = new StatsLists();
//            statsLists.statusList= FXCollections.observableArrayList();
//            statsLists.genderList= FXCollections.observableArrayList();
//            statsLists.countryList= FXCollections.observableArrayList();
            statsLists.list= FXCollections.observableArrayList();


        }
    }
        return statsLists;
    }

    public ObservableList<String> getList() {
        return list;
    }

    public void updateUserStats()
    {
        Platform.runLater(()->{
            StatsLists.getInstance().list.clear();
            StatsLists.getInstance().list.add("");
        });
    }
//    public ObservableList<PieChart.Data> getStatusList() {
//        return statusList;
//    }
//
//    public ObservableList<PieChart.Data> getGenderList() {
//        return genderList;
//    }
//
//    public ObservableList<PieChart.Data> getCountryList() {
//        return countryList;
//    }

//    public void updateUserStats()
//    {
//        Platform.runLater(()->{
//            StatsLists.getInstance().statusList.clear();
//            StatsLists.getInstance().statusList.add(new PieChart.Data("",0.0));
//        });
//    }
//    public void updateGenderStats()
//    {
//        Platform.runLater(()->{
//            StatsLists.getInstance().genderList.clear();
//            StatsLists.getInstance().genderList.add( new PieChart.Data("",0.0));
//        });
//    }
//    public void updateCountryStats()
//    {
//        Platform.runLater(()->{
//            StatsLists.getInstance().countryList.clear();
//            StatsLists.getInstance().countryList.add(new PieChart.Data("",0.0));
//
//        });
//    }


}
