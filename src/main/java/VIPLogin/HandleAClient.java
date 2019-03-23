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
                String shit = "";
                try {
                    System.out.println("f");
                    shit = inputFromClient.readUTF();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(shit);
                if ( shit.equals("1")) {
                    String username = inputFromClient.readUTF();
                    String password = inputFromClient.readUTF();
                    boolean isRegistering = inputFromClient.readBoolean();
                    Server server = new Server();
                    userPassData = server.readFile();
                    Encrpt encrpt = new Encrpt();
                    String deencrptedPassword = encrpt.encrypt(password.getBytes(), password.length());

                    //  out.writeUTF("Registed");
                    Platform.runLater(() -> {
                        boolean isRegistered = false;
                        if (isRegistering) {
                            isRegistered = server.store(username, password);
                        }
                       /* else {
                            if (userPassData.contains(username)) {
                                Account account = new Account();
                                if (userPassData.get(username).equals(password)) {
                                    System.out.println(userPassData.get(username));
                                    System.out.println("YEETBOI");
                                    //TODO ADD VIP FUNCTIONALITY HERE
                                }
                            }
                        }*/
                        if (isRegistered) {
                            ta.appendText("Already Registed");
                        }
                        ta.appendText(username + '\n');
                        /*ta.appendText("Encrpted Password: " + password + '\n');
                        ta.appendText("Decrepted Password: " + deencrptedPassword + '\n');
                        ta.appendText("Registeing: " + isRegistering+ '\n');*/
                        /*for (String name: userPassData)){
                            String keys =name;
                            String value = userPassData.get(name);
                            ta.appendText(keys + " " + value);
                        }*/
                        server.saveFile(userPassData);
                    });
                }
                else if (shit.equals("2")){
                    System.out.println(4);
                    Server server = new Server();
                    userPassData = server.readFile();
                    System.out.println(5);
                    System.out.println(userPassData.size());
                    outputToClient.writeObject(userPassData);
                }
            }
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }
    }
}