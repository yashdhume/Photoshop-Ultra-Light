package VIPLogin;

import java.io.*;
import java.net.*;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class Client {
    public Client(){}
    // Declare IO streams
    private ObjectOutputStream toServer = null;
    private ObjectInputStream fromServer = null;

    private void initalize(){
        try {
            // Create a socket to connect to the server
            Socket socket = new Socket("localhost", 8000);

            // Create an input stream to receive data from the server
            fromServer = new ObjectInputStream(socket.getInputStream());

            // Create an output stream to send data to the server
            toServer = new ObjectOutputStream(socket.getOutputStream());

        }
        catch (IOException ex) {
            System.out.println(ex.toString() + '\n');
        }
    }

    public GridPane start() {
        initalize();

        try {
            String a = "1";
            toServer.writeUTF(a);
        } catch (Exception e) {
            e.printStackTrace();
        }

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(30));
        gridPane.setHgap(20);
        gridPane.setVgap(20);

        Text txtUserName = new Text("Username");
        TextField txtFieldUserName = new TextField();
        Text txtPassword = new Text("Password");
        PasswordField passwordFieldPassword = new PasswordField();

        Button btn = new Button("Login");
        Button btnRegister = new Button("Register");

        // handle action event
        btn.setOnAction(e -> btnAction(txtFieldUserName,passwordFieldPassword,false));
        btnRegister.setOnAction(e -> btnAction(txtFieldUserName,passwordFieldPassword,true));

        HBox hbox = new HBox(20);
        hbox.getChildren().addAll(btn, btnRegister);

        gridPane.add(txtUserName,0,0);
        gridPane.add(txtFieldUserName,1,0);
        gridPane.add(txtPassword,0,1);
        gridPane.add(passwordFieldPassword,1,1);
        gridPane.add(hbox, 1,2);

        return gridPane;
    }

    private  void btnAction(TextField textField, PasswordField passwordField, boolean isRegistering){
        try {
            // Get the radius from the text field
            String username = textField.getText();
            String password = passwordField.getText();
            Encrpt encrpt = new Encrpt();
            String encrptedPassword = encrpt.encrypt(password.getBytes(),password.length());

            // Send data to the server
            toServer.writeUTF(username);
            toServer.writeUTF(encrptedPassword);
            toServer.writeBoolean(isRegistering);
            toServer.flush();
        }
        catch (Exception ex) {
            System.err.println(ex);
        }
    }
}