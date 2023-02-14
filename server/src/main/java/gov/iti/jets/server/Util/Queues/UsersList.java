package gov.iti.jets.server.Util.Queues;

import gov.iti.jets.common.dto.UserDto;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UsersList {
    private ObservableList<UserDto> userList;
//    private   ObservableList<UserDto> offlineList;


    private static UsersList usersList;
    public  static UsersList getInstance(){
        synchronized (UsersList.class){
            if(usersList == null) {
                usersList = new UsersList();
                usersList.userList= FXCollections.observableArrayList();
//                usersStats.offlineList= FXCollections.observableArrayList();

            }
        }
        return usersList;
    }

    public ObservableList<UserDto> getUserList() {
        return userList;
    }

//    public ObservableList<UserDto> getOfflineList() {
//        return offlineList;
//    }

//    public void updateOnlineStats()
//    {
//        Platform.runLater(()->{
//            UsersStats.getInstance().getOnlineList().clear();
//            UsersStats.getInstance().getOnlineList().add(new UserDto());
//        });
//    }

    public void updateOnlineAndOfflineStats()
    {
        Platform.runLater(()->{
            UsersList.getInstance().getUserList().clear();
            UsersList.getInstance().getUserList().add(new UserDto());

        });
    }


}
