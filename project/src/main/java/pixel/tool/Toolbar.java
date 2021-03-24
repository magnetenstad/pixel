package pixel.tool;

import java.util.ArrayList;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import pixel.PixelApp;
import pixel.sprite.Sprite;

public class Toolbar {
	private Pane parent = PixelApp.getController().getToolbarVBox();
	private Tool toolSelected;
	private ArrayList<Tool> tools = new ArrayList<Tool>();
	private ToggleGroup toggleGroup = new ToggleGroup();
	
	public Toolbar(Tool[] tools) {
		checkToolsNotEmpty(tools);
		for (Tool tool : tools) {
			addTool(tool);
		}
		setToolSelected(tools[0]);
	}
	private void checkToolsNotEmpty(Tool[] tools) {
		if (tools.length == 0) {
			throw new IllegalArgumentException("Tools is empty!");
		}
	}
	public void addTool(Tool tool) {
		tools.add(tool);
		newToolButton(tool);
	}
	private ToggleButton newToolButton(Tool tool) {
		ToggleButton toolButton = new ToggleButton(tool.getName());
		toolButton.setToggleGroup(toggleGroup);
		toolButton.setOnAction(event -> {
			setToolSelected(tool);
			PixelApp.getController().getToolSizeSpinner().getValueFactory().setValue(tool.getSize());
		});
		parent.getChildren().add(toolButton);
		if (parent.getChildren().size() == 1) {
			toolButton.setSelected(true);
		}
		return toolButton;
	}
	public void setToolSelected(Tool toolSelected) {
		this.toolSelected = toolSelected;
	}
	public Tool getToolSelected() {
		return toolSelected;
	}
	public void useToolSelected(Sprite sprite, MouseEvent event) {
		toolSelected.use(sprite, event);
		sprite.updateGui();
	}
	public void updateToolColor(int color) {
		for (Tool tool : tools) {
			tool.setColor(color);
		}
	}
	public void updateToolSize(int size) {
		toolSelected.setSize(size);
	}
}
