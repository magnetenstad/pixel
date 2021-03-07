package pixel;

import pixel.sprite.*;
import pixel.tool.*;

import java.util.ArrayList;
import java.util.Arrays;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class PixelController {
	private Toolbar toolbar;
	private Directory directory;
	private Palette palette;
	
	@FXML
	Button setFileRootButton;
	@FXML
	AnchorPane fileViewPane;
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
	VBox rightVBox;
	@FXML
	VBox layersVBox;
	@FXML
	Button newLayerButton;
	@FXML
	Button removeLayerButton;
	@FXML
	VBox paletteVBox;
	
	public Pane getLayersVBox() {
		return (Pane) layersVBox;
	}
	public Pane getToolBarVBox() {
		return (Pane) toolBarVBox;
	}
	public Pane getFileViewPane() {
		return (Pane) fileViewPane;
	}
	public TabPane getTabPane() {
		return tabPane;
	}
	public Toolbar getToolbar() {
		return toolbar;
	}
	public Pane getPaletteVBox() {
		return (Pane) paletteVBox;
	}
	
	@FXML
	void initialize() {
		ArrayList<Tool> tools = new ArrayList<Tool>(Arrays.asList(new PencilTool(), new EraserTool(), new LineTool()));
		toolbar = new Toolbar(tools);
		directory = new Directory();
		palette = new Palette();
		new SpriteTab();
		
		newTab.setOnAction(event -> {
			new SpriteTab();
		});
		setFileRootButton.setOnAction(event -> {
			directory.askForDirectory();
		});
		newLayerButton.setOnAction(event -> {
			Sprite sprite = getSpriteCurrent();
			sprite.addSpriteLayer();
		});
		removeLayerButton.setOnAction(event -> {
			Sprite sprite = getSpriteCurrent();
			SpriteLayer canvasLayerCurrent = sprite.getSpriteLayerCurrent();
			if (canvasLayerCurrent != null) {
				canvasLayerCurrent.removeGuiFromParent();
				sprite.removeSpriteLayer(canvasLayerCurrent);
			}
		});
		colorPicker.setOnAction(event -> {
			palette.setColorCurrent(colorPicker.getValue());
		});
		toolSlider.setOnMouseClicked(event -> {
			toolbar.updateToolSize((int) toolSlider.getValue());
		});
	}
	private Sprite getSpriteCurrent() {
		return ((SpriteTab) tabPane.getSelectionModel().getSelectedItem()).getSprite();
	}
}