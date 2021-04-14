package pixel.tool;

import java.util.Collection;

import pixel.cursorlist.CursorList;
import pixel.cursorlist.CursorListEvent;
import pixel.cursorlist.CursorListListener;
import pixel.gui.PaletteGui;

public class Toolbar extends CursorList<Tool> {

	public void addTools(Collection<Tool> tools) {
		for (Tool tool : tools) {
			add(tool);
		}
	}
	
	public void updateToolColor(int color) {
		for (Tool tool : elements) {
			tool.setColor(color);
		}
	}
	public void updateToolSize(int size) {
		getSelected().setSize(size);
	}
}
