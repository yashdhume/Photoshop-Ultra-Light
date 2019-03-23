package Global;
import VIPLogin.Account;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
public class SwitchButton extends Label {
    private SimpleBooleanProperty switchedOn = new SimpleBooleanProperty(true);
    public SwitchButton(Account account) {
        Button switchBtn = new Button();
        switchBtn.setPrefWidth(40);
        switchBtn.setOnAction(t -> switchedOn.set(!switchedOn.get()));
        setGraphic(switchBtn);
        switchedOn.addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                setText("ON");
                setStyle("-fx-background-color: green;-fx-text-fill:white;-fx-border-radius: 20 20 20 20;");
                setContentDisplay(ContentDisplay.RIGHT);
                account.setVip(true);
            } else {
                setText("OFF");
                setStyle("-fx-background-color: red;-fx-text-fill:white;-fx-border-radius: 20 20 20 20;");
                setContentDisplay(ContentDisplay.LEFT);
                account.setVip(false);
            }
        });
        switchedOn.set(false);
    }
    public SimpleBooleanProperty switchOnProperty() {
        return switchedOn;
    }
}