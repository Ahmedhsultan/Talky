package gov.iti.jets.server.presentation.controllers;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXListView;
import gov.iti.jets.common.dto.MessageDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
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

        paneObservableList.add(loadPaneUserCard("UserRow"));

//        UsersData.forEach((k, v)->{
//            Pane p = loadPaneUserCard("userCard");
//            addCardData(p, "Martina Naeem", "01012312343" ,"Martina@gmail.com","Female", "Egypt", "offline");
//        });
        for(int i =0;i<5;i++){
            Pane p = loadPaneUserCard("userCard");
            addCardData(p, "Martina Naeem", "01012312343" ,"Martina@gmail.com","Female", "Egypt", "offline");
        }
        userListView.setItems(paneObservableList);
    }
    private Pane loadPaneUserCard(String name) {
        Pane pane = null;
        try {
            Parent root = FXMLLoader.load(getClass().getResource(String.format("/view/%s.fxml",name)));
            pane = (Pane) root;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pane;
    }

    private void addCardData(Pane p, String name, String phone , String email,String gender, String country, String status){
        Pane temp = loadPaneUserCard("userCard");
        ((Label)(temp.lookup("#UserName"))).setText(name);
        ((Label)(temp.lookup("#userPhone"))).setText(phone);
        ((Label)(temp.lookup("#userEmail"))).setText(email);
        ((Label)(temp.lookup("#userGender"))).setText(gender);
        ((Label)(temp.lookup("#userCountry"))).setText(country);
        ImageView s = (ImageView) temp.getChildren().get(7);
        if(status == "online"){
            s.setImage(new Image("/image/icons8-checkmark-64.png"));
        }else{
            s.setImage(new Image("/image/icons8-cancel-64.png"));
        }
        paneObservableList.add(temp);
    }


}

