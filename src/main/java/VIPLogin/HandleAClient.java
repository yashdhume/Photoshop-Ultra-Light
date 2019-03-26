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
            String whichClient = "";
            try {
                whichClient = inputFromClient.readUTF();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (whichClient.equals("2")){
                Server server = new Server();
                userPassData = server.readFile();
                outputToClient.writeObject(userPassData);
            }
            AlertDialogue alert = new AlertDialogue();
            // Continuously serve the client
            while (true) {
                if (whichClient.equals("1")) {
                    String username = inputFromClient.readUTF();
                    String password = inputFromClient.readUTF();
                    boolean isRegistering = inputFromClient.readBoolean();
                    Server server = new Server();
                    Platform.runLater(() -> {
                        userPassData = server.readFile();
                        boolean isRegistered = false;
                        if (isRegistering) {
                            isRegistered = store(username, password);
                        }
                        else if(userPassData!=null){
                            for (Account account: userPassData) {
                               if(account.getUsername().equals(username)&&account.getPassword().equals(password)){
                                   account.setLoggedIn(true);
                               }
                               else if (account.getUsername().equals(username)&&!account.getPassword().equals(password)){
                                   System.out.println(account.getUsername());
                                   alert.getAlert(new Exception("User Name or Password doesn't Exist"));
                               }
                            }
                            server.saveFile(userPassData);
                        }
                        if (isRegistered) {
                            alert.getAlert(new Exception("User is already registered"));
                        }
                        server.saveFile(userPassData);
                    });
                }
            }
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }
    private boolean store(String userName, String password){
        Account account = new Account(userName, password,false, false);
        if(!userPassData.contains(account)){
            userPassData.add(account);
            return false;
        } else return true;
    }
}