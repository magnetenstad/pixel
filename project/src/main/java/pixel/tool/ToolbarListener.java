package pixel.tool;

public abstract class ToolbarListener {
	
	public void toolbarAddedTool(Toolbar toolbar, Tool tool) {};
	
	public void toolbarRemovedTool(Toolbar toolbar, Tool tool) {};
	
	public void toolbarSelectedTool(Toolbar toolbar, Tool tool) {};
	
}
