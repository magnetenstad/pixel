package pixel.tool;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import pixel.palette.Palette;
import pixel.palette.PaletteListener;

public class Toolbar implements Iterable<Tool>, PaletteListener {
	private Tool toolSelected;
	private ArrayList<Tool> tools = new ArrayList<>();
	private ArrayList<ToolbarListener> listeners = new ArrayList<>();
	
	public void addTool(Tool tool) {
		tools.add(tool);
		notifyAddedTool(tool);
		if (toolSelected == null) {
			setToolSelected(tool);
		}
	}
	public void addTools(Collection<Tool> tools) {
		for (Tool tool : tools) {
			addTool(tool);
		}
	}
	public void removeTool(Tool tool) {
		tools.remove(tool);
		if (toolSelected == tool) {
			toolSelected = null;
		}
		notifyRemovedTool(tool);
	}
	public void setToolSelected(Tool tool) {
		if (!tools.contains(tool)) {
			throw new IllegalArgumentException();
		}
		this.toolSelected = tool;
		notifySelectedTool(tool);
	}
	public void setToolSelected(int index) {
		if (!(0 <= index && index < size())) {
			throw new IllegalArgumentException();
		}
		setToolSelected(tools.get(index));
	}
	public Tool getToolSelected() {
		return toolSelected;
	}
	public void updateToolColor(int color) {
		for (Tool tool : tools) {
			tool.setColor(color);
		}
	}
	public void updateToolSize(int size) {
		toolSelected.setSize(size);
	}
	public int size() {
		return tools.size();
	}
	@Override
	public void paletteChanged(Palette palette) {
		updateToolColor(palette.getIndex());
	}
	public void notifyAddedTool(Tool tool) {
		for (ToolbarListener listener : listeners) {
			listener.toolbarAddedTool(this, tool);
		}
	}
	public void notifyRemovedTool(Tool tool) {
		for (ToolbarListener listener : listeners) {
			listener.toolbarRemovedTool(this, tool);
		}
	}
	public void notifySelectedTool(Tool tool) {
		for (ToolbarListener listener : listeners) {
			listener.toolbarSelectedTool(this, tool);
		}
	}
	public void addListener(ToolbarListener listener) {
		listeners.add(listener);
	}
	public void removeListener(ToolbarListener listener) {
		listeners.remove(listener);
	}
	@Override
	public Iterator<Tool> iterator() {
		return tools.iterator();
	}
}
