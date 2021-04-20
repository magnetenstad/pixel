package pixel.gui;

import java.util.ArrayList;

import javafx.scene.control.Spinner;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import pixel.cursorlist.CursorList;
import pixel.cursorlist.CursorListEvent;
import pixel.cursorlist.CursorListListener;
import pixel.palette.Palette;
import pixel.tool.Tool;
import pixel.tool.Toolbar;

/**
 * A gui to interact with a Toolbar.
 * Should listen to its Toolbar and a PaletteGui.
 * @author Magne Tenstad
 */
public class ToolbarGui implements CursorListListener {
	private ToggleGroup toggleGroup = new ToggleGroup();
	private ArrayList<ToolButton> toolButtons = new ArrayList<>();
	private Spinner<Integer> toolSizeSpinner;
	private Pane pane;
	private final Toolbar toolbar;
	
	public ToolbarGui(Toolbar toolbar) {
		this.toolbar = toolbar;
		toolbar.addListener(this);
	}
	
	/**
	 * 
	 * @return toolbar
	 */
	public Toolbar getToolbar() {
		return toolbar;
	}
	
	/**
	 * Sets the pane for building the toolbar.
	 * @param pane
	 */
	public void setPane(Pane pane) {
		clearButtonsFromPane(this.pane);
		this.pane = pane;
		addButtonsToPane(this.pane);
	}
	
	/**
	 * Sets the toolSizeSpinner, so it can be updated when changing tool.
	 * @param spinner
	 */
	public void setToolSizeSpinner(Spinner<Integer> spinner) {
		this.toolSizeSpinner = spinner;
	}
	
	/**
	 * Clears gui from the given pane.
	 * @param pane
	 */
	private void clearButtonsFromPane(Pane pane) {
		if (pane != null) {
			for (ToggleButton toolButton : toolButtons) {
				if (pane.getChildren().contains(toolButton)) {
					pane.getChildren().remove(toolButton);
				}
			}
		}
	}
	
	/**
	 * Builds gui to the given pane.
	 * @param pane
	 */
	private void addButtonsToPane(Pane pane) {
		if (pane != null) {
			for (ToggleButton toolButton : toolButtons) {
				if (!pane.getChildren().contains(toolButton)) {
					pane.getChildren().add(toolButton);
				}
			}
		}
	}
	
	/**
	 * An extension of ToggleButton to represent a selectable tool.
	 * @author Magne Tenstad
	 */
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
	
	/**
	 * Listens to toolbar and PaletteGui.
	 */
	@Override
	public void cursorListChanged(CursorList<?> cursorList, CursorListEvent event, Object element) {
		if (cursorList instanceof PaletteGui) {
			Palette palette = ((PaletteGui) cursorList).getSelected();
			if (palette != null && palette.getCursor() != -1) {
				toolbar.updateToolColor(palette.getCursor());
			}
		}
		else if (cursorList == toolbar) {
			switch (event) {
			case ElementAdded:
				if (element instanceof Tool) {
					ToolButton toolButton = new ToolButton((Tool) element);
					toolButtons.add(toolButton);
					if (pane != null) {
						pane.getChildren().add(toolButton);
					}
				}
				break;
			case CursorChanged:
				for (ToolButton toolButton : toolButtons) {
					toolButton.setSelected(toolButton.getTool() == cursorList.getSelected());
				}
				break;
			case ElementRemoved:
				for (ToolButton toolButton : toolButtons) {
					if (toolButton.getTool() == element) {
						toolButtons.remove(toolButton);
						if (pane != null && pane.getChildren().contains(toolButton)) {
							pane.getChildren().remove(toolButton);
						}
					}
				}
				break;
			default:
				break;
			}
		}
	}
}
