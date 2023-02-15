package gov.iti.jets.client.presentation.controllers;


import com.google.common.io.Files;
import com.jfoenix.controls.JFXButton;
import gov.iti.jets.client.Queues.*;
import gov.iti.jets.client.Queues.ContactList;
import gov.iti.jets.client.Queues.InvitationQueue;
import gov.iti.jets.client.Queues.MessagesQueue;
import gov.iti.jets.client.Queues.MyID;
import gov.iti.jets.client.business.services.PaneManager;
import gov.iti.jets.client.business.services.SceneManager;
import gov.iti.jets.client.network.service.*;
import gov.iti.jets.common.dto.*;
import gov.iti.jets.common.util.Constants;
import gov.iti.jets.common.util.Validation;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
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
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.time.LocalTime;
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
    private Button logout;

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
    private VBox chatVBox;
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
    @FXML
    private Button attachBtn;


    @FXML
    private Circle notificationCountCircle;
    @FXML
    private Label notificationCountLabel;
    @FXML
    private Button closeChatBtn;
    @FXML
    private ButtonBar firstButtonBar;
    @FXML
    private ButtonBar secondButtonBar;

    @FXML
    private ImageView userImage;

    private UserSessionDto userSessionDto;

    ObservableList<Pane> paneObservableList = FXCollections.observableArrayList();
    ObservableList<Pane> notificationObservableList = FXCollections.observableArrayList();
    ObservableList<Pane> messagesObservableList = FXCollections.observableArrayList();
    ObservableList<Pane> contactsObservableList = FXCollections.observableArrayList();
    ObservableList<Pane> invitationsObservableList = FXCollections.observableArrayList();
    ObservableList<Pane> chatsObservableList = FXCollections.observableArrayList();


    int notificationCount = 0;
    Long currentChat = null;
    boolean isChatClosed = true;
    boolean isBarsVisible = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        PaneManager.setPrimaryPane("containerPane", containerPane);
        closeChatBtn.fire();

        resetMessageOptions();
        if (PasswordLoginController.userSessionDto != null)
            userSessionDto = PasswordLoginController.userSessionDto;
        else
            userSessionDto = RegisterController.userSessionDto;
        attachBtn.setOnAction(ev->{
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(null);

            try {
                if (file != null) {
                    new FileTransferService().sendFile(currentChat, userSessionDto.getUser().getId(),file);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }catch (IOException e) {
                e.printStackTrace();
            }
        });

        try {
            userImage.setImage(new Image(saveUserImage(userSessionDto.getUser()),200,200,false,true));
        } catch (IOException e) {
            e.printStackTrace();
        }

        chatsButton.fire();
        selectChat();
        checkInvitations();
        checkNotifications();
        checkContacts();
        checkNewSentMessages();
        selectInvitation();
    }

    private void resetMessageOptions() {
        ObservableList<String> fontFamilies = FXCollections.observableArrayList(Font.getFamilies());
        fonts.setItems(fontFamilies);
        fonts.setValue(fontFamilies.get(0));
        fontSize.setValue(35);
        textColor.setValue(Color.rgb(103, 19, 210));
        highlight.setValue(Color.TRANSPARENT);
        bold.setSelected(true);
        italic.setSelected(false);
        underline.setSelected(false);

    }


    private void checkNewSentMessages() {
        MessagesQueue.getList().addListener(new MapChangeListener<Long, List<MessageDto>>() {
            @Override
            public void onChanged(Change<? extends Long, ? extends List<MessageDto>> change) {
                if (currentPane.getText().equals("Chats")) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            createOneToOneChatList();

                        }
                    });
                }
                if (currentChat != null && currentChat.equals(MessagesQueue.change.keySet().toArray()[0])) {
                    if (MessagesQueue.change.get(MessagesQueue.change.keySet().toArray()[0]).getSenderId().equals(MyID.getInstance().getMyId())) {
                        Platform.runLater(() -> {
                            createMessage(MessagesQueue.change.get(MessagesQueue.change.keySet().toArray()[0]), 1);
                        });
                    } else {
                        createMessage(MessagesQueue.change.get(MessagesQueue.change.keySet().toArray()[0]), 2);
                    }
                } else if (!currentPane.getText().equals("Notifications")) {
                    notificationCountLabel.setText(++notificationCount + "");
                    notificationCountCircle.setVisible(true);
                    notificationCountLabel.setVisible(true);
                }
            }
        });
    }


    private void checkInvitations() {
        InvitationQueue.getList().addListener(new ListChangeListener<InvitationDto>() {
            @Override
            public void onChanged(Change<? extends InvitationDto> change) {
                if (currentPane.getText().equals("Invitations")) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            createInvitationList();
                        }
                    });
                }
            }
        });
    }

    private void checkContacts() {
        ContactList.getList().addListener(new ListChangeListener<ContactDto>() {
            @Override
            public void onChanged(Change<? extends ContactDto> change) {
                if (currentPane.getText().equals("Contacts")) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            createContactList();
                        }
                    });
                }
            }
        });
    }

    private void checkNotifications() {
        NotificationQueue.getList().addListener(new ListChangeListener<NotificationDto>() {
            @Override
            public void onChanged(Change<? extends NotificationDto> change) {

                if (currentPane.getText().equals("Notifications")) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            createNotificationList();
                        }

                    });
                }
            }
        });
    }


//    private void checkNewGroup() {
//        ChatList.getList().addListener(new ListChangeListener<ChatDto>() {
//            @Override
//            public void onChanged(Change<? extends ChatDto> change) {
//                if (currentPane.getText().equals("Chats")) {
//                    Platform.runLater(new Runnable() {
//                        @Override
//                        public void run() {
//                          createNewGroupPane(change);
//                        }
//                    });
//                }
//            }
//        });
//    }
//    private void createNewGroupPane(ListChangeListener.Change<? extends ChatDto> change) {
//        Pane temp = PaneManager.getPaneManager().putRecentChatCard();
//        ((Label) (temp.lookup("#chatID"))).setText(change.getId()+"");
////            putImageOnPane(chat.getPicture_icon(), temp);
////            putStatusOnPane(ContactList.getList().stream().filter(c-> c.getPhoneNumber() == v.get(v.size()-1).getSenderId()).collect(Collectors.toList()).get(0).getIsOnlineStatus(), temp);
////            putUserNameOnPane(ContactList.getList().stream().filter(c-> c.getPhoneNumber() == v.get(v.size()-1).getSenderId()).collect(Collectors.toList()).get(0).getName(), temp);
//        putUserNameOnPane(change.getName(), temp);
//        putTimeOnPane(change.getModified_on().toString(), temp);
//        chatsObservableList.add(temp);
//
//    }


    private void selectChat() throws NumberFormatException {
        leftList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Pane>() {
            @Override
            public void changed(ObservableValue<? extends Pane> observable, Pane oldValue, Pane newValue) {
                if (newValue != null) {
                    if (currentPane.getText().equals("Contacts") || currentPane.getText().equals("Chats")) {
                        chatVBox.getChildren().clear();
                        if (isChatClosed) {
                            rightAnchorPane.getChildren().remove(rightAnchorPane.getChildren().size() - 1);
                            isChatClosed = false;
                        }

                        if (currentPane.getText().equals("Contacts")) {
                            for (ChatDto chat : ChatList.getList()) {
                                if (chat.getMembersIds().size() == 2 && (chat.getMembersIds().get(0).equals(((Label) (newValue.lookup("#userPhoneNumber"))).getText()) || chat.getMembersIds().get(1).equals(((Label) (newValue.lookup("#userPhoneNumber"))).getText()))) {
                                    currentChat = chat.getId();
                                }
                            }

                        } else if (currentPane.getText().equals("Chats")) {
                            currentChat = Long.parseLong(((Label) (newValue.lookup("#chatID"))).getText());
                        }
                        chatName.setText(((Label) (newValue.lookup("#userName"))).getText());
//                          chatIcon.setFill(new ImagePattern(new Image(Constants.byteArrayToImage(arr, img).getPath(),100,100,false,true)));

                        if (MessagesQueue.getList().containsKey(currentChat)) {
                            for (MessageDto message : MessagesQueue.getList().get(currentChat)) {
                                if (message.getSenderId().equals(userSessionDto.getUser().getId())) {
                                    createMessage(message, 1);
                                } else {
                                    createMessage(message, 2);
                                }
                            }
                        }
                    }

                }
            }
        });
    }

    @FXML
    private void openChats(ActionEvent actionEvent) {
//        NotificationQueue.getList().add(i, new NotificationDto(i++, 012, 011, "add", new Date(40000), "add me", false));
        searchField.setVisible(true);
        contactsButton.setStyle(null);
        invitationsButton.setStyle(null);
        notificationsButton.setStyle(null);
        currentPane.setText("Chats");
        leftList.setId("");
        deleteAddDelContact();
        chatsButton.setStyle("-fx-border-width: 0 0 2px 5px; -fx-border-color: purple;");
        createOneToOneChatList();
    }

    private void createOneToOneChatList() {
        chatsObservableList.clear();
        leftList.setItems(chatsObservableList);
        MessagesQueue.getList().forEach((k, v) -> {
            if(k != -1) {
                Pane temp = PaneManager.getPaneManager().putRecentChatCard();
                ((Label) (temp.lookup("#chatID"))).setText(k.toString());
//            putImageOnPane(chat.getPicture_icon(), temp);
//            putStatusOnPane(ContactList.getList().stream().filter(c-> c.getPhoneNumber() == v.get(v.size()-1).getSenderId()).collect(Collectors.toList()).get(0).getIsOnlineStatus(), temp);
//            putUserNameOnPane(ContactList.getList().stream().filter(c-> c.getPhoneNumber() == v.get(v.size()-1).getSenderId()).collect(Collectors.toList()).get(0).getName(), temp);
                putMessageOnPane(v.get(v.size() - 1).getMessage(), temp);
                putTimeOnPane(v.get(v.size() - 1).getTimestamp(), temp);
                putUserNameOnPane(v.get(v.size() - 1).getSenderId(), temp);
                chatsObservableList.add(temp);
            }
        });

//        chatsObservableList.stream().sorted((p, n) -> Integer.parseInt(((Label) (p.lookup("#timestamp"))).getText()));
    }

    int x = 0;

    @FXML
    private void openContacts(ActionEvent actionEvent) {
//        InvitationQueue.getList().add(new InvitationDto(x++, "012", new UserCardDto(), null, null, false));
        searchField.setVisible(true);
        currentPane.setText("Contacts");
        invitationsButton.setStyle(null);
        chatsButton.setStyle(null);
        notificationsButton.setStyle(null);
        leftList.setId("");
        deleteAddDelContact();
        contactsButton.setStyle("-fx-border-width: 0 0 2px 5px; -fx-border-color: purple;");
        createContactList();

    }

    private void createContactList() {
        contactsObservableList.clear();
        leftList.setItems(contactsObservableList);
        for (ContactDto contact : ContactList.getList()) {
            Pane temp = PaneManager.getPaneManager().putContactCard();
//            putImageOnPane(contact.getPicture(), temp);
            putStatusOnPane(contact.getIsOnlineStatus(), temp);
            putUserNameOnPane(contact.getName(), temp);
            putPhoneNumOnPane(contact.getPhoneNumber(), temp);
            putBioOnPane(contact.getBio(), temp);
            putOnlineStatusOnPane(contact.getIsOnlineStatus(), temp);
//            putImageOnPane(contact,temp);
            contactsObservableList.add(temp);
        }
    }


    @FXML
//    private void openInvitations(ActionEvent actionEvent) {
////        ChatList.getList().add(new ChatDto(9, "habala",null, new Date(50000),null, "OneToOne", null));
//        searchField.setVisible(true);
//        currentPane.setText("Invitations");
//        chatsButton.setStyle(null);
//        contactsButton.setStyle(null);
//        notificationsButton.setStyle(null);
//        leftList.setId("");
//        deleteAddDelContact();
//        invitationsButton.setStyle("-fx-border-width: 0 0 2px 5px; -fx-border-color: purple;");
//        createInvitationList();
//    }

    private void createInvitationList() {
        invitationsObservableList.clear();
        leftList.setItems(invitationsObservableList);
        for (InvitationDto invitation : InvitationQueue.getList()) {
            Pane temp = PaneManager.getPaneManager().putInvitationCard();

//            putImageOnPane(invitation., temp);
            putUserNameOnPane(invitation.getReceiverId(), temp);
//            paneObservableList.add(temp);
            invitationsObservableList.add(temp);
        }

        leftList.setItems(paneObservableList);
    }

    @FXML
    private void openInvitations(ActionEvent actionEvent) {
        searchField.setVisible(true);
        currentPane.setText("Invitations");
        chatsButton.setStyle(null);
        contactsButton.setStyle(null);
        notificationsButton.setStyle(null);
        leftList.setId("");
        deleteAddDelContact();
        invitationsButton.setStyle("-fx-border-width: 0 0 2px 5px; -fx-border-color: purple;");
        paneObservableList.clear();
//        for (InvitationDto invitation : userSessionDto.getInvitationListDto()) {
//            Pane temp = PaneManager.getPaneManager().putInvitationCard();
//            ((Label) (temp.lookup("#invitId"))).setText(invitation.getId() + "");
//            (temp.lookup("#invitId")).setVisible(false);
//            UserCardDto dto = new UserCardDto();
//            ((Label) (temp.lookup("#userName"))).setText(dto.getName().toString());
//            ((Label) (temp.lookup("#invitationDate"))).setText(invitation.getCreatedOn().toString());
//        }

        for (InvitationDto invitation : InvitationQueue.getList()) {
            Pane temp = PaneManager.getPaneManager().putInvitationCard();
            ((Label) (temp.lookup("#invitId"))).setText(invitation.getId() + "");
            (temp.lookup("#invitId")).setVisible(false);
            System.out.println(invitation.getUserCardDto().getImgPath());
//            putImageOnPane(invitation.getUserCardDto().getImgPath(), invitation.getUserCardDto().getImage(), temp);
            putUserNameOnPane(invitation.getUserCardDto().getName(), temp);
            System.out.println("fff");
            Long invitationId = Long.parseLong(((Label) (temp.lookup("#invitId"))).getText());
            JFXButton accept = (JFXButton) (temp.getChildren().get(4));
            System.out.println(" " +accept.getText() + accept.getId());
            accept.setOnAction(e->{
                Registry reg = null;
                System.out.println("Accept invitation" + invitationId);
                try {
                    reg = RMIManager.getRegistry();
                    new InvitationService().acceptInvit(invitationId);
                } catch (RemoteException ex) {
                    System.out.println(ex.getMessage());
                    throw new RuntimeException(ex);
                }
            });

            JFXButton decline = (JFXButton) (temp.getChildren().get(5));
            System.out.println(" " +decline.getText() + decline.getId());
            decline.setOnAction(e->{
                Registry reg = null;
                System.out.println("Decline invitation");
                try {
                    reg = RMIManager.getRegistry();
                    new InvitationService().rejectInvit(invitationId);
                } catch (RemoteException ex) {
                    System.out.println(ex.getMessage());
                    throw new RuntimeException(ex);
                }
            });
            paneObservableList.add(temp);
        }

        leftList.setItems(paneObservableList);
    }

    private void selectInvitation() {
        leftList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Pane>() {
            @Override
            public void changed(ObservableValue<? extends Pane> observable, Pane oldValue, Pane newValue) {
                if(newValue!=null)
                    if((currentPane.getText().equals("Invitation"))) {
                        Long invitationId = Long.parseLong(((Label) (newValue.lookup("#invitId"))).getText());
                        ((JFXButton) (newValue.lookup("#confirInvitation"))).setOnAction(e -> {
                            Registry reg = null;
                            try {
                                reg = RMIManager.getRegistry();
                                new InvitationService().acceptInvit(invitationId);
                            } catch (RemoteException ex) {
                                System.out.println(ex.getMessage());
                                throw new RuntimeException(ex);
                            }

                        });
                        ((JFXButton) (newValue.lookup("#declineInvitation"))).setOnAction(e -> {
                            Registry reg = null;
                            try {
                                reg = RMIManager.getRegistry();
                                new InvitationService().rejectInvit(invitationId);
                            } catch (RemoteException ex) {
                                System.out.println(ex.getMessage());
                                throw new RuntimeException(ex);
                            }
                        });
                    }
    }});
    }
    @FXML
    private void openNotifications(ActionEvent actionEvent) {
        searchField.setVisible(true);
        currentPane.setText("Notifications");
        chatsButton.setStyle(null);
        contactsButton.setStyle(null);
        invitationsButton.setStyle(null);
        deleteAddDelContact();
        leftList.setId("");
        notificationsButton.setStyle("-fx-border-width: 0 0 2px 5px; -fx-border-color: purple;");
        notificationCount = 0;
        notificationCountCircle.setVisible(false);
        notificationCountLabel.setVisible(false);
        createNotificationList();

    }

    private void createNotificationList() {
        notificationObservableList.clear();
        leftList.setItems(notificationObservableList);
        NotificationQueue.getList().forEach(n -> {
            Pane temp = PaneManager.getPaneManager().putNotificationPane();
            ((Label) (temp.lookup("#notificationMessage"))).setText(n.getStatus());
            ((Label) (temp.lookup("#timestamp"))).setText(n.getCreated_on() + "");
            notificationObservableList.add(temp);
        });
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
        putBioOnPane(userSessionDto.getUser().getBio(), temp);
        putUserNameOnPane(userSessionDto.getUser().getName(), temp);
        putPhoneNumOnPane(userSessionDto.getUser().getId(), temp);
        //putIsOnlineStatusOnPane(userSessionDto.getUser().getIsOnlineStatus(), temp);
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
        if(status.equals(Constants.ONLINE_STATUS_OFFLINE))
            ((Circle)(temp.lookup("#statusCircle"))).setFill(Color.RED);
        else if(status.equals(Constants.ONLINE_STATUS_AVAILABLE))
            ((Circle)(temp.lookup("#statusCircle"))).setFill(Color.GREEN);
        else if(status.equals("NA"))
            ((Circle)(temp.lookup("#statusCircle"))).setRadius(0);
    }


    private void putOnlineStatusOnPane(String status, Pane temp) {
        if(status.equals(Constants.ONLINE_STATUS_OFFLINE))
            ((Label)temp.lookup("#onlineStatus")).setText("");
        else
            ((Label)temp.lookup("#onlineStatus")).setText(status);
    }

    private void putBioOnPane(String bio, Pane temp) {
        ((Label)temp.lookup("#bio")).setText(bio);
    }

    private  void putImageOnPane( ContactDto dto,Pane temp) {
        try {
            ((Circle) temp.lookup("#userPic")).setFill(new ImagePattern(new Image(saveUserImage(dto),100,100,false,true)));
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

        contactsButton.setStyle("-fx-border-width: 0 0 2px 5px; -fx-border-color: purple;");
        paneObservableList.clear();
        paneObservableList.add(temp);
        leftList.setItems(paneObservableList);
    }
    @FXML
    void deleteContact(MouseEvent event) {
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
                new InvitationService().sendInvit(MyID.getInstance().getMyId(),tx.getText());
            }
        }
    }

    private void deleteAddDelContact() {
        addIcon.setImage(new Image("image/icons-add.png"));
        addIcon.setDisable(false);
        addContactCard.setImage(null);
        addContactCard.setDisable(true);
        deleteContactCard.setImage(null);
        deleteContactCard.setDisable(true);
        btnAddContacts.setVisible(false);
    }

    public void sendMessage(ActionEvent actionEvent) {
        if(!messageField.getText().trim().isBlank()) {
            MessageDto messageDto = new MessageDto();
            messageDto.setMessage(messageField.getText().trim());
            messageDto.setFont(fonts.getSelectionModel().getSelectedItem());
            messageDto.setBold(true);
            messageDto.setItalic(italic.isSelected());
            messageDto.setUnderline(underline.isSelected());
            messageDto.setTextColor("#" + Integer.toHexString(textColor.getValue().hashCode()));
            messageDto.setHighlightColor("#" + Integer.toHexString(highlight.getValue().hashCode()));
            messageDto.setSenderId(userSessionDto.getUser().getId());
            messageDto.setFontSize((int) (fontSize.getValue()));
            messageDto.setTimestamp(LocalTime.now().getHour() + ":" + LocalTime.now().getMinute() + "");
            try {
                SendMessage.send(currentChat, messageDto);
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (NotBoundException e) {
                e.printStackTrace();
            }
            messageField.setText("");
            Animation animation = new Timeline(new KeyFrame(Duration.seconds(0.5), new KeyValue(rightScrollPane.vvalueProperty(), 1)));
            animation.play();
        }
    }






    public void createMessage(MessageDto messageOptions, int chat) {

//        ImageView imageView = new ImageView(image);
//        imageView.setFitWidth(20);
//        imageView.setFitHeight(20);
        HBox sentMessage = new HBox();

        if (chat == 1) {
            sentMessage.getChildren().addAll(createBubble(messageOptions, Color.web("#6713d2")));
            sentMessage.setAlignment(Pos.BASELINE_RIGHT);
        } else {
            sentMessage.getChildren().addAll(createBubble(messageOptions, Color.web("#cc208e")));
            sentMessage.setAlignment(Pos.BASELINE_LEFT);
        }

        chatVBox.getChildren().add(sentMessage);

    }

    private Group createBubble(MessageDto messageOptions, Color bubbleColor) {

        Text messageTemp = new Text(messageOptions.getMessage());
        messageTemp.setWrappingWidth(250);
        messageTemp.setFont(Font.font(messageOptions.getFont(), messageOptions.getFontSize()));
        messageTemp.setUnderline(messageOptions.isUnderline());
        if (messageOptions.isBold()) {
            messageTemp.setStyle("-fx-font-weight: bold;");
        }
        if (messageOptions.isItalic()) {
            messageTemp.setStyle("-fx-font-weight: italic;");

        }
        int messageWidth = (int) messageTemp.getLayoutBounds().getWidth();
//        System.out.println(messageWidth);
        int messageHeight = (int) messageTemp.getLayoutBounds().getHeight();

        Text timeTemp = new Text(messageOptions.getTimestamp());
        timeTemp.setFont(Font.font("Arial", 10));
        int timeHeight = (int) timeTemp.getLayoutBounds().getHeight();
        int timeWidth = (int) timeTemp.getLayoutBounds().getWidth();

        Text nameTemp = new Text("sender name");
        nameTemp.setFont(Font.font("Arial", 15));
        int nameHeight = (int) nameTemp.getLayoutBounds().getHeight();
        int nameWidth = (int) nameTemp.getLayoutBounds().getWidth();

        Rectangle bubble = new Rectangle();
        if (nameWidth < messageWidth)
            bubble.setWidth(messageWidth + 25);
        else
            bubble.setWidth(nameWidth + 25);

        bubble.setHeight(messageHeight + timeHeight + nameHeight + 15);
        bubble.setArcHeight(30);
        bubble.setArcWidth(30);
        bubble.setFill(bubbleColor);
        bubble.setOpacity(0.3);

        Label nameLabel = new Label("Sender name");
        nameLabel.setFont(Font.font("Arial", 15));
        nameLabel.setTextFill(Color.ORANGE);
        if (messageWidth > nameWidth)
            nameLabel.setTranslateX((bubble.getWidth() - messageWidth) / 2);
        else
            nameLabel.setTranslateX((bubble.getWidth() - nameWidth) / 2);
        nameLabel.setTranslateY(3);

        Label messageLabel = new Label(messageOptions.getMessage());
        messageLabel.setWrapText(true);
        messageLabel.setMaxWidth(250);
        messageLabel.setFont(Font.font(messageOptions.getFont(), messageOptions.getFontSize()));
        messageLabel.setUnderline(messageOptions.isUnderline());
        FontWeight w;
        if (messageOptions.isBold()) {
             w = FontWeight.BOLD;
//
        }
        else {
          w  = FontWeight.NORMAL;
        }

        FontPosture p ;
        if (messageOptions.isItalic()) {
            p = FontPosture.ITALIC;
//            messageLabel.setStyle("-fx-font-weight: bold;");
        }
        else {
          p  = FontPosture.REGULAR;
        }


//

        Font font = Font.font(messageOptions.getFont(), w, p, messageOptions.getFontSize());
        messageLabel.setFont(font);
        if (messageOptions.isItalic()) {
            messageLabel.setStyle("-fx-font-weight: italic;");

        }

        messageLabel.setStyle("-fx-text-fill:" + messageOptions.getTextColor() + ";-fx-background-color:" + messageOptions.getHighlightColor() + ";");
        messageLabel.setTranslateX((bubble.getWidth() - messageWidth) / 2);
        messageLabel.setTranslateY(nameHeight + 7);

        Label timeLabel = new Label(messageOptions.getTimestamp());
        timeLabel.setFont(Font.font("Arial", 10));
        timeLabel.setTextFill(Color.SILVER);
        timeLabel.setTranslateX(bubble.getWidth() - (timeWidth + 10));
        timeLabel.setTranslateY(messageHeight + nameHeight + 10);
        Group group = new Group(bubble, nameLabel, messageLabel, timeLabel);
        return group;
    }

    File file = null;

    public void sendFile(ActionEvent actionEvent) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Select the File you want . . .");
        file = fc.showOpenDialog(null);
        if (file != null) {
            createAttachmentMessage();
        }

    }

    @FXML
    private void logout(){
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.initStyle(StageStyle.UNDECORATED);
        window.setTitle("Confirm");
        window.setMinWidth(250);

        Label label = new Label();
        label.setText("Choose Logout or Exit");

        Button yesButton = new Button("Logout");
        yesButton.setStyle("-fx-background-color: rgba(253,68,68,0.62);");
        yesButton.setMinWidth(150);
        Button noButton = new Button("Cancel");
        noButton.setStyle("-fx-background-color: #00ff5d;");
        noButton.setMinWidth(150);

        yesButton.setOnAction(e -> {
            try {
                LogoutService.logout();
                SceneManager s =SceneManager.getSceneManager();
                s.switchToLoginScene();
            } catch (NotBoundException | RemoteException ex) {
                ex.printStackTrace();
            }
            window.close();
        });

        noButton.setOnAction(e -> {
            window.close();
        });

        HBox hBox = new HBox(10);
        hBox.getChildren().addAll(yesButton, noButton);
        hBox.setAlignment(Pos.CENTER);
        VBox layout = new VBox(20);
        layout.getChildren().addAll(label, hBox);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        layout.setStyle("-fx-border-color: #9900ff; -fx-border-width: 0.5;");

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }

    private void createAttachmentMessage() {
        HBox hbox = new HBox();
        ImageView image = new ImageView(new Image(String.format("/image/%sEX.png", Files.getFileExtension(file.getPath())), 50, 50, false, true));
        image.setTranslateX(5);
        Label fileName = new Label(file.getName());
        fileName.setStyle("-fx-font-size: 12px;  -fx-font-weight: bold; -fx-text-fill: #333333; -fx-text-margin:50px;");
        hbox.setSpacing(20);
        hbox.getChildren().addAll(image, fileName);
        hbox.setMaxWidth(new Text(file.getName()).getLayoutBounds().getWidth() + 100);
        hbox.setMinHeight(70);
        image.setTranslateY(10);
        fileName.setTranslateY(25);
        hbox.setStyle("-fx-background-color: #e9e1e1; -fx-background-radius: 20; -fx-border-radius:20;");
        try {
            new FileTransferService().sendFile(currentChat, userSessionDto.getUser().getId(), file);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
//       chatVBox.getChildren().add(hbox);
        messagesObservableList.add(hbox);
    }

    public void closeChat(ActionEvent actionEvent) {

        rightAnchorPane.getChildren().add(0, new ImageView(new Image(String.format("/image/%s.gif", (int) (Math.random() * (6)) + 1 + ""), 506, 633, false, false)));
        rightAnchorPane.getChildren().get(0).toFront();
        isChatClosed = true;


    }

    public void edit(ActionEvent actionEvent) {
        if(isBarsVisible){
            firstButtonBar.setVisible(false);
            secondButtonBar.setVisible(false);
            isBarsVisible = false;
        }
        else{
            firstButtonBar.setVisible(true);
            secondButtonBar.setVisible(true);
            isBarsVisible = true;

        }
    }

    public String  saveUserImage(ContactDto dto) throws IOException {
        String path = Constants.USER_IMAGES_DIR +dto.getImgPath();
        Constants.byteArrayToImage(dto.getImage(), URLDecoder.decode(path, "UTF-8"));
        return URLDecoder.decode(path, "UTF-8");
    }
    public String  saveUserImage(UserDto dto) throws IOException {
        String path = Constants.USER_IMAGES_DIR +dto.getImgPath();
        Constants.byteArrayToImage(dto.getImage(), URLDecoder.decode(path, "UTF-8"));
        return URLDecoder.decode(path, "UTF-8");
    }
}

