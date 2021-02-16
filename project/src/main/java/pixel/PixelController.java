package pixel;

import java.util.List;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.StackPane;
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
		tabPane.getTabs().add(tab);
		
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setOnScroll(event -> {
			if (event.isControlDown()) {
				double delta = event.getDeltaY() > 0 ? 0.1 : -0.1;
				scrollPane.getContent().setScaleX(clamp(scrollPane.getContent().getScaleX() + delta, 0.1, 10));
				scrollPane.getContent().setScaleY(clamp(scrollPane.getContent().getScaleY() + delta, 0.1, 10));
			}
	    });
		scrollPane.setHbarPolicy(ScrollBarPolicy.ALWAYS);
		scrollPane.setVbarPolicy(ScrollBarPolicy.ALWAYS);

		tab.setContent(scrollPane);
		
		StackPane pane = new StackPane();
		pane.setPrefHeight(1000);
		pane.setPrefWidth(1000);
		scrollPane.setContent(pane);
		
		Canvas canvas = createCanvas();
		pane.getChildren().add(canvas);
		StackPane.setAlignment(canvas, Pos.CENTER);
		
		return tab;
	}
	
	private Canvas createCanvas() {
		Canvas canvas = new Canvas(600, 600);
		GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
		graphicsContext.setImageSmoothing(false);
		graphicsContext.setFill(Color.WHITE);
		graphicsContext.fillRect(0, 0, 600, 600);
		
		canvas.setOnMouseDragged(event -> {
			switch (toolSelected) {
				case "Pencil": 
					graphicsContext.setFill(colorPicker.getValue());
					graphicsContext.fillRect((int) event.getX(), (int) event.getY(), toolSlider.getValue(), toolSlider.getValue());
				break;
				case "Eraser":
					//graphicsContext.clearRect((int) event.getX(), (int) event.getY(), toolSlider.getValue(), toolSlider.getValue());
					graphicsContext.setFill(Color.WHITE);
					graphicsContext.fillRect((int) event.getX(), (int) event.getY(), toolSlider.getValue(), toolSlider.getValue());
				break;
			}
	    });
		
		return canvas;
	}
	
	private double clamp(double value, double min, double max) {
		return Math.max(min, Math.min(value, max));
	}
}









