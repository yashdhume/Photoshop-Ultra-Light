package VIPLogin;

import java.io.*;
import java.net.*;
import java.util.Date;
import java.util.HashMap;

import javafx.application.Platform;
import javafx.scene.control.TextArea;

public class Server  {
    HashMap<String, String> userPassData;
    public Server(){
        userPassData =  readFile();
    }
    public TextArea start() {
        // Text area for displaying contents
        TextArea ta = new TextArea();

        // Create a scene and place it in the stage
        /*Scene scene = new Scene(new ScrollPane(ta), 450, 200);
        primaryStage.setTitle("Server"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage*/

        new Thread( () -> {
            try {
                // 1. Create a server socket
                ServerSocket server = new ServerSocket(8000);

                Platform.runLater(() -> ta.appendText("Server started at " + new Date() + '\n'));

                // 2. Listen for a connection request
                Socket socket;
                socket = server.accept();

                // 3. Create data input stream
                DataInputStream in = new DataInputStream(socket.getInputStream());


                // 4. create data output stream
                //DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());

                while (true){
                    // 5. Receive radius from the client
                    String username = in.readUTF();
                    String password = in.readUTF();
                    boolean isRegistering = in.readBoolean();
                    userPassData =  readFile();
                    Encrpt encrpt = new Encrpt();
                    String deencrptedPassword = encrpt.encrypt(password.getBytes(),password.length());

                    //  out.writeUTF("Registed");
                    Platform.runLater(() -> {
                        boolean isRegistered= false;
                        if(isRegistering){
                            isRegistered = store(username,password);
                        }
                        else {
                            if (userPassData.containsKey(username)) {
                                if (userPassData.get(username).equals(password)) {
                                    System.out.println(userPassData.get(username));
                                    System.out.println("YEETBOI");
                                    //TODO ADD VIP FUNCTIONALITY HERE
                                }
                            }
                        }
                        if(isRegistered){
                            ta.appendText("Already Registed");
                        }
                        ta.appendText(username+ '\n');
                        /*ta.appendText("Encrpted Password: " + password + '\n');
                        ta.appendText("Decrepted Password: " + deencrptedPassword + '\n');
                        ta.appendText("Registeing: " + isRegistering+ '\n');*/
                        for (String name: userPassData.keySet()){
                            String keys =name;
                            String value = userPassData.get(name);
                            ta.appendText(keys + " " + value);
                        }
                        saveFile(userPassData);
                    });
                }
            }
            catch(IOException ex) {
                ex.printStackTrace();
            }
        }).start();
        return ta;
    }
    private boolean store(String userName, String password){
        if(!userPassData.containsKey(userName)){
            userPassData.put(userName, password);
         return false;
         }
         else return true;
    }

    private final String PATH = "src/main/resources/userPasswordData.txt";
    private void saveFile(HashMap<String, String> users) {
        try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(new File(PATH).getAbsoluteFile()))) {
            os.writeObject(users);
        }catch (Exception e){
            System.out.println(e);
        }
    }
    private HashMap<String, String> readFile() {

        try (ObjectInputStream is = new ObjectInputStream(new  FileInputStream(new File(PATH).getAbsoluteFile()))) {
            return (HashMap<String, String>) is.readObject();
        }catch (Exception e){
            System.out.println(e);
            return null;
        }
    }
}