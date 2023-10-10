import java.io.IOException;

public class Demo {
    public static void main(String[] args) throws IOException {
        WorkWithAPI.sendPOST();
        WorkWithAPI.sendPUT();
        WorkWithAPI.sendDELETE();
        WorkWithAPI.sendGET();
        WorkWithAPI.sendGETbyId(4);
        WorkWithAPI.sendGETbyUserName();
        TypicodeAPI.getComments(2);
        TypicodeAPI.getComments(6);
        TypicodeAPI.getToDo(2);


        // This is work with Pets not for homework. It's to myself
        RelWithAPI.sendGET();
        RelWithAPI.sendPOST();
        RelWithAPI.sendGETbyId();
        RelWithAPI.sendPUT();
        RelWithAPI.sendGETbyId();

    }

}
