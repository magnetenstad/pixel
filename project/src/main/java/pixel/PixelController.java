package pixel;

import pixel.tools.EraserTool;
import pixel.tools.PencilTool;
import pixel.tools.Toolbar;
import pixel.tools.Tool;

import java.util.ArrayList;
import java.util.Arrays;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

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
		Canvas canvas = spriteTab.getSprite().getVisibleCanvas();
		canvas.setOnMousePressed(event -> {
			useTool(event);
		});
		canvas.setOnMouseDragged(canvas.getOnMousePressed());
	}
	
	public void useTool(MouseEvent event) {
		Tool tool = toolbar.getToolSelected();
		Sprite sprite = ((SpriteTab) tabPane.getSelectionModel().getSelectedItem()).getSprite();
		tool.use(sprite, event);
	}
}