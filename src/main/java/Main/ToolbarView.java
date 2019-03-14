package Main;
import Effects.BlackWhiteEffect;
import Tools.PaintDraw;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

public class ToolbarView {
	AnchorPane toolbarPane;
	EditingView editingView = new EditingView();

	public ToolbarView(AnchorPane pane) {
		toolbarPane = pane;
		GetToolbarView();
	}
	
	public void GetToolbarView() {
		GridPane gp = new GridPane();
		gp.setHgap(10);
		gp.setVgap(10);
		Button BlackAndWhite = new Button("Black and White");
		BlackAndWhite.setOnAction((event) -> {
			BlackWhiteEffect blackWhiteEffect = new BlackWhiteEffect(editingView.imageViewEditView.getImage());
			editingView.imageViewEditView.setImage(blackWhiteEffect.getEffect());
		});

		Button Draw = new Button("Draw");
		Slider slider = new Slider (0, 100, 100);
		ColorPicker colorPicker = new ColorPicker();



		gp.add(BlackAndWhite, 0, 0);
		gp.add(Draw, 0, 1);
		gp.add(slider, 0, 2);
		gp.add(colorPicker, 0, 3);
		toolbarPane.getChildren().addAll(gp);
	}
}
