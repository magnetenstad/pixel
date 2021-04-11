package pixel;

import pixel.file.FileManager;
import pixel.file.PixelFileManager;
import pixel.sprite.*;
import pixel.tool.*;

import java.util.ArrayList;
import java.util.Arrays;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/*
 * Main app FXML controller.
 */
public class PixelController {
	private FileManager fileManager;
	private Palette palette;
	private PaletteGui paletteGui;
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
	private Button newLayerButton, removeLayerButton, moveLayerUpButton, moveLayerDownButton;
	@FXML
	private MenuItem newFile, openFile, closeFile, saveFile, saveFileAs, exportFile;
	
	/*
	 * Initializes FXML elements.
	 */
	@FXML
	private void initialize() {
		Tool[] tools = { new PencilTool(), new EraserTool(), new LineTool(), new BucketTool() };
		toolbar = new Toolbar(tools);
		palette = Palette.fromHexFile("src/main/resources/endesga-16.hex");
		palette.addListener(toolbar);
		paletteGui = new PaletteGui();
		paletteGui.setPane(paletteVBox);
		paletteGui.setPalette(palette);
		SpriteLayerGui.setPane(layersVBox);
		fileManager = new PixelFileManager();
		tabPane.setTabClosingPolicy(TabClosingPolicy.SELECTED_TAB);
		toolSizeSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 40, 1));
	}
	
	/*
	 * @return The sprite that is currently visible in tabPane.
	 */
	public Sprite getSprite() {
		SpriteTab spriteTab = ((SpriteTab) tabPane.getSelectionModel().getSelectedItem());
		if (spriteTab != null) {
			return spriteTab.getSprite();
		}
		return null;
	}
	
	public Palette getPalette() {
		return palette;
	}
	
	// 1 Getters for the FXML elements
	
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
	
	// 2 Methods for the FXML buttons

	// 2.1 Layer buttons
	
	@FXML
	private void newLayerButtonOnAction(ActionEvent event) {
		Sprite sprite = getSprite();
		if (sprite != null) {
			sprite.addSpriteLayerWithGui();
		}
	}
	@FXML
	private void removeLayerButtonOnAction(ActionEvent event) {
		Sprite sprite = getSprite();
		if (sprite != null) {
			SpriteLayer spriteLayer = sprite.getSpriteLayer();
			if (spriteLayer != null) {
				sprite.removeSpriteLayer(spriteLayer);
			}
		}
	}
	@FXML
	private void moveLayerUpButtonOnAction(ActionEvent event) {
		Sprite sprite = getSprite();
		if (sprite != null) {
			sprite.moveSpriteLayerUp();
		}
	}
	@FXML
	private void moveLayerDownButtonOnAction(ActionEvent event) {
		Sprite sprite = getSprite();
		if (sprite != null) {
			sprite.moveSpriteLayerDown();
		}
	}
	
	// 2.2 Tool buttons
	
	@FXML
	private void colorPickerOnAction(ActionEvent event) {
		palette.setColor(colorPicker.getValue());
		if (getSprite() != null) {
			getSprite().updateGui();
		}
	}
	@FXML
	private void toolSizeSpinnerOnMouseClicked(MouseEvent event) {
		toolbar.updateToolSize((int) toolSizeSpinner.getValue());
	}
	
	// 2.3 File buttons
	
	@FXML
	private void newFileOnAction(ActionEvent event) {
		new SpriteTab();
	}
	@FXML
	private void openFileOnAction(ActionEvent event) {
		Sprite sprite = fileManager.loadSprite();
		if (sprite != null) {
			new SpriteTab(sprite.getSpriteGui());
		}
	}
	@FXML
	private void closeFileOnAction(ActionEvent event) {
		if (0 < tabPane.getTabs().size()) {
			tabPane.getTabs().remove(tabPane.getSelectionModel().getSelectedItem());
			SpriteLayerGui.updateAll();
		}
	}
	
	@FXML
	private void saveFileOnAction(ActionEvent event) {
		Sprite sprite = getSprite();
		if (sprite != null) {
			if (sprite.getPath() != null) {
				fileManager.saveSprite(sprite.getPath(), sprite);
			}
			else {
				fileManager.saveSprite(sprite);
			}
		}
	}
	@FXML
	private void saveFileAsOnAction(ActionEvent event) {
		Sprite sprite = getSprite();
		if (sprite != null) {
			fileManager.saveSprite(sprite);
		}
	}
	@FXML
	private void exportFileOnAction(ActionEvent event) {
		Sprite sprite = getSprite();
		if (sprite != null) {
			fileManager.exportSprite(sprite);
		}
	}
}