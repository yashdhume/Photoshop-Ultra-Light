package VIPLogin;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Date;
import javafx.application.Platform;
import javafx.scene.control.TextArea;

public class Server {
    private TextArea ta = new TextArea();
    private int clientNo = 0;
    private ArrayList<Account> userPassData;

    public Server(){
        userPassData =  readFile();
    }
    public TextArea start(){
        new Thread( () -> {
            try {
                // Create SumIntegers server socket
                ServerSocket serverSocket = new ServerSocket(8000);
                ta.appendText("MultiThreadServer started at "
                        + new Date() + '\n');

                while (true) {
                    // Listen for SumIntegers new connection request
                    Socket socket = serverSocket.accept();

                    // Increment clientNo
                    clientNo++;

                    Platform.runLater( () -> {
                        // Display the client number
                        ta.appendText("Starting thread for client " + clientNo + " at " + new Date() + '\n');

                        // Find the client's host name, and IP address
                        InetAddress inetAddress = socket.getInetAddress();
                        ta.appendText("Client " + clientNo + "'s host name is " + inetAddress.getHostName() + "\n");
                        ta.appendText("Client " + clientNo + "'s IP Address is " + inetAddress.getHostAddress() + "\n");
                    });

                    // Create and start SumIntegers new thread for the connection
                    new Thread(new HandleAClient(socket,ta)).start();
                }
            }
            catch(IOException ex) {
                System.err.println(ex);
            }
        }).start();
        return  ta;
    }
    public boolean store(String userName, String password){
        Account account = new Account(userName, password,false);
        if(!userPassData.contains(account)){
            userPassData.add(account);
            return false;
        }
        else return true;
    }

    private final String PATH = "src/main/resources/userPasswordData.txt";
    public void saveFile(ArrayList<Account> users) {
        try {
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(new File(PATH).getAbsoluteFile()));
            os.writeObject(users);
            os.close();
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public ArrayList<Account> readFile() {
        try (ObjectInputStream is = new ObjectInputStream(new FileInputStream(new File(PATH).getAbsoluteFile()))) {
            return (ArrayList<Account>) is.readObject();
        }catch (Exception e){
            System.out.println(e);
            return null;
        }
    }
}