package pixel;

import java.util.ArrayList;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;

public class Toolbar {
	private Pane parent;
	private Tool toolSelected;
	private ArrayList<Tool> tools = new ArrayList<Tool>();
	private ToggleGroup toggleGroup = new ToggleGroup();
	
	public Toolbar(Pane parent, ArrayList<Tool> tools) {
		setParent(parent);
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
	
	public Tool getToolSelected() {
		return toolSelected;
	}
	
	private void addTool(Tool tool) {
		tools.add(tool);
		newToolButton(tool);
	}
	
	private void setParent(Pane parent) {
		this.parent = parent;
	}
	
	private void setToolSelected(Tool toolSelected) {
		this.toolSelected = toolSelected;
	}
	
	private ToggleButton newToolButton(Tool tool) {
		ToggleButton toolButton = new ToggleButton(tool.getName());
		toolButton.setToggleGroup(toggleGroup);
		toolButton.setOnAction(event -> {
			toolSelected = tool; 
		});
		parent.getChildren().add(toolButton);
		return toolButton;
	}
}
