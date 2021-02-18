package pixel;

import pixel.lib.SimpleFileTreeItem;
import pixel.tool.EraserTool;
import pixel.tool.PencilTool;
import pixel.tool.Tool;
import pixel.tool.Toolbar;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TabPane;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
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
	ScrollPane fileViewPane;
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
	AnchorPane rightSplitPane;
	
	public File getRootFile() {
		return rootFile;
	}
	
	public void askForDirectory() {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		File selectedDirectory = directoryChooser.showDialog(rightSplitPane.getScene().getWindow());
		if (selectedDirectory != null) {
			setRootFile(selectedDirectory);
		}
	}
	
	public void setRootFile(File rootFile) {
		this.rootFile = rootFile;
		fileView = new TreeView<File>(new SimpleFileTreeItem(rootFile));
		fileViewPane.setContent(fileView);
	}
	
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

		setFileRootButton.setOnAction(event -> {
			askForDirectory();
		});
		
		// File tree
		/*
		 * Adding a TreeView to the very left of the application window.
		 */
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