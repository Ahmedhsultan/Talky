package gov.iti.jets.client.network.service;

import gov.iti.jets.client.Queues.MyID;
import gov.iti.jets.common.network.server.UserRemote;
import gov.iti.jets.common.util.Constants;

import java.io.File;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class LogoutService {
    public static void logout() throws NotBoundException, RemoteException {
        deleteCash();
        UserRemote userRemote = RMIManager.lookUpRegister();
        userRemote.logout(MyID.getInstance().getMyId());
        //stop Online pulling service
        PullOnlineUsersFromServer.getInstance().stopService();
    }

    private static void deleteCash(){
        File file = new File(Constants.MAIN_DIR + "\\" + Constants.CASH_FILE_NAMW);
        if (file.delete()) {
            System.out.println("File deleted successfully");
        } else {
            System.out.println("Error while deleting file");
        }
    }
}
