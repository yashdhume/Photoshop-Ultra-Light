package VIPLogin;

import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

import Global.AlertDialogue;
import Global.SwitchButton;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class Admin {

    public Admin() {
    }

    // IO streams
    private ArrayList<Account> accounts;
    // IO streams
    private ObjectOutputStream toServer = null;
    // DataInputStream fromServer = null;
    private ObjectInputStream fromServer = null;

    private TextArea ta = new TextArea();

    public BorderPane start() {

        initalize();
        try {
            String a = "2";
            toServer.writeUTF(a);
            toServer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
        GridPane gridPane = new GridPane();

        BorderPane mainPane = new BorderPane();
        // Text area to display contents

        try {
            accounts = (ArrayList<Account>) fromServer.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(accounts.size());
        ArrayList<SwitchButton> switchButtons = new ArrayList<>();
        int count = 0;
        ArrayList<Text> userNames = new ArrayList<>();
        for (Account account : accounts) {
            userNames.add(new Text(account.getUsername()));
            gridPane.add(userNames.get(count), 0, count);
            SwitchButton button = new SwitchButton(account);
            switchButtons.add(button);
            gridPane.add(switchButtons.get(count), 1, count);
            count++;
        }

        // mainPane.setCenter(new ScrollPane(ta));
        mainPane.setTop(gridPane);

        return mainPane;


    }

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
}