package gov.iti.jets.client.Dina;

public class GroupList {
    private static GroupList groupList;
    public static GroupList getList(){
        if(groupList == null)
            groupList = new GroupList();

        return groupList;
    }
}
