package gov.iti.jets.client.presentation.controllers;

import gov.iti.jets.client.Queues.*;
import gov.iti.jets.client.Util.InviteWindow;
import gov.iti.jets.client.Util.NotificationManager;
import gov.iti.jets.client.Util.NotificationIcon;
import gov.iti.jets.client.business.services.PaneManager;
import gov.iti.jets.client.business.services.SceneManager;
import gov.iti.jets.client.network.service.*;
import gov.iti.jets.common.dto.ContactDto;
import gov.iti.jets.common.dto.InvitationDto;
import gov.iti.jets.common.dto.MessageDto;
import gov.iti.jets.common.dto.NotificationDto;
import gov.iti.jets.common.util.Constants;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.scene.image.*;
import javafx.stage.StageStyle;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private VBox main;
    @FXML
    private AnchorPane notification;
    @FXML
    private ScrollPane mainPane;
    @FXML
    private ImageView mainImage;
    @FXML
    private Text mainName;
    @FXML
    private TextField message;
    @FXML
    private Text send;
    @FXML
    private VBox chat;
    @FXML
    private ImageView myPic;
    @FXML
    private ImageView chats;
    @FXML
    private ImageView Invitations;
    @FXML
    private ImageView notificaions;
    @FXML
    private ImageView logout;


    private Long currentChatId;
    String currentList = "contacts";
    PaneManager paneManager = new PaneManager();
    private Integer numOfNotification = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ContactDto myContactDto = ContactList.getList().stream().filter(y -> y.getId().equals(MyID.getInstance().getMyId())).toList().get(0);
        myPic.setImage(new Image(new ByteArrayInputStream(myContactDto.getImage())));
        main.setSpacing(10);
        main.setPadding(new Insets(10,10,10,10));

        NotificationQueue.getList().addListener(new ListChangeListener<NotificationDto>() {
            @Override
            public void onChanged(Change<? extends NotificationDto> change) {
                while (change.next())
                    for (var item : change.getAddedSubList()) {
                        Platform.runLater(() -> NotificationManager.createNotification(item, notification));
                    }
            }
        });

        openChats();
    }


    @FXML
    private void sendByEnter(KeyEvent event){
        if (event.getCode() == KeyCode.ENTER) {
            send();
        }
    }

    @FXML
    public void close() {
        Stage stage = (Stage) main.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void send() {
        if (!message.getText().trim().isBlank()) {
            MessageDto messageDto = new MessageDto();
            messageDto.setMessage(message.getText().trim());
//            messageDto.setFont(fonts.getSelectionModel().getSelectedItem());
//            messageDto.setBold(bold.isSelected());
//            messageDto.setItalic(italic.isSelected());
//            messageDto.setUnderline(underline.isSelected());
//            messageDto.setTextColor("#" + Integer.toHexString(textColor.getValue().hashCode()));
//            messageDto.setHighlightColor("#" + Integer.toHexString(highlight.getValue().hashCode()));
            messageDto.setSenderId(MyID.getInstance().getMyId());
//            messageDto.setFontSize((int) (fontSize.getValue()));
            messageDto.setTimestamp(LocalTime.now().getHour() + ":" + LocalTime.now().getMinute() + "");
            try {
                SendMessage.send(currentChatId, messageDto);
                message.setText("");
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (NotBoundException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void openChats(){
        currentList = "contacts";
        putToContact();

        ContactList.getList().addListener((ListChangeListener<? super ContactDto>) change -> {
            putToContact();
        });
        MessagesQueue.getList().addListener((MapChangeListener.Change<? extends Long, ? extends ObservableList<MessageDto>> change) -> {
            putMessages();
            for (var item : MessagesQueue.change.entrySet()){
                String senderId = item.getValue().getSenderId();
                if (!senderId.equals(MyID.getInstance().getMyId())){
                    ContactDto contactDto = ContactList.getList().stream().filter(x -> x.getId().equals(senderId)).toList().get(0);

                    NotificationDto notificationDto = new NotificationDto();
                    notificationDto.setType("message");
                    notificationDto.setName(contactDto.getName());
                    notificationDto.setBytes(contactDto.getImage());
                    notificationDto.setMsg(item.getValue().getMessage());
                    NotificationQueue.getList().add(notificationDto);
                    MessagesQueue.change.clear();
//                    NotificationManager.createNotification(notificationDto, notification);
                }
            }
        });
    }
    @FXML
    private void openInvitation(){
        currentList = "invitations";

        InvitationQueue.getList().addListener((ListChangeListener<? super InvitationDto>) change -> {
            putToInvitation();
        });
        putToInvitation();
    }
    @FXML
    private void openNotification(){
        NotificationManager.clearNotificationIcon();
        Stage window = new Stage();
        ScrollPane scrollPane = new ScrollPane();
        VBox vBox = new VBox();
        for (NotificationDto notificationDto : NotificationQueue.getList()){
            Pane card = PaneManager.getPaneManager().putContactCard();
            ((Text) card.lookup("#name")).setText(notificationDto.getName());
            if (notificationDto.getType().equals("message"))
                ((Text) card.lookup("#msg")).setText(notificationDto.getMsg());
            else
                ((Text) card.lookup("#msg")).setText(notificationDto.getOnlineStatus());
            ((ImageView) card.lookup("#pic")).setImage(new Image(new ByteArrayInputStream(notificationDto.getBytes())));

            vBox.getChildren().add(card);
        }

        window.setX(notificaions.localToScreen(notificaions.getBoundsInLocal()).getMinX());
        window.setY(notificaions.localToScreen(notificaions.getBoundsInLocal()).getMinY());
        window.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
            } else {
                window.close();
            }
        });
        scrollPane.setContent(vBox);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        Scene scene = new Scene(scrollPane, 200, 300);
        window.setResizable(false);
        window.initStyle(StageStyle.UNDECORATED);
        window.setScene(scene);
        window.showAndWait();
    }

    private void putToInvitation(){
        Platform.runLater(() -> main.getChildren().clear());

        Pane putInvitationButton = paneManager.putInvitationButton();
        ((Button) putInvitationButton.lookup("#addContact")).addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // Code to handle the button click event
                InviteWindow inviteWindow = new InviteWindow();
            }
        });
        Platform.runLater(() -> main.getChildren().add(putInvitationButton));

        InvitationQueue.getList().forEach(invitation -> {
            Pane invitationCard = paneManager.putInvitationCard();

            ((Text) invitationCard.lookup("#name")).setText(invitation.getUserCardDto().getName());
            ((ImageView) invitationCard.lookup("#pic")).setImage(new Image(new ByteArrayInputStream(invitation.getUserCardDto().getImage())));

            ((Button) invitationCard.lookup("#accept")).addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    // Code to handle the button click event
                    new  InvitationService().acceptInvit(invitation.getId());
                }
            });
            ((Button) invitationCard.lookup("#reject")).addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    // Code to handle the button click event
                    new InvitationService().rejectInvit(invitation.getId());
                }
            });


            Platform.runLater(() -> main.getChildren().add(invitationCard));
        });
    }

    private void putToContact() {
        if (currentList.equals("contacts")){
            Platform.runLater(() -> main.getChildren().clear());

            ChatList.getList().forEach(chat -> {
            if (chat.getType().equals(Constants.CHAT_ONE_TO_ONE)) {
                for (String membersId : chat.getMembersIds()) {
                    if (!membersId.equals(MyID.getInstance().getMyId())) {
                        ContactDto contactDto = ContactList.getList().stream().filter(y -> y.getId().equals(membersId)).toList().get(0);
                        Pane contactCard = paneManager.putContactCard();
                        ((Text) contactCard.lookup("#name")).setText(contactDto.getName());
                        String msg = "";
                        if (MessagesQueue.getList().containsKey(chat.getId()) && MessagesQueue.getList().get(chat.getId()).size() > 0) {
                            msg = MessagesQueue.getList().get(chat.getId()).get(MessagesQueue.getList().get(chat.getId()).size() - 1).getMessage();
                        }
                        ((Text) contactCard.lookup("#msg")).setText(msg);
                        if (contactDto.getIsOnlineStatus().equals(Constants.ONLINE_STATUS_AVAILABLE)) {
                            ((Circle) contactCard.lookup("#status")).setFill(Color.GREEN);
                        }
                        else {
                            ((Circle) contactCard.lookup("#status")).setFill(Color.RED);
                        }

                        ((ImageView) contactCard.lookup("#pic")).setImage(new Image(new ByteArrayInputStream(contactDto.getImage())));

                        Platform.runLater(() ->  contactCard.setOnMouseClicked(event -> putOnMainChat(contactDto, chat.getId())));
                        contactCard.fireEvent(new javafx.scene.input.MouseEvent(javafx.scene.input.MouseEvent.MOUSE_CLICKED, 0,
                                0, 0, 0, javafx.scene.input.MouseButton.PRIMARY, 1, true, true, true, true,
                                true, true, true, true, true, true, null));
                        Platform.runLater(() -> main.getChildren().add(contactCard));
                    }
                }
            }
            });
        }
    }

    private void putOnMainChat(ContactDto contactDto, Long chatId) {
        chat.getChildren().clear();
        mainImage.setImage(new Image(new ByteArrayInputStream(contactDto.getImage())));
        mainName.setText(contactDto.getName());
        currentChatId = chatId;
        putMessages();
    }

    private void putMessages() {
        Platform.runLater(() -> {
            chat.getChildren().clear();
            if (MessagesQueue.getList().containsKey(currentChatId))
                for (MessageDto messageDto : MessagesQueue.getList().get(currentChatId)) {
                    Pane chatCard;
                    HBox hBox = new HBox();
                    ContactDto contactDto;
                    if (messageDto.getSenderId().equals(MyID.getInstance().getMyId())){
                        chatCard = PaneManager.getPaneManager().putRightRecentChatCard();
                        hBox.setAlignment(Pos.CENTER_RIGHT);
                        contactDto = ContactList.getList().stream().filter(y -> y.getId().equals(MyID.getInstance().getMyId())).toList().get(0);
                    }else{
                        chatCard = PaneManager.getPaneManager().putRecentChatCard();
                        contactDto = ContactList.getList().stream().filter(y -> y.getId().equals(messageDto.getSenderId())).toList().get(0);
                    }
                    ((Text) chatCard.lookup("#msg")).setText(messageDto.getMessage());
                    ((ImageView) chatCard.lookup("#pic")).setImage(new Image(new ByteArrayInputStream(contactDto.getImage())));
                    hBox.getChildren().add(chatCard);
                    chat.getChildren().add(hBox);
                }
        });
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
                SceneManager s = SceneManager.getSceneManager();
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

        layout.setStyle("-fx-border-color: #3C9AEC; -fx-border-width: 0.5;");

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }

//    private void createAttachmentMessage() {
//        HBox hbox = new HBox();
//        ImageView image = new ImageView(new Image(String.format("/image/%sEX.png", Files.getFileExtension(file.getPath())), 50, 50, false, true));
//        image.setTranslateX(5);
//        Label fileName = new Label(file.getName());
//        fileName.setStyle("-fx-font-size: 12px;  -fx-font-weight: bold; -fx-text-fill: #333333; -fx-text-margin:50px;");
//        hbox.setSpacing(20);
//        hbox.getChildren().addAll(image, fileName);
//        hbox.setMaxWidth(new Text(file.getName()).getLayoutBounds().getWidth() + 100);
//        hbox.setMinHeight(70);
//        image.setTranslateY(10);
//        fileName.setTranslateY(25);
//        hbox.setStyle("-fx-background-color: #e9e1e1; -fx-background-radius: 20; -fx-border-radius:20;");
//        try {
//            new FileTransferService().sendFile(currentChat, MyID.getInstance().getMyId(), file);
//        } catch (RemoteException e) {
//            e.printStackTrace();
//        }
////       chatVBox.getChildren().add(hbox);
//        messagesObservableList.add(hbox);
//    }
}
