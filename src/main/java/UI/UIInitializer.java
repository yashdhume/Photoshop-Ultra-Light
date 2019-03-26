package UI;

import javafx.scene.layout.AnchorPane;

public class UIInitializer {
    public ToolbarView toolbarView;
    public PropertiesView propertiesView;

    public UIInitializer(AnchorPane toolbar){
        InitializeToolbar(toolbar);
    }

    public UIInitializer(AnchorPane toolbar, AnchorPane properties){
        InitializeToolbar(toolbar);
        InitializeProperties(properties);
    }

    public void InitializeToolbar(AnchorPane pane) {
        toolbarView = new ToolbarView(pane);
    }
    public void InitializeProperties(AnchorPane pane) {
        propertiesView = new PropertiesView(pane);
    }

}
