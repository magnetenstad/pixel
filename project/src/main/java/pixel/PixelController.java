package pixel;

import pixel.sprite.*;
import pixel.tool.*;

import java.io.File;
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
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class PixelController {
	private Toolbar toolbar;
	private Directory directory;
	private Palette palette;
	
	@FXML
	private Button setFileRootButton;
	@FXML
	private AnchorPane fileViewPane;
	@FXML
	private TabPane tabPane;
	@FXML
	private VBox toolBarVBox;
	@FXML
	private ColorPicker colorPicker;
	@FXML
	private Slider toolSlider;
	@FXML
	private VBox rightVBox;
	@FXML
	private VBox layersVBox;
	@FXML
	private Button newLayerButton;
	@FXML
	private Button removeLayerButton;
	@FXML
	private VBox paletteVBox;
	@FXML
	private MenuItem newFile;
	@FXML
	private MenuItem openFile;
	@FXML
	private MenuItem closeFile;
	@FXML
	private MenuItem saveFile;
	@FXML
	private MenuItem saveFileAs;
	
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
	public Slider getToolSlider() {
		return toolSlider;
	}
	
	@FXML
	void initialize() {
		ArrayList<Tool> tools = new ArrayList<Tool>(Arrays.asList(new PencilTool(), new EraserTool(), new LineTool(), new BucketTool()));
		toolbar = new Toolbar(tools);
		directory = new Directory();
		palette = new Palette();
		//new SpriteTab();
		
		newFile.setOnAction(event -> {
			new SpriteTab();
		});
		openFile.setOnAction(event -> {
			directory.openFile();
		});
		closeFile.setOnAction(event -> {
			tabPane.getTabs().remove(tabPane.getSelectionModel().getSelectedItem());
		});
		saveFile.setOnAction(event -> {
			Sprite sprite = getSpriteCurrent();
			if (sprite.getPath() != null) {
				Directory.saveSpriteToPath(sprite, sprite.getPath());
			}
			else {
				directory.saveSprite(sprite);
			}
		});
		saveFileAs.setOnAction(event -> {
			directory.saveSprite(getSpriteCurrent());
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
			getSpriteCurrent().updateImageView();
		});
		
		toolSlider.setOnMouseClicked(event -> {
			toolbar.updateToolSize((int) toolSlider.getValue());
		});
	}
	public Sprite getSpriteCurrent() {
		return ((SpriteTab) tabPane.getSelectionModel().getSelectedItem()).getSprite();
	}
	public Palette getPalette() {
		return palette;
	}
}