package pixel;

import pixel.file.FileManager;
import pixel.file.PixelFileManager;
import pixel.gui.PaletteGui;
import pixel.gui.SpriteGui;
import pixel.gui.SpriteTab;
import pixel.gui.ToolbarGui;
import pixel.palette.Palette;
import pixel.sprite.*;
import pixel.tool.*;

import java.util.List;

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
	private static FileManager fileManager;
	private static Palette palette;
	private static PaletteGui paletteGui;
	private static ToolbarGui toolbarGui;
	
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
		SpriteGui.setSpriteLayerPane(layersVBox);
		
		Toolbar toolbar = new Toolbar();
		toolbarGui = new ToolbarGui(toolbar);
		toolbarGui.setPane(toolbarVBox);
		toolbarGui.setToolSizeSpinner(toolSizeSpinner);
		List<Tool> tools = List.of(new PencilTool(), new EraserTool(), new LineTool(), new BucketTool());
		toolbar.addTools(tools);
		
		palette = Palette.fromHexFile("src/main/resources/endesga-16.hex");
		palette.addListener(toolbar);
		paletteGui = new PaletteGui(palette);
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
	
	// 1 Methods for the FXML buttons

	// 1.1 Layer buttons
	
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
			sprite.removeSelected();
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
	
	// 1.2 Tool buttons
	
	@FXML
	private void toolSizeSpinnerOnMouseClicked(MouseEvent event) {
		toolbarGui.getToolbar().updateToolSize((int) toolSizeSpinner.getValue());
	}
	
	// 1.3 File buttons
	
	@FXML
	private void newFileOnAction(ActionEvent event) {
		Sprite sprite = new Sprite(32, 32);
		sprite.addSpriteLayer();
		SpriteGui spriteGui = new SpriteGui(sprite, palette);
		Toolbar toolbar = toolbarGui.getToolbar();
		toolbar.addListener(spriteGui);
		toolbar.notifySetIndex();
		SpriteTab spriteTab = new SpriteTab(spriteGui);
		tabPane.getTabs().add(spriteTab);
		tabPane.getSelectionModel().select(spriteTab);
	}
	
	@FXML
	private void openFileOnAction(ActionEvent event) {
		Sprite sprite = fileManager.loadSprite();
		if (sprite != null) {
			new SpriteTab(new SpriteGui(sprite, palette));
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
			fileManager.exportSprite(sprite, palette);
		}
	}
}