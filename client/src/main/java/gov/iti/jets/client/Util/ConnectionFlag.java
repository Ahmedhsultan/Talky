package gov.iti.jets.client.Util;


public class ConnectionFlag {
    private static ConnectionFlag connectionFlag;
    public Boolean connectedFlag;

    private ConnectionFlag(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    try {
                        ConnectionFlag.getInstance().connectedFlag = false;
                        Thread.sleep(40000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }).start();
    }

    public static ConnectionFlag getInstance(){
        if(connectionFlag == null)
            connectionFlag = new ConnectionFlag();

        return connectionFlag;
    }
}
