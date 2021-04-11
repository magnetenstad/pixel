package pixel.tool;

import java.util.ArrayList;

import javafx.scene.control.Spinner;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import pixel.Palette;
import pixel.PaletteListener;
import pixel.sprite.SpriteGui;

public class Toolbar implements PaletteListener {
	private Pane pane;
	private Spinner<Integer> toolSizeSpinner;
	private Tool toolSelected;
	private ArrayList<Tool> tools = new ArrayList<Tool>();
	private ToggleGroup toggleGroup = new ToggleGroup();
	
	public Toolbar(Tool[] tools) {
		checkToolsNotEmpty(tools);
		for (Tool tool : tools) {
			addTool(tool);
		}
		update();
	}
	private void checkToolsNotEmpty(Tool[] tools) {
		if (tools.length == 0) {
			throw new IllegalArgumentException("Tools is empty!");
		}
	}
	public void addTool(Tool tool) {
		tools.add(tool);
		update();
	}
	public void update() {
		if (pane != null) {
			pane.getChildren().clear();
			for (Tool tool : tools) {
				newToolButton(tool);
			}
			if (toolSelected == null && tools.size() > 0) {
				setToolSelected(tools.get(0));
			}
		}
	}
	private ToggleButton newToolButton(Tool tool) {
		ToggleButton toolButton = new ToggleButton(tool.getName());
		toolButton.setToggleGroup(toggleGroup);
		toolButton.setOnAction(event -> {
			setToolSelected(tool);
			toolSizeSpinner.getValueFactory().setValue(tool.getSize());
		});
		pane.getChildren().add(toolButton);
		if (pane.getChildren().size() == 1) {
			toolButton.setSelected(true);
		}
		return toolButton;
	}
	public void setPane(Pane pane) {
		if (pane != null) {
			pane.getChildren().clear();
		}
		this.pane = pane;
		update();
	}
	public void setToolSizeSpinner(Spinner<Integer> spinner) {
		this.toolSizeSpinner = spinner;
	}
	public void setToolSelected(Tool toolSelected) {
		this.toolSelected = toolSelected;
	}
	public Tool getToolSelected() {
		return toolSelected;
	}
	public void useToolSelected(SpriteGui spriteGui, MouseEvent event) {
		toolSelected.use(spriteGui, event);
	}
	public void updateToolColor(int color) {
		for (Tool tool : tools) {
			tool.setColor(color);
		}
	}
	public void updateToolSize(int size) {
		toolSelected.setSize(size);
	}
	@Override
	public void paletteChanged(Palette palette) {
		updateToolColor(palette.getIndex());
	}
}
