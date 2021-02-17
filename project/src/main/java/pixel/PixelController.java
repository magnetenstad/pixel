package pixel;

import java.util.List;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
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
import javafx.scene.image.PixelWriter;
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
		
		Canvas canvas = createCanvas();
		StackPane pane = new StackPane();
		pane.setPrefHeight(canvas.getWidth()*2);
		pane.setPrefWidth(canvas.getHeight()*2);
		pane.getChildren().add(canvas);
		StackPane.setAlignment(canvas, Pos.CENTER);
		
		ScrollPane scrollPane = new ZoomableScrollPane(pane); // Source: Daniel Hári
		tab.setContent(scrollPane);
		return tab;
	}
	
	private Canvas createCanvas() {
		Canvas canvas = new Canvas(640, 640);
		GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
		graphicsContext.setImageSmoothing(false);
		graphicsContext.setFill(Color.WHITE);
		graphicsContext.fillRect(0, 0, 640, 640);
		canvas.setCursor(Cursor.CROSSHAIR);
		
		canvas.setOnMousePressed(event -> {
			int scale = 32; // Pixels are 32x32, canvas is blurry otherwise.
			if (event.isPrimaryButtonDown()) {
				switch (toolSelected) {
					case "Pencil":
						graphicsContext.setFill(colorPicker.getValue());
						graphicsContext.fillRect(((int) event.getX() / scale) * scale, ((int) event.getY() / scale) * scale, scale, scale);
					break;
					case "Eraser":
						graphicsContext.setFill(Color.WHITE);
						graphicsContext.fillRect(((int) event.getX() / scale) * scale, ((int) event.getY() / scale) * scale, scale, scale);
					break;
				}
			}
			else if (event.isSecondaryButtonDown()) {
				graphicsContext.setFill(Color.WHITE);
				graphicsContext.fillRect(((int) event.getX() / scale) * scale, ((int) event.getY() / scale) * scale, scale, scale);
			}
	    });
		canvas.setOnMouseDragged(canvas.getOnMousePressed());
		
		return canvas;
	}
}









