package pixel;

import pixel.file.FileManager;
import pixel.file.PixelFileManager;
import pixel.sprite.*;
import pixel.tool.*;

import java.util.ArrayList;
import java.util.Arrays;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/*
 * Main app FXML controller.
 */
public class PixelController {
	private FileManager fileManager;
	private Palette palette;
	private Toolbar toolbar;
	
	@FXML
	private TabPane tabPane;
	@FXML
	private VBox toolbarVBox, layersVBox, paletteVBox;
	@FXML
	private ColorPicker colorPicker;
	@FXML
	private Spinner<Integer> toolSizeSpinner;
	@FXML
	private Button newLayerButton, removeLayerButton;
	@FXML
	private MenuItem newFile, openFile, closeFile, saveFile, saveFileAs, exportFile;
	
	// Getters for FXML elements:
	
	public Pane getLayersVBox() {
		return (Pane) layersVBox;
	}
	public Pane getToolbarVBox() {
		return (Pane) toolbarVBox;
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
	public Spinner<Integer> getToolSizeSpinner() {
		return toolSizeSpinner;
	}
	
	/*
	 * Initializes FXML elements.
	 */
	@FXML
	private void initialize() {
		fileManager = new PixelFileManager();
		palette = new Palette();
		ArrayList<Tool> tools = new ArrayList<Tool>(Arrays.asList(new PencilTool(), new EraserTool(), new LineTool(), new BucketTool()));
		toolbar = new Toolbar(tools);
		
		tabPane.setTabClosingPolicy(TabClosingPolicy.SELECTED_TAB);
		toolSizeSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 40, 1));
		newFile.setOnAction(event -> {
			new SpriteTab();
		});
		openFile.setOnAction(event -> {
			Sprite sprite = fileManager.loadSprite();
			if (sprite != null) {
				new SpriteTab(sprite);
			}
		});
		closeFile.setOnAction(event -> {
			if (0 < tabPane.getTabs().size()) {
				tabPane.getTabs().remove(tabPane.getSelectionModel().getSelectedItem());
				SpriteTab.updateSpriteLayerGui();
			}
		});
		saveFile.setOnAction(event -> {
			Sprite sprite = getSpriteCurrent();
			if (sprite != null) {
				if (sprite.getPath() != null) {
					fileManager.saveSprite(sprite.getPath(), sprite);
				}
				else {
					fileManager.saveSprite(sprite);
				}
			}
		});
		saveFileAs.setOnAction(event -> {
			Sprite sprite = getSpriteCurrent();
			if (sprite != null) {
				fileManager.saveSprite(sprite);
			}
		});
		exportFile.setOnAction(event -> {
			Sprite sprite = getSpriteCurrent();
			if (sprite != null) {
				fileManager.exportSprite(sprite);
			}
		});
		newLayerButton.setOnAction(event -> {
			Sprite sprite = getSpriteCurrent();
			if (sprite != null) {
				sprite.addSpriteLayer();
			}
		});
		removeLayerButton.setOnAction(event -> {
			Sprite sprite = getSpriteCurrent();
			if (sprite != null) {
				SpriteLayer canvasLayerCurrent = sprite.getSpriteLayerCurrent();
				if (canvasLayerCurrent != null) {
					canvasLayerCurrent.removeGuiFromParent();
					sprite.removeSpriteLayer(canvasLayerCurrent);
				}
			}
		});
		colorPicker.setOnAction(event -> {
			palette.setColorCurrent(colorPicker.getValue());
			if (getSpriteCurrent() != null) {
				getSpriteCurrent().updateImageView();
			}
		});
		toolSizeSpinner.setOnMouseClicked(event -> {
			toolbar.updateToolSize((int) toolSizeSpinner.getValue());
		});
	}
	
	/*
	 * @return The Sprite that is currently visible in tabPane.
	 */
	public Sprite getSpriteCurrent() {
		SpriteTab spriteTab = ((SpriteTab) tabPane.getSelectionModel().getSelectedItem());
		if (spriteTab != null) {
			return spriteTab.getSprite();
		}
		return null;
	}
	public Palette getPalette() {
		return palette;
	}
}