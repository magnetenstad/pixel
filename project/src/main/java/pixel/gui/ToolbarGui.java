package pixel.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javafx.scene.control.Spinner;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import pixel.CursorList;
import pixel.CursorListListener;
import pixel.tool.Tool;
import pixel.tool.Toolbar;

public class ToolbarGui implements CursorListListener {
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
	
	private void addButtonsToPane(Pane pane) {
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
				toolbar.select(tool);
				toolSizeSpinner.getValueFactory().setValue(tool.getSize());
			});
		}
		public Tool getTool() {
			return tool;
		}
	}

	@Override
	public void listAddedElement(CursorList<?> selectableList, Object element) {
		if (element instanceof Tool) {
			ToolButton toolButton = new ToolButton((Tool) element);
			toolButtons.add(toolButton);
			if (pane != null) {
				pane.getChildren().add(toolButton);
			}
		}
	}
	
	@Override
	public void listRemovedElement(CursorList<?> selectableList, Object element) {
		for (ToolButton toolButton : toolButtons) {
			if (toolButton.getTool() == element) {
				toolButtons.remove(toolButton);
				if (pane != null && pane.getChildren().contains(toolButton)) {
					pane.getChildren().remove(toolButton);
				}
			}
		}
	}

	@Override
	public void listSetCursor(CursorList<?> selectableList, int index) {
		for (ToolButton toolButton : toolButtons) {
			toolButton.setSelected(toolButton.getTool() == selectableList.get(index));
		}
	};
}
