package pixel;

import pixel.ext.SimpleFileTreeItem;
import pixel.sprite.Sprite;
import pixel.sprite.SpriteLayer;
import pixel.sprite.SpriteTab;
import pixel.tool.EraserTool;
import pixel.tool.PencilTool;
import pixel.tool.Tool;
import pixel.tool.Toolbar;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.TabPane;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;

public class PixelController {
	private Project projectCurrent;
	private Toolbar toolbar;
	private File rootFile;
	private TreeView<File> fileView = new TreeView<File>();
	
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
	void initialize() {
		// Init Toolbar
		ArrayList<Tool> tools = new ArrayList<Tool>(Arrays.asList(new PencilTool(), new EraserTool()));
		toolbar = new Toolbar(toolBarVBox, tools);
		
		// Init SpriteTabs
		newSpriteTab();
		newTab.setOnAction(event -> {
			newSpriteTab();
		});
		
		// Init setFileRootButton
		setFileRootButton.setOnAction(event -> {
			askForDirectory();
		});
		
		// Init layerButtons
		newLayerButton.setOnAction(event -> {
			Sprite sprite = getSpriteCurrent();
			sprite.addCanvasLayer();
		});
		
		removeLayerButton.setOnAction(event -> {
			Sprite sprite = getSpriteCurrent();
			SpriteLayer canvasLayerCurrent = sprite.getCanvasLayerCurrent();
			if (canvasLayerCurrent != null) {
				canvasLayerCurrent.removeGuiFromParent();
				sprite.removeCanvasLayer(canvasLayerCurrent);
			}
		});
	}
	
	public File getRootFile() {
		return rootFile;
	}
	
	public void askForDirectory() {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		File selectedDirectory = directoryChooser.showDialog(setFileRootButton.getScene().getWindow());
		if (selectedDirectory != null) {
			setRootFile(selectedDirectory);
		}
	}
	
	public void setRootFile(File rootFile) {
		this.rootFile = rootFile;
		fileView = new TreeView<File>(new SimpleFileTreeItem(rootFile));
		fileViewPane.getChildren().clear();
		fileViewPane.getChildren().add(fileView);
	}
	
	public void newSpriteTab() {
		SpriteTab spriteTab = new SpriteTab(tabPane, layersVBox, "untitled");
		ImageView imageView = spriteTab.getSprite().getImageView();
		imageView.setOnMousePressed(event -> {
			useTool(event);
		});
		imageView.setOnMouseDragged(imageView.getOnMousePressed());
	}
	
	public void useTool(MouseEvent event) {
		Tool tool = toolbar.getToolSelected();
		Sprite sprite = getSpriteCurrent();
		tool.use(sprite, event);
	}
	
	public Sprite getSpriteCurrent() {
		return ((SpriteTab) tabPane.getSelectionModel().getSelectedItem()).getSprite();
	}
	
}