package pixel.tool;

import javafx.scene.input.MouseEvent;
import pixel.gui.SpriteGui;

public abstract class Tool {
	protected String name;
	protected int size = 1;
	protected int color = 0;
	
	public Tool(String name) {
		setName(name);
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setSize(int size) {
		this.size = size;
	}
	
	public int getSize() {
		return size;
	}
	
	public abstract void use(SpriteGui spriteGui, MouseEvent event);
	
	public void setColor(int color) {
		this.color = color;
	}
	
	public static Integer[] eventToPosition(SpriteGui spriteGui, MouseEvent event) {
		Integer[] pos = new Integer[2];
		pos[0] = (int) (event.getX() / spriteGui.getScale());
		pos[1] = (int) (event.getY() / spriteGui.getScale());
		return pos;
	}
}
