package pixel;

import java.util.ArrayList;
import java.util.Arrays;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class PixelController {
	Project projectCurrent;
	Toolbar toolbar;

	@FXML
	TabPane tabPane;
	@FXML
	VBox toolBarVBox;
	@FXML
	ColorPicker colorPicker;
	@FXML
	MenuItem newTab;
	@FXML
	Slider toolSlider;
	
	@FXML
	void initialize() {
		// Create Toolbar
		ArrayList<Tool> tools = new ArrayList<Tool>(Arrays.asList(new PencilTool(), new EraserTool()));
		toolbar = new Toolbar(toolBarVBox, tools);
		
		// Init SpriteTabs
		newSpriteTab();
		newTab.setOnAction(event -> {
			newSpriteTab();
		});
	}
	
	public void newSpriteTab() {
		SpriteTab spriteTab = new SpriteTab(tabPane, "untitled");
		Sprite sprite = spriteTab.getSprite();
		sprite.setOnMousePressed(event -> {
			useTool(event);
		});
		sprite.setOnMouseDragged(sprite.getOnMousePressed());
	}
	
	public void useTool(MouseEvent event) {
		Tool tool = toolbar.getToolSelected();
		Sprite sprite = ((SpriteTab) tabPane.getSelectionModel().getSelectedItem()).getSprite();
		tool.use(sprite, event);
	}
}

