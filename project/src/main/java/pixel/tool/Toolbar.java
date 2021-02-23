package pixel.tool;

import java.util.ArrayList;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Toolbar {
	private Pane parent;
	private Tool toolSelected;
	private ArrayList<Tool> tools = new ArrayList<Tool>();
	private ToggleGroup toggleGroup = new ToggleGroup();
	
	public Toolbar(Pane parent, ArrayList<Tool> tools) {
		this.parent = parent;
		checkToolsNotEmpty(tools);
		for (Tool tool : tools) {
			addTool(tool);
		}
		setToolSelected(tools.get(0));
	}
	
	private void checkToolsNotEmpty(ArrayList<Tool> tools) {
		if (tools.size() == 0) {
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
		});
		parent.getChildren().add(toolButton);
		return toolButton;
	}
	
	public void setToolSelected(Tool toolSelected) {
		this.toolSelected = toolSelected;
	}
	
	public Tool getToolSelected() {
		return toolSelected;
	}
	
	
	public void updateToolColor(Color color) {
		for (Tool tool : tools) {
			tool.setColor(color);
		}
	}
	
	public void updateToolSize(int size) {
		for (Tool tool : tools) {
			tool.setSize(size);
		}
	}
}
