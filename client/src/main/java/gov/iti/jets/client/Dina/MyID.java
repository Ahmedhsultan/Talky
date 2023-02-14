package gov.iti.jets.client.Dina;

public class MyID {
    private static MyID instance;
    private String myId;
    private String password;

    private MyID(String myId,String password){
        this.myId = myId;
        this.password = password;
    }

    public static MyID getInstance(String... myIdAndPassword){
        if (myIdAndPassword.length == 2)
            instance = null;
        if(instance == null)
            instance = new MyID(myIdAndPassword[0],myIdAndPassword[1]);

        return instance;
    }
    public String getMyId(){
        return myId;
    }
    public String getPassword(){
        return password;
    }
}
