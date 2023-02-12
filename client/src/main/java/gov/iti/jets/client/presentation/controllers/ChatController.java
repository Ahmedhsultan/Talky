package gov.iti.jets.client.presentation.controllers;


import com.jfoenix.controls.JFXButton;
import gov.iti.jets.client.Dina.MessagesQueue;
import gov.iti.jets.client.business.services.PaneManager;
import gov.iti.jets.client.network.service.InvitationService;
import gov.iti.jets.client.network.service.RMIManager;
import gov.iti.jets.client.network.service.SendMessage;
import gov.iti.jets.common.dto.*;
import gov.iti.jets.common.util.Constants;
import gov.iti.jets.common.util.Validation;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ChatController implements Initializable {

    @FXML
    private Button chatsButton;

    @FXML
    private Button contactsButton;

    @FXML
    private Button invitationsButton;

    @FXML
    private Button notificationsButton;

    @FXML
    private Label currentPane;
    @FXML
    private ListView leftList;

    @FXML
    private Pane containerPane;

    @FXML
    private TextField searchField;

    @FXML
    private AnchorPane rightAnchorPane;

    @FXML
    private Label chatName;

    @FXML
    private Circle chatIcon;
    @FXML
    private ImageView addIcon;
    @FXML
    private ImageView addContactCard;
    @FXML
    private ImageView deleteContactCard;

    @FXML
    private JFXButton btnAddContacts;
    @FXML
    private ScrollPane rightScrollPane;
    @FXML
    private ListView chatVBox;
    @FXML
    private TextArea messageField;
    @FXML
    private ColorPicker highlight;
    @FXML
    private ColorPicker textColor;
    @FXML
    private Slider fontSize;
    @FXML
    private ComboBox<String> fonts;
    @FXML
    private CheckBox italic;
    @FXML
    private CheckBox bold;
    @FXML
    private CheckBox underline;


    private UserSessionDto userSessionDto;

    ObservableList <? extends MessageDto> messages = FXCollections.observableArrayList();
    ObservableList<Pane> paneObservableList = FXCollections.observableArrayList();
    ObservableList<Pane> notificationObservableList = FXCollections.observableArrayList();
    ObservableList<Pane> messagesObservableList = FXCollections.observableArrayList();

    Long currentChat = 1l;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        PaneManager.setPrimaryPane("containerPane", containerPane);
        if(PasswordLoginController.userSessionDto != null)
            userSessionDto = PasswordLoginController.userSessionDto;
        else
            userSessionDto = RegisterController.userSessionDto;

        leftList.setItems(paneObservableList);

//        chatsButton.fire();
//        test();
        selectChat();
        checkMessages();


    }

//    [MessageDto(message=, senderId=01272934956, fontSize=0, bold=false, italic=false,
//    underline=false, font=null, textColor=0xffffffff, highlightColor=0xffffffff, timestamp=21:34)]
//    added at key 1
    private void checkMessages() {
        MessagesQueue.getList().addListener(new MapChangeListener<Long, List<MessageDto>>() {
            @Override
            public void onChanged(Change<? extends Long, ? extends List<MessageDto>> change) {
                Pane temp = PaneManager.getPaneManager().putNotificationPane();
                ((Label)(temp.lookup("#notificationMessage"))).setText(change.toString());
                notificationObservableList.add(temp);
                chatsButton.fire();
                if(currentChat.equals(change.getKey())){
                    if(change.getValueAdded().get(change.getValueAdded().size()-1).getSenderId()==userSessionDto.getUser().getId()) {
                        createMessage(change.getValueAdded().get(change.getValueAdded().size()-1), 1);
                    }
                    else {
                        createMessage(change.getValueAdded().get(change.getValueAdded().size()-1), 2);

                    }
                }

//                ob.add(createMessage(Color.DARKBLUE, messageField.getText(), 2));
//                chatVBox.setItems(ob);

//                System.out.println(change);

//                System.out.println(MessagesQueue.getList());
            }
        });
    }

    private void selectChat() throws NumberFormatException {
        leftList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Pane>() {
            @Override
            public void changed(ObservableValue<? extends Pane> observable, Pane oldValue, Pane newValue) {
                if(newValue!=null)
                    if(currentPane.getText().equals("Contacts")) {
                        chatName.setText(((Label) (newValue.lookup("#userName"))).getText());
//                        chatIcon.setFill(new ImagePattern(new Image(Constants.byteArrayToImage(arr, img).getPath(),100,100,false,true)));
//                        currentChat = Long.parseLong (((Label)(newValue.lookup("#chatID"))).getText());
                          currentChat = 1l;
                        for(MessageDto message :  MessagesQueue.getList().get(currentChat)) {
                           if(message.getSenderId().equals(userSessionDto.getUser().getId())) {
//                               chatVBox.getChildren().add(createMessage(Color.DARKBLUE, message.getMessage(), 2));
                           }

                        }
                    }
            }
        });
    }

    long i =0;


    @FXML
    private void openChats(ActionEvent actionEvent) {

//        ObservableList<MessageDto> ob = FXCollections.observableArrayList();
//
//        MessageDto messageDto = new MessageDto();
//        messageDto.setMessage(messageField.getText().trim());
//        messageDto.setFont(fonts.getSelectionModel().getSelectedItem());
//        messageDto.setBold(bold.isSelected());
//        messageDto.setItalic(italic.isSelected());
//        messageDto.setUnderline(underline.isSelected());
//        messageDto.setTextColor(textColor.getValue().toString());
//        messageDto.setHighlightColor(highlight.getValue().toString());
//        messageDto.setSenderId(userSessionDto.getUser().getId());
//        messageDto.setTimestamp( LocalTime.now().getHour() + ":" + LocalTime.now().getMinute() + "");
//
//        ob.add(messageDto);
//        MessagesQueue.getList().put(i++,ob);
//        b.add(1.0);
        searchField.setVisible(true);
        contactsButton.setStyle(null);
        invitationsButton.setStyle(null);
        notificationsButton.setStyle(null);
        currentPane.setText("Chats");
        leftList.setId("");
        deleteAddDelContact();
        chatsButton.setStyle("-fx-border-width: 0 0 2px 5px; -fx-border-color: purple;");
        paneObservableList.clear();
        MessagesQueue.getList().forEach((k, v)->{
            Pane temp = PaneManager.getPaneManager().putRecentChatCard();
            ((Label)(temp.lookup("#chatID"))).setText(k+"");
            (temp.lookup("#chatID")).setVisible(false);
//            putImageOnPane(chat.getPicture_icon(), temp);
//            putStatusOnPane("NA", temp);
//            putUserNameOnPane(chat.getName(), temp);
            putMessageOnPane(v.get(v.size()-1).getMessage(), temp);
            putTimeOnPane(v.get(v.size()-1).getTimestamp(), temp);
            paneObservableList.add(temp);
        });

        leftList.setItems(paneObservableList);
    }
    long j =0;

    private void openChat() {
        searchField.setVisible(true);
        contactsButton.setStyle(null);
        invitationsButton.setStyle(null);
        notificationsButton.setStyle(null);
        currentPane.setText("Chats");
        leftList.setId("");
        deleteAddDelContact();
        chatsButton.setStyle("-fx-border-width: 0 0 2px 5px; -fx-border-color: purple;");
        paneObservableList.clear();
        MessagesQueue.getList().forEach((k, v)->{
            Pane temp = PaneManager.getPaneManager().putRecentChatCard();
            ((Label)(temp.lookup("#chatID"))).setText(k+"");
            (temp.lookup("#chatID")).setVisible(false);
//            putImageOnPane(chat.getPicture_icon(), temp);
//            putStatusOnPane("NA", temp);
//            putUserNameOnPane(chat.getName(), temp);
            putMessageOnPane(v.get(v.size()-1).getMessage(), temp);
            putTimeOnPane(v.get(v.size()-1).getTimestamp(), temp);
            paneObservableList.add(temp);
        });

        leftList.setItems(paneObservableList);
    }



    @FXML
    private void openContacts(ActionEvent actionEvent) {

        MessageDto messageDto = new MessageDto();
        messageDto.setMessage(messageField.getText().trim());
        messageDto.setFont(fonts.getSelectionModel().getSelectedItem());
        messageDto.setBold(bold.isSelected());
        messageDto.setItalic(italic.isSelected());
        messageDto.setUnderline(underline.isSelected());
        messageDto.setTextColor(textColor.getValue().toString());
        messageDto.setHighlightColor(highlight.getValue().toString());
        messageDto.setSenderId(userSessionDto.getUser().getId());
        messageDto.setTimestamp( LocalTime.now().getHour() + ":" + LocalTime.now().getMinute() + "");

        MessagesQueue.getList().get(j++).add(messageDto);



        searchField.setVisible(true);
        currentPane.setText("Contacts");
        invitationsButton.setStyle(null);
        chatsButton.setStyle( null);
        notificationsButton.setStyle(null);
        leftList.setId("");
        deleteAddDelContact();
        contactsButton.setStyle(  "-fx-border-width: 0 0 2px 5px; -fx-border-color: purple;");
        paneObservableList.clear();
        for (ContactDto contact : userSessionDto.getContactListDto()) {
            Pane temp = PaneManager.getPaneManager().putContactCard();
//            putImageOnPane(contact.getPicture(), temp);
            putStatusOnPane(contact.getIsOnlineStatus(), temp);
            putUserNameOnPane(contact.getName(), temp);
            putBioOnPane(contact.getBio(), temp);
            putOnlineStatusOnPane(contact.getIsOnlineStatus(), temp);
            paneObservableList.add(temp);
        }
        leftList.setItems(paneObservableList);
    }

    @FXML
    private void openInvitations(ActionEvent actionEvent) {
        searchField.setVisible(true);
        currentPane.setText("Invitations");
        chatsButton.setStyle( null);
        contactsButton.setStyle(null);
        notificationsButton.setStyle(null);
        leftList.setId("");
        deleteAddDelContact();
        invitationsButton.setStyle(  "-fx-border-width: 0 0 2px 5px; -fx-border-color: purple;");
        paneObservableList.clear();
        for(InvitationDto invitation: userSessionDto.getInvitationListDto()) {
//            Pane temp = PaneManager.getPaneManager().putInvitationCard();

//            putImageOnPane(invitation., temp);
//            putUserNameOnPane(contact.getName(), temp);
//            paneObservableList.add(temp);
        }
//        leftList.setItems(paneObservableList);
    }

    @FXML
    private void openNotifications(ActionEvent actionEvent) {
        searchField.setVisible(true);
        currentPane.setText("Notifications");
        chatsButton.setStyle( null);
        contactsButton.setStyle(null);
        invitationsButton.setStyle(null);
        deleteAddDelContact();
        leftList.setId("");
        notificationsButton.setStyle(  "-fx-border-width: 0 0 2px 5px; -fx-border-color: purple;");
        leftList.setItems(notificationObservableList);
    }

    @FXML
    private void OpenProfile(MouseEvent mouseEvent) {
        currentPane.setText("Profile");
        chatsButton.setStyle( null);
        contactsButton.setStyle(null);
        invitationsButton.setStyle(null);
        notificationsButton.setStyle(null);
        searchField.setVisible(false);
        paneObservableList.clear();
        leftList.setId("listOfProfile");
        Pane temp = PaneManager.getPaneManager().putProfilePane();
//        putBioOnPane(userSessionDto.getUser().getBio(), temp);
//        putUserNameOnPane(userSessionDto.getUser().getName(), temp);
//        putPhoneNumOnPane(userSessionDto.getUser().getId(), temp);
//        putIsOnlineStatusOnPane(userSessionDto.getUser().getIsOnlineStatus(), temp);
//        putImageOnPane(userSessionDto.getUser().getImgPath(), userSessionDto.getUser().getImage(), temp);
        paneObservableList.add(temp);
        leftList.setItems(paneObservableList);


    }

    @FXML
    private void searchOnList(KeyEvent keyEvent) {
        ObservableList<Pane> c = FXCollections.observableArrayList(paneObservableList.stream().filter(x->((Label)(x.lookup("#userName"))).getText().toLowerCase().contains(searchField.getText().toLowerCase())).collect(Collectors.toList()));
        leftList.setItems(c);
    }


    private void putMessageOnPane(String message, Pane temp) {
        ((Label) temp.lookup("#recentMessage")).setText(message);
    }

    private void putTimeOnPane(String time, Pane temp) {
        ((Label)temp.lookup("#timestamp")).setText(time);
    }

    private void putUserNameOnPane(String userName, Pane temp) {
        ((Label)temp.lookup("#userName")).setText(userName);
    }

    private void putPhoneNumOnPane(String phone, Pane temp) {
        ((Label)temp.lookup("#userPhoneNumber")).setText(phone);
    }


    private void putStatusOnPane(String status, Pane temp) {
        if(status.equals("Offline"))
            ((Circle)(temp.lookup("#statusCircle"))).setFill(Color.RED);
        else if(status.equals("Online"))
            ((Circle)(temp.lookup("#statusCircle"))).setFill(Color.GREEN);
        else if(status.equals("NA"))
            ((Circle)(temp.lookup("#statusCircle"))).setRadius(0);
    }


    private void putOnlineStatusOnPane(String status, Pane temp) {
        if(status.equals("Offline"))
            ((Label)temp.lookup("#onlineStatus")).setText("");
        else
            ((Label)temp.lookup("#onlineStatus")).setText(status);
    }

    private void putBioOnPane(String bio, Pane temp) {
        ((Label)temp.lookup("#bio")).setText(bio);
    }

    private  void putImageOnPane(String img, byte [] arr, Pane temp) {
        try {
            ((Circle) temp.lookup("#userPic")).setFill(new ImagePattern(new Image(Constants.byteArrayToImage(arr, img).getPath(),100,100,false,true)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void AddNewContacts(MouseEvent event) {
        Pane temp = PaneManager.getPaneManager().putAddContactCard();
        currentPane.setText("Add Contacts");
        currentPane.setStyle("-fx-font-size: 40; ");
        chatsButton.setStyle( null);
        invitationsButton.setStyle(null);
        notificationsButton.setStyle(null);

        addIcon.setImage(null);
        addIcon.setDisable(true);
        addContactCard.setImage(new Image("image\\icons-add.png"));
        addContactCard.setDisable(false);
        deleteContactCard.setImage(new Image("image\\removeContact.png"));
        deleteContactCard.setDisable(false);
        btnAddContacts.setVisible(true);

        contactsButton.setStyle( "-fx-border-width: 0 0 2px 5px; -fx-border-color: purple;");
        paneObservableList.clear();
        paneObservableList.add(temp);
        leftList.setItems(paneObservableList);
    }
    @FXML
    void deleteContact(MouseEvent event) {
        //System.out.println("Remove "+p.size());
        if(paneObservableList.size()>1){
            paneObservableList.remove(paneObservableList.size()-1);
            leftList.setItems(paneObservableList);
        }

    }
    @FXML
    void addNewContact(MouseEvent event) {
        Pane temp = PaneManager.getPaneManager().putAddContactCard();
        paneObservableList.add(temp);
        leftList.setItems(paneObservableList);
    }
    @FXML
    void addContacts(ActionEvent event) {
        leftList.cellFactoryProperty();
        for (Pane k: paneObservableList) {
            TextField tx = (TextField) k.getChildren().get(1);
            Label label = (Label) k.getChildren().get(2);
            if(Validation.validatePhoneNumber(tx,label)){
                System.out.println(tx.getText());
                Registry reg = null;
                try {
                    reg = RMIManager.getRegistry();
                } catch (RemoteException e) {
                    System.out.println(e.getMessage());
                    throw new RuntimeException(e);
                }

                System.out.println("sender id = "+userSessionDto.getUser().getId() + "reciever id  = "+ tx.getText());
                new InvitationService().sendInvit(userSessionDto.getUser().getId(),tx.getText(),reg);
            }
        }
    }
    private void deleteAddDelContact(){
        addIcon.setImage(new Image("image/icons-add.png"));
        addIcon.setDisable(false);
        addContactCard.setImage(null);
        addContactCard.setDisable(true);
        deleteContactCard.setImage(null);
        deleteContactCard.setDisable(true);
        btnAddContacts.setVisible(false);
    }

    public void sendMessage(ActionEvent actionEvent) {

        MessageDto messageDto = new MessageDto();
        messageDto.setMessage(messageField.getText().trim());
        messageDto.setFont(fonts.getSelectionModel().getSelectedItem());
        messageDto.setBold(bold.isSelected());
        messageDto.setItalic(italic.isSelected());
        messageDto.setUnderline(underline.isSelected());
        messageDto.setTextColor(textColor.getValue().toString());
        messageDto.setHighlightColor(highlight.getValue().toString());
        messageDto.setSenderId(userSessionDto.getUser().getId());
        messageDto.setTimestamp( LocalTime.now().getHour() + ":" + LocalTime.now().getMinute() + "");
        try {
            SendMessage.send(currentChat, messageDto);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }

    }

    public void createMessage(MessageDto messageOptions, int chat) {

//        ImageView imageView = new ImageView(image);
//        imageView.setFitWidth(20);
//        imageView.setFitHeight(20);
        HBox sentMessage = new HBox();

        if (chat == 1) {
            sentMessage.getChildren().addAll(createBubble(messageOptions, Color.DARKBLUE));
        } else {
            sentMessage.getChildren().addAll(createBubble(messageOptions, Color.LIGHTBLUE));
        }

        messagesObservableList.add(sentMessage);
        chatVBox.setItems(messagesObservableList);
//        sentMessage.setTranslateX(x);
//        return sentMessage;
    }

    private Group createBubble(MessageDto messageOptions, Color bubbleColor) {

        Text messageTemp = new Text(messageOptions.getMessage());
        messageTemp.setWrappingWidth(70);
        messageTemp.setFont(Font.font(messageOptions.getFont(), messageOptions.getFontSize()));
        int messageWidth = (int) messageTemp.getLayoutBounds().getWidth();
        int messageHeight = (int) messageTemp.getLayoutBounds().getHeight();

        Text timeTemp = new Text(messageOptions.getTimestamp());
        timeTemp.setFont(Font.font("Arial", 10));
        int timeHeight = (int) timeTemp.getLayoutBounds().getHeight();
        int timeWidth = (int) timeTemp.getLayoutBounds().getWidth();

        Rectangle bubble = new Rectangle();
        bubble.setWidth(messageWidth + 25);
        bubble.setHeight(messageHeight + timeHeight + 10);
        bubble.setArcHeight(30);
        bubble.setArcWidth(30);
        bubble.setFill(bubbleColor);

        Label messageLabel = new Label(messageOptions.getMessage());
        messageLabel.setWrapText(true);
        messageLabel.setMaxWidth(70);
        messageLabel.setFont(Font.font(messageOptions.getFont(), messageOptions.getFontSize()));
//        messageLabel.setTextFill();
        messageLabel.setTranslateX((bubble.getWidth() - messageWidth) / 2);
        messageLabel.setTranslateY(10);

        Label timeLabel = new Label(messageOptions.getTimestamp());
        timeLabel.setFont(Font.font("Arial", 10));
        timeLabel.setTextFill(Color.GRAY);
        timeLabel.setTranslateX(bubble.getWidth() - (timeWidth + 10));
        timeLabel.setTranslateY(messageHeight + 7);
        Group group = new Group(bubble, messageLabel, timeLabel);
        return group;
    }

}

