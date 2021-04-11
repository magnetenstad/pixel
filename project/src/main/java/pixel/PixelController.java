package pixel;

import pixel.file.FileManager;
import pixel.file.PixelFileManager;
import pixel.sprite.*;
import pixel.tool.*;

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
		SpriteTab.setTabPane(tabPane);
		SpriteGui.setSpriteLayerPane(layersVBox);
		Tool[] tools = { new PencilTool(), new EraserTool(), new LineTool(), new BucketTool() };
		toolbar = new Toolbar(tools);
		toolbar.setPane(toolbarVBox);
		toolbar.setToolSizeSpinner(toolSizeSpinner);
		palette = Palette.fromHexFile("src/main/resources/endesga-16.hex");
		palette.addListener(toolbar);
		paletteGui = new PaletteGui();
		paletteGui.setPalette(palette);
		paletteGui.setPane(paletteVBox);
		fileManager = new PixelFileManager();
		tabPane.setTabClosingPolicy(TabClosingPolicy.SELECTED_TAB);
		toolSizeSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 40, 1));
	}
	
	/*
	 * @return The SpriteGui that is currently visible in tabPane.
	 */
	public SpriteGui getSpriteGui() {
		SpriteTab spriteTab = ((SpriteTab) tabPane.getSelectionModel().getSelectedItem());
		return (spriteTab != null) ? spriteTab.getSpriteGui() : null;
	}
	
	/*
	 * @return The Sprite that is currently visible in tabPane.
	 */
	public Sprite getSprite() {
		SpriteGui spriteGui = getSpriteGui();
		return (spriteGui != null ? spriteGui.getSprite() : null);
	}
	
	public Palette getPalette() {
		return palette;
	}
	public void updateSpriteGui() {
		SpriteGui spriteGui = getSpriteGui();
		if (spriteGui != null) {
			spriteGui.update();
		}
	}
	// 1 Getters for the FXML elements
	
	public Toolbar getToolbar() {
		return toolbar;
	}
	
	// 2 Methods for the FXML buttons

	// 2.1 Layer buttons
	
	@FXML
	private void newLayerButtonOnAction(ActionEvent event) {
		Sprite sprite = getSprite();
		if (sprite != null) {
			sprite.addSpriteLayer();
		}
	}
	@FXML
	private void removeLayerButtonOnAction(ActionEvent event) {
		Sprite sprite = getSprite();
		if (sprite != null) {
			sprite.removeSpriteLayer();
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
			new SpriteTab(new SpriteGui(sprite));
		}
	}
	@FXML
	private void closeFileOnAction(ActionEvent event) {
		if (0 < tabPane.getTabs().size()) {
			tabPane.getTabs().remove(tabPane.getSelectionModel().getSelectedItem());
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