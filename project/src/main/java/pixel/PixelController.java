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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.json.JSONException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 * Main app FXML controller.
 * @author: Magne Tenstad
 */
public class PixelController {
	private static FileManager fileManager;
	private static PaletteGui paletteGui;
	private static ToolbarGui toolbarGui;
	
	@FXML
	private TabPane tabPane;
	@FXML
	private VBox toolbarVBox, layersVBox, paletteVBox;
	@FXML
	private Spinner<Integer> toolSizeSpinner;
	@FXML
	private Menu newFile, recentFiles, fileMenu;
	
	/**
	 * Initializes FXML elements.
	 */
	@FXML
	private void initialize() {
		SpriteGui.setSpriteLayerPane(layersVBox);
		tabPane.setTabClosingPolicy(TabClosingPolicy.SELECTED_TAB);
		
		Toolbar toolbar = new Toolbar();
		toolbarGui = new ToolbarGui(toolbar);
		toolbarGui.setPane(toolbarVBox);
		toolbarGui.setToolSizeSpinner(toolSizeSpinner);
		List<Tool> tools = List.of(new PencilTool(), new EraserTool(), new LineTool(), new BucketTool());
		toolbar.addTools(tools);
		toolSizeSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 40, 1));
		
		paletteGui = new PaletteGui();
		paletteGui.setPane(paletteVBox);
		paletteGui.addListener(toolbarGui);
		
		try {
			paletteGui.add(Palette.fromHexFile("src/main/resources/palettes/endesga-32.hex"));
			paletteGui.add(Palette.fromHexFile("src/main/resources/palettes/downgraded-32.hex"));
			paletteGui.add(Palette.fromHexFile("src/main/resources/palettes/pineapple-32.hex"));
			paletteGui.add(Palette.fromHexFile("src/main/resources/palettes/juice32.hex"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		fileManager = new PixelFileManager();
		fileMenu.setOnShowing(event -> {
			rebuildRecentFilesMenu();
		});
		
		for (int i = 2; i < 7; i++) {
			int size = (int) Math.pow(2, i);
			MenuItem menuItem = new MenuItem("" + size + "x" + size);
			menuItem.setOnAction(event -> {
				Sprite sprite = new Sprite(size, size);
				sprite.add(new SpriteLayer(sprite));
				newSpriteTab(sprite);
			});
			newFile.getItems().add(menuItem);
		}
	}
	
	/**
	 * @return The SpriteGui that is currently visible in tabPane.
	 */
	public SpriteGui getSpriteGui() {
		SpriteTab spriteTab = ((SpriteTab) tabPane.getSelectionModel().getSelectedItem());
		return (spriteTab != null) ? spriteTab.getSpriteGui() : null;
	}
	
	/**
	 * @return The Sprite that is currently visible in tabPane.
	 */
	public Sprite getSprite() {
		SpriteGui spriteGui = getSpriteGui();
		return (spriteGui != null ? spriteGui.getSprite() : null);
	}
	
	/**
	 * Instantiates a new SpriteTab with the given sprite.
	 * @param sprite
	 */
	public void newSpriteTab(Sprite sprite) {
		if (sprite != null) {
			SpriteGui spriteGui = newSpriteGui(sprite);
			SpriteTab spriteTab = new SpriteTab(spriteGui);
			tabPane.getTabs().add(spriteTab);
			tabPane.getSelectionModel().select(spriteTab);
		}
	}
	
	/**
	 * Instantiates a new SpriteGui with the given sprite.
	 * Makes sure spriteGui listens to the correct objects.
	 * @param sprite
	 * @return spriteGui
	 */
	public SpriteGui newSpriteGui(Sprite sprite) {
		SpriteGui spriteGui = new SpriteGui(sprite);
		toolbarGui.getToolbar().addListener(spriteGui);
		paletteGui.addListener(spriteGui);
		return spriteGui;
	}
	
	private void rebuildRecentFilesMenu() {
		recentFiles.getItems().clear();
		for (String path : fileManager.getRecentPaths()) {
			MenuItem menuItem = new MenuItem(path);
			recentFiles.getItems().add(menuItem);
			menuItem.setOnAction(event -> {
				try {
					newSpriteTab(fileManager.loadSprite(path));
				} catch (JSONException | IOException e) {
					e.printStackTrace();
				}
			});
		}
	}
	
	// 1 Methods for the FXML buttons

	// 1.1 Layer buttons
	
	@FXML
	private void newLayerButtonOnAction(ActionEvent event) {
		Sprite sprite = getSprite();
		if (sprite != null) {
			sprite.add(new SpriteLayer(sprite));
		}
	}
	@FXML
	private void removeLayerButtonOnAction(ActionEvent event) {
		Sprite sprite = getSprite();
		if (sprite != null && sprite.getSelected() != null) {
			sprite.removeSelected();
		}
	}
	@FXML
	private void moveLayerUpButtonOnAction(ActionEvent event) {
		Sprite sprite = getSprite();
		if (sprite != null && sprite.getCursor() > 0) {
			sprite.moveSelectedForward();
		}
	}
	@FXML
	private void moveLayerDownButtonOnAction(ActionEvent event) {
		Sprite sprite = getSprite();
		if (sprite != null && sprite.getCursor() + 1 < sprite.size()) {
			sprite.moveSelectedBackward();
		}
	}
	
	// 1.2 Tool buttons
	
	@FXML
	private void toolSizeSpinnerOnMouseClicked(MouseEvent event) {
		toolbarGui.getToolbar().updateToolSize((int) toolSizeSpinner.getValue());
	}
	
	// 1.3 Palette buttons
	
	@FXML
	private void uploadPaletteButtonOnAction(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new ExtensionFilter("HEX Files", "*.hex"));
		File file = fileChooser.showOpenDialog(PixelApp.getWindow());
		if (file != null) {
			Palette palette;
			try {
				palette = Palette.fromHexFile(file.getAbsolutePath());
				if (palette.size() != 0) {
					paletteGui.add(palette);
					paletteGui.select(palette);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}
	
	@FXML
	private void paletteBackwardButtonOnAction(ActionEvent event) {
		if (paletteGui != null) {
			int index = (paletteGui.getCursor() - 1) % paletteGui.size();
			index += index < 0 ? paletteGui.size() : 0;			
			paletteGui.select(paletteGui.get(index));
			paletteGui.rebuild();
		}
	}
	
	@FXML
	private void paletteForwardButtonOnAction(ActionEvent event) {
		if (paletteGui != null) {
			int index = (paletteGui.getCursor() + 1) % paletteGui.size();			
			paletteGui.select(paletteGui.get(index));
			paletteGui.rebuild();
		}
	}
	
	// 1.4 File buttons

	@FXML
	private void openFileOnAction(ActionEvent event) {
		try {
			Sprite sprite = fileManager.loadSprite();
			if (sprite != null) {
				newSpriteTab(sprite);
			}
		} catch (JSONException | IOException e) {
			e.printStackTrace();
		}
	}
	@FXML
	private void closeFileOnAction(ActionEvent event) {
		if (tabPane.getTabs().size() > 0) {
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
			fileManager.exportSprite(sprite, paletteGui.getSelected());
		}
	}
}