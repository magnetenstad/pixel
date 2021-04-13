package pixel.tool;

import java.util.Collection;

import pixel.CursorList;
import pixel.CursorListListener;

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
	public void listAddedElement(CursorList<?> selectableList, Object element) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void listRemovedElement(CursorList<?> selectableList, Object element) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void listSetCursor(CursorList<?> selectableList, int cursor) {
		updateToolColor(cursor);
	}
}
