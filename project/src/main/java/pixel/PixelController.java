package pixel;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class PixelController {
	Project projectCurrent = new Project("untitled", "");
	String toolSelected = "Pencil";
	double mouseXPrev;
	double mouseYPrev;
	
	@FXML
	TabPane tabPane;
	
	@FXML
	VBox toolButtonVBox;
	
	@FXML
	ColorPicker colorPicker;
	
	@FXML
	MenuItem newTab;
	
	@FXML
	Slider toolSlider;
	
	@FXML
	void initialize() {
		// Projects
		addTab(projectCurrent.getFileName());
		
		
		// Tools
		ToggleGroup toggleGroup = new ToggleGroup();
		
		List<String> toolNames = List.of("Pencil", "Eraser", "Bucket");
		
		for (String toolName : toolNames) {
			ToggleButton toolButton = new ToggleButton(toolName);
			toolButtonVBox.getChildren().add(toolButton);
			toolButton.setOnAction(event -> {
				toolSelected = toolName; 
			});
		}
		
		for (Node child : toolButtonVBox.getChildren()) {
			((ToggleButton) child).setToggleGroup(toggleGroup);
		}
		
		// newTab
		newTab.setOnAction(event -> {
			addTab("untitled");
		});
	}
	
	private Tab addTab(String name) {
		Tab tab = new Tab();
		tab.setText(name);
		tab.setContent(createCanvas());
		
		tabPane.getTabs().add(tab);
		
		return tab;
	}
	
	private Canvas createCanvas() {
		Canvas canvas = new Canvas(600, 600);
		GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
//		graphicsContext.setFill(Color.WHITE);
//		graphicsContext.fill();
		
		canvas.setOnMouseDragged(event -> {
			switch (toolSelected) {
				case "Pencil": 
					graphicsContext.setFill(colorPicker.getValue());
					graphicsContext.fillRect(event.getX(), event.getY(), toolSlider.getValue(), toolSlider.getValue());
				break;
				case "Eraser":
					graphicsContext.clearRect(event.getX(), event.getY(), toolSlider.getValue(), toolSlider.getValue());
				break;
			}
	    });
		
		canvas.setOnScroll(event -> {
			if (event.isControlDown()) {
				double delta = event.getDeltaY() > 0 ? 0.1 : -0.1;
				canvas.setScaleX(canvas.getScaleX() + delta);
				canvas.setScaleY(canvas.getScaleY() + delta);
			}
	    });
		
		return canvas;
	}
}
