package pixel.tool;

import java.util.Collection;

import pixel.cursorlist.CursorList;
import pixel.cursorlist.CursorListEvent;
import pixel.cursorlist.CursorListListener;

public class Toolbar extends CursorList<Tool> implements CursorListListener {

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
	
	@Override
	public void cursorListChanged(CursorList<?> cursorList, CursorListEvent event, Object element) {
		updateToolColor(cursor);
	}
}
