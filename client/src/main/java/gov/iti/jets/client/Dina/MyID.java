package gov.iti.jets.client.Dina;

public class MyID {
    private static MyID instance;
    private final String myId;

    private MyID(String myId){
        this.myId = myId;
    }

    public static MyID getInstance(String... myId){
        if(instance == null)
            instance = new MyID(myId[0]);

        return instance;
    }
    public String getMyId(){
        return myId;
    }
}
