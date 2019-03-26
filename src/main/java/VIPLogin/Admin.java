package VIPLogin;
//Admin client
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import Global.AlertDialogue;
import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class Admin {
    public Admin() {}

    // IO streams
    private ArrayList<Account> accounts;
    // IO streams
    private ObjectOutputStream toServer = null;
    // DataInputStream fromServer = null;
    private ObjectInputStream fromServer = null;
    //inialize the sockets and streams
    private void initalize() {
        try {
            Socket socket = new Socket("localhost", 8000);
            toServer = new ObjectOutputStream(socket.getOutputStream());
            fromServer = new ObjectInputStream(socket.getInputStream());
        } catch (IOException ex) {
            AlertDialogue alertDialogue = new AlertDialogue();
            alertDialogue.getAlert(ex);
        }
    }
    //Display all the users and a switch for the vip or not
    public BorderPane start(Server server) {
        initalize();
        try {
            String a = "2";
            toServer.writeUTF(a);
            toServer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(30));
        gridPane.setVgap(20);
        gridPane.setHgap(20);
        BorderPane mainPane = new BorderPane();

        try {
            accounts = (ArrayList<Account>) fromServer.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }

        int count = 0;
        ArrayList<SwitchButton> switchButtons = new ArrayList<>();
        ArrayList<Text> userNames = new ArrayList<>();

        for (Account account : accounts) {
            userNames.add(new Text(account.getUsername()));
            gridPane.add(userNames.get(count), 0, count);
            SwitchButton button = new SwitchButton(count, accounts, server);
            switchButtons.add(button);
            gridPane.add(switchButtons.get(count), 1, count);
            count++;
        }

        mainPane.setTop(gridPane);

        return mainPane;
    }

}