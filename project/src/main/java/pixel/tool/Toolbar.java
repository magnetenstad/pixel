package pixel.tool;

import java.util.Collection;

import pixel.cursorlist.CursorList;
import pixel.cursorlist.CursorListEvent;
import pixel.cursorlist.CursorListListener;
import pixel.gui.PaletteGui;

/**
 * An extension of CursorList<Tool>,
 * with methods for updating color and size.
 * @author Magne Tenstad
 *
 */
public class Toolbar extends CursorList<Tool> {
	
	/**
	 * Adds the given collection of tools, one by one.
	 * @param tools
	 */
	public void addTools(Collection<Tool> tools) {
		for (Tool tool : tools) {
			add(tool);
		}
	}
	
	/**
	 * Updates all tools with the given color.
	 * @param color
	 */
	public void updateToolColor(int color) {
		for (Tool tool : elements) {
			tool.setColor(color);
		}
	}
	
	/**
	 * Updates the selected tool with the given size.
	 * @param size
	 */
	public void updateToolSize(int size) {
		getSelected().setSize(size);
	}
}
