package VIPLogin;

import javafx.application.Platform;
import javafx.scene.control.TextArea;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

class HandleAClient implements Runnable {
    private Socket socket; // A connected socket
    private TextArea ta;
    private ArrayList<Account> userPassData;

    public HandleAClient(Socket socket, TextArea ta) {
        this.socket = socket;
        this.ta = ta;
    }

    public void run() {
        try {
            // Create data input and output streams
            ObjectOutputStream outputToClient = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream inputFromClient = new ObjectInputStream(socket.getInputStream());

            // Continuously serve the client
            while (true) {
                String temp = "";
                try {
                    temp = inputFromClient.readUTF();
                } catch (Exception e) {
                    e.printStackTrace();
                }

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
                            ta.appendText("Already Registered");
                        } else {
                            Account new_account = new Account(username, password, false);
                            userPassData.add(new_account);
                        }
                        ta.appendText(username + '\n');
                        server.saveFile(userPassData);
                    });
                } else if (temp.equals("2")){
                    Server server = new Server();
                    userPassData = server.readFile();
                    outputToClient.writeObject(userPassData);
                }
            }
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }
}