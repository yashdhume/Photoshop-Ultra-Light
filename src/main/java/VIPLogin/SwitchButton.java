package VIPLogin;


import UI.PropertiesView;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;

public class SwitchButton extends Label {
    private SimpleBooleanProperty switchedOn = new SimpleBooleanProperty(true);
    ArrayList<Account> accounts;
        //Switch Button that changes if value is true or not
    public SwitchButton(int count, ArrayList<Account> account, Server server) {
        this.accounts = account;
        Button switchBtn = new Button();
        switchBtn.setPrefWidth(40);
        switchBtn.setOnAction(t -> switchedOn.set(!switchedOn.get()));
        setGraphic(switchBtn);

        switchedOn.addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                setText("ON");
                setStyle("-fx-background-color: green;-fx-text-fill:white;-fx-border-radius: 20 20 20 20;");
                setContentDisplay(ContentDisplay.RIGHT);
                account.get(count).setVip(true);

                if(account.get(count).isLoggedIn())
                    PropertiesView.gp.setVisible(true);
                server.saveFile(accounts);
                //switchedOn.setValue(true);
            } else {
                setText("OFF");
                setStyle("-fx-background-color: red;-fx-text-fill:white;-fx-border-radius: 20 20 20 20;");
                setContentDisplay(ContentDisplay.LEFT);
                account.get(count).setVip(false);
                server.saveFile(accounts);
                if(account.get(count).isLoggedIn())
                    PropertiesView.gp.setVisible(false);
                //switchedOn.setValue(false);
            }
        });
        switchedOn.set(account.get(count).isVip());
        setStyle(switchedOn.getValue().booleanValue());
        server.saveFile(accounts);
    }

    //setStyle update
    private void setStyle(boolean value) {
        if (value) {
            setText("ON");
            setStyle("-fx-background-color: green;-fx-text-fill:white;-fx-border-radius: 20 20 20 20;");
            setContentDisplay(ContentDisplay.RIGHT);
            //switchedOn.setValue(true);
        } else {
            setText("OFF");
            setStyle("-fx-background-color: red;-fx-text-fill:white;-fx-border-radius: 20 20 20 20;");
            setContentDisplay(ContentDisplay.LEFT);
            //switchedOn.setValue(false);
        }
    }
}