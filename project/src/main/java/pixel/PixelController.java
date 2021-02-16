package pixel;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Tab;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

public class PixelController {
	Project projectCurrent = new Project("New project", "");

	@FXML
	Tab projectTab;
	
	@FXML
	VBox toolButtonVBox;
	
	@FXML
	ColorPicker colorPicker;
	
	@FXML
	void initialize() {
		// Projects
		projectTab.setText(projectCurrent.getFileName());
		Canvas canvas = new Canvas(400, 400);
		projectTab.setContent(canvas);
		GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
		
		canvas.setOnMouseDragged(event -> {
			graphicsContext.setFill(colorPicker.getValue());
			graphicsContext.fillRect(event.getX(), event.getY(), 4, 4);
	    });
		
		// Tools
		ToggleGroup toggleGroup = new ToggleGroup();
		toolButtonVBox.getChildren().add(new ToggleButton("Pencil"));
		toolButtonVBox.getChildren().add(new ToggleButton("Eraser"));
		toolButtonVBox.getChildren().add(new ToggleButton("Bucket"));
		
		for (Node child : toolButtonVBox.getChildren()) {
			((ToggleButton) child).setToggleGroup(toggleGroup);
		}
	}
}
