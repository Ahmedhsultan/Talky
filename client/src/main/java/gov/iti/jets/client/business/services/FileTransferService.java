package gov.iti.jets.client.business.services;

import gov.iti.jets.client.network.service.RMIManager;
import gov.iti.jets.common.network.server.IServer;
import gov.iti.jets.common.util.Constants;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class FileTransferService {

    public void sendFile(long chatId, String senderId, File file) throws RemoteException {

        try (SeekableByteChannel fChan = Files.newByteChannel(Path.of(file.getPath()))) {
            IServer iServer = RMIManager.lookUpIServer();
            ByteBuffer mBuf = ByteBuffer.allocate(Constants.DEFAULT_BUFFER_SIZE);
            int count;
            do {
                count = fChan.read(mBuf);
                if (count != -1) {
                    mBuf.rewind();
                    iServer.sendFile(chatId, senderId, mBuf.array(), file.getName());
                }
            } while (count != -1);

        } catch (IOException e) {
            e.printStackTrace();
            throw new RemoteException("Failed to send File!!");
        } catch (NotBoundException e) {
            e.printStackTrace();
            throw new RemoteException("Failed to send File!!");
        }
    }
}
