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
    Server server;
    public HandleAClient(Socket socket, TextArea ta) {
        this.socket = socket;
        this.ta = ta;
        server = new Server();
        userPassData = server.readFile();
    }

    public void run() {
        try {
            // Create data input and output streams
            ObjectOutputStream outputToClient = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream inputFromClient = new ObjectInputStream(socket.getInputStream());
            String whichClient = "";
            int counter = 0;
            try {
                whichClient = inputFromClient.readUTF();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (whichClient.equals("2")){
                userPassData = server.readFile();
                outputToClient.writeObject(userPassData);
            }
            // Continuously serve the client
            while (true) {
                if ( whichClient.equals("1")) {
                    String username = inputFromClient.readUTF();
                    String password = inputFromClient.readUTF();
                    boolean isRegistering = inputFromClient.readBoolean();
                    userPassData = server.readFile();

                    Platform.runLater(() -> {
                        boolean isRegistered = false;
                        if (isRegistering) {
                            isRegistered = store(username, password);
                        }
                       else {
/*                            if (userPassData.contains(account)) {

                            }*/
                        }
                        if (isRegistered) {
                            ta.appendText("Already Registed");
                        }
                        ta.appendText(username + '\n');
                        server.saveFile(userPassData);
                    });

                }
                Thread.yield();
            }
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }
    }
    public boolean store(String userName, String password){
        Account account = new Account(userName, password,false);
        if(!userPassData.contains(account)){
            userPassData.add(account);
            return false;
        }
        else return true;
    }
}