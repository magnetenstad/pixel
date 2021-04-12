package pixel.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javafx.scene.control.Spinner;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import pixel.tool.Tool;
import pixel.tool.Toolbar;
import pixel.tool.ToolbarListener;

public class ToolbarGui extends ToolbarListener {
	private ToggleGroup toggleGroup = new ToggleGroup();
	private Spinner<Integer> toolSizeSpinner;
	private Pane pane;
	private ArrayList<ToolButton> toolButtons = new ArrayList<>();
	private final Toolbar toolbar;
	
	public ToolbarGui(Toolbar toolbar) {
		this.toolbar = toolbar;
		toolbar.addListener(this);
	}
	
	public Toolbar getToolbar() {
		return toolbar;
	}
	
	public void setPane(Pane pane) {
		clearButtonsFromPane(this.pane);
		this.pane = pane;
		addButtonsToPane(this.pane);
	}
	
	public void setToolSizeSpinner(Spinner<Integer> spinner) {
		this.toolSizeSpinner = spinner;
	}
	
	private void clearButtonsFromPane(Pane pane) {
		if (pane != null) {
			for (ToggleButton toolButton : toolButtons) {
				if (pane.getChildren().contains(toolButton)) {
					pane.getChildren().remove(toolButton);
				}
			}
		}
	}
	
	public void addButtonsToPane(Pane pane) {
		if (pane != null) {
			for (ToggleButton toolButton : toolButtons) {
				if (!pane.getChildren().contains(toolButton)) {
					pane.getChildren().add(toolButton);
				}
			}
		}
	}
	
	private class ToolButton extends ToggleButton {
		private final Tool tool;
		public ToolButton(Tool tool) {
			this.tool = tool;
			setText(tool.getName());
			setToggleGroup(toggleGroup);
			setOnAction(event -> {
				toolbar.setToolSelected(tool);
				toolSizeSpinner.getValueFactory().setValue(tool.getSize());
			});
		}
		public Tool getTool() {
			return tool;
		}
	}
	
	@Override
	public void toolbarAddedTool(Toolbar toolbar, Tool tool) {
		ToolButton toolButton = new ToolButton(tool);
		toolButtons.add(toolButton);
		if (pane != null) {
			pane.getChildren().add(toolButton);
		}
	};
	
	@Override
	public void toolbarRemovedTool(Toolbar toolbar, Tool tool) {
		for (ToolButton toolButton : toolButtons) {
			if (toolButton.getTool() == tool) {
				toolButtons.remove(toolButton);
				if (pane != null && pane.getChildren().contains(toolButton)) {
					pane.getChildren().remove(toolButton);
				}
			}
		}
	};
	
	@Override
	public void toolbarSelectedTool(Toolbar toolbar, Tool tool) {
		for (ToolButton toolButton : toolButtons) {
			toolButton.setSelected(toolButton.getTool() == tool);
		}
	};
}
