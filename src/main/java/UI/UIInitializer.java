package UI;

import javafx.scene.layout.AnchorPane;

public class UIInitializer {
    public UIInitializer(){}
    public ToolbarView toolbarView;
    public PropertiesView propertiesView;
    public void InitializeToolbar(AnchorPane pane) {
        toolbarView = new ToolbarView(pane);
    }
    public void InitializeProperties(AnchorPane pane) {
        propertiesView = new PropertiesView(pane);
    }

}
