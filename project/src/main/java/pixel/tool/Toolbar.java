package pixel.tool;

import java.util.Collection;

import pixel.SelectableList;
import pixel.SelectableListListener;

public class Toolbar extends SelectableList<Tool> implements SelectableListListener {

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
	public void listAddedElement(SelectableList<?> selectableList, Object element) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void listRemovedElement(SelectableList<?> selectableList, Object element) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void listSetIndex(SelectableList<?> selectableList, int index) {
		updateToolColor(index);
	}
}
