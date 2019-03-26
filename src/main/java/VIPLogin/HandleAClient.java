package VIPLogin;

import Global.AlertDialogue;
import javafx.application.Platform;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

class HandleAClient implements Runnable {
    private Socket socket; // A connected socket
    private ArrayList<Account> userPassData;

    public HandleAClient(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            // Create data input and output streams
            ObjectOutputStream outputToClient = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream inputFromClient = new ObjectInputStream(socket.getInputStream());
            String temp = "";
            try {
                temp = inputFromClient.readUTF();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (temp.equals("2")){
                Server server = new Server();
                userPassData = server.readFile();
                outputToClient.writeObject(userPassData);
            }
            // Continuously serve the client
            while (true) {


                if (temp.equals("1")) {
                    String username = inputFromClient.readUTF();
                    String password = inputFromClient.readUTF();
                    boolean isRegistering = inputFromClient.readBoolean();
                    Server server = new Server();
                    userPassData = server.readFile();
                    Platform.runLater(() -> {
                        boolean isRegistered = false;
                        if (isRegistering) {
                            isRegistered = server.store(username, password);
                        }
                        if (isRegistered) {
                            AlertDialogue alert = new AlertDialogue();
                            alert.getAlert(new Exception("User is already registered"));
                        } else {
                            Account new_account = new Account(username, password, false);
                            userPassData.add(new_account);
                        }

                        server.saveFile(userPassData);
                    });
                }
            }
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }
}