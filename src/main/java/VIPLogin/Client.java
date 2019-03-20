package VIPLogin;

import java.io.*;
import java.net.*;

import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
public class Client {
    public Client(){}
    // IO streams
    DataOutputStream toServer = null;
    // DataInputStream fromServer = null;
    DataInputStream fromServer = null;
    TextArea ta = new TextArea();
    public BorderPane start() {
        GridPane gridPane = new GridPane();
        Text txtUserName = new Text("Username");
        TextField txtFieldUserName = new TextField();
        Text txtPassword = new Text("Password");
        PasswordField passwordFieldPassword = new PasswordField();
        Button btn = new Button("Login");
        Button btnRegister = new Button("Register");
        gridPane.add(txtUserName,0,0);
        gridPane.add(txtFieldUserName,1,0);
        gridPane.add(txtPassword,0,1);
        gridPane.add(passwordFieldPassword,1,1);
        gridPane.add(btn, 2,0);
        gridPane.add(btnRegister, 2,1);
        BorderPane mainPane = new BorderPane();
        // Text area to display contents

        mainPane.setCenter(new ScrollPane(ta));
        mainPane.setTop(gridPane);

        // Create a scene and place it in the stage
       /* Scene scene = new Scene(mainPane, 450, 200);
        primaryStage.setTitle("Client"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage*/

        // handle action event
        btn.setOnAction(e -> btnAction(txtFieldUserName,passwordFieldPassword,false));
        btnRegister.setOnAction(e -> btnAction(txtFieldUserName,passwordFieldPassword,true));

        try {
            // 3. Create a socket to connect to the server
            Socket socket = new Socket("localhost", 8000);

            // 4. Create an input stream to receive data from the server
            // fromServer = new DataInputStream(socket.getInputStream());
            fromServer = new DataInputStream(socket.getInputStream());
            // 5. Create an output stream to send data to the server
            toServer = new DataOutputStream(socket.getOutputStream());

        }
        catch (IOException ex) {
            ta.appendText(ex.toString() + '\n');
        }
        return mainPane;
    }
    private  void btnAction(TextField textField, PasswordField passwordField, boolean isRegistering){
        try {
            // Get the radius from the text field
            String username = textField.getText();
            String password = passwordField.getText();
            String key = "Yash Dhume";
            Encrpt encrpt = new Encrpt();
            String encrptedPassword = encrpt.encrypt(password.getBytes(),password.length());
            // 1. Send the radius to the server
            toServer.writeUTF(username);
            toServer.writeUTF(encrptedPassword);
            toServer.writeBoolean(isRegistering);
            toServer.flush();

            // 2. Get area from the server
            //   Object obj = fromServer.readObject();

            //String input =fromServer.readUTF();
            ta.appendText("Username: " + username + "\n");
            ta.appendText("Password: " + encrptedPassword + "\n");
            // ta.appendText("Valid: " + input + "\n");
        }
        catch (Exception ex) {
            System.err.println(ex);
        }
    }
}