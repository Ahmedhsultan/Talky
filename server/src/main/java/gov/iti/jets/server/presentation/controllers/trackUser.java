package gov.iti.jets.server.presentation.controllers;


import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Map;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXListView;
import gov.iti.jets.common.dto.MessageDto;
import gov.iti.jets.common.dto.MiniUserDto;
import gov.iti.jets.common.dto.UserDto;
import gov.iti.jets.common.util.Constants;
import gov.iti.jets.server.Util.Queues.StatsLists;
import gov.iti.jets.server.Util.Queues.UsersList;
import gov.iti.jets.server.service.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class trackUser implements Initializable {

    @FXML
    private JFXListView userListView;
    ObservableList<Pane> paneObservableList = FXCollections.observableArrayList();

//    private static ObservableMap<Long, ObservableList<Object>> UsersData;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        StatsLists.getInstance().getList().addListener(new ListChangeListener<String>() {
            public void onChanged(ListChangeListener.Change<? extends String> change) {
                paneObservableList.clear();
                paneObservableList.add(loadPaneUserCard("UserRow"));
                UserService userService = new UserService();
                userService.getAllMiniUsers().forEach((miniUserDto) -> {
                    Pane p = loadPaneUserCard("userCard");
                    addCardData(p, miniUserDto);
                });
            }
        });

        paneObservableList.clear();
        paneObservableList.add(loadPaneUserCard("UserRow"));
        UserService userService = new UserService();
        userService.getAllMiniUsers().forEach((miniUserDto) -> {
            Pane p = loadPaneUserCard("userCard");
            addCardData(p, miniUserDto);
        });
//        UsersData.forEach((k, v)->{
//            Pane p = loadPaneUserCard("userCard");
//            addCardData(p, "Martina Naeem", "01012312343" ,"Martina@gmail.com","Female", "Egypt", "offline");
//        });
//        for(int i =0;i<5;i++){
//            Pane p = loadPaneUserCard("userCard");
//            addCardData(p, "Martina Naeem", "01012312343" ,"Martina@gmail.com","Female", "Egypt", "offline");
//        }
        userListView.setItems(paneObservableList);
    }
    private Pane loadPaneUserCard(String name) {
        Pane pane = null;
        try {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource(String.format("view/%s.fxml",name)));
            pane = (Pane) root;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pane;
    }

    private void addCardData(Pane p, MiniUserDto dto){
        Pane temp = loadPaneUserCard("userCard");
        ((Label)(temp.lookup("#UserName"))).setText(dto.getName());
        ((Label)(temp.lookup("#userPhone"))).setText(dto.getId());
        ((Label)(temp.lookup("#userEmail"))).setText(dto.getEmail());
        ((Label)(temp.lookup("#userGender"))).setText(dto.getGender());
        ((Label)(temp.lookup("#userCountry"))).setText(dto.getCountry());
        ImageView s = null;
//        try {
           s = (ImageView) temp.getChildren().get(6);
// =new ImageView(new Image(URLDecoder.decode(Constants.USER_IMAGES_DIR+dto.getImgPath(),"UTF-8"))));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        if(!dto.getIsOnlineStatus() .equals(Constants.ONLINE_STATUS_OFFLINE) ){
            s.setImage(new Image("/image/icons8-checkmark-64.png"));
        }else{
            s.setImage(new Image("/image/icons8-cancel-64.png"));
        }
        paneObservableList.add(temp);
    }


}

