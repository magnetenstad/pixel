package pixel.tool;

import javafx.scene.input.MouseEvent;
import pixel.gui.SpriteGui;

/**
 * Tools are used to draw to sprites in specific ways.
 * @author Magne Tenstad
 *
 */
public abstract class Tool {
	protected final String name;
	protected int size = 1;
	protected int color = 0;
	
	public Tool(String name) {
		this.name = name;
	}
	
	/**
	 * Applies some effect to the given sprite.
	 * @param spriteGui
	 * @param event
	 */
	public abstract void use(SpriteGui spriteGui, MouseEvent event);
	
	/**
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets size.
	 * @param size
	 */
	public void setSize(int size) {
		this.size = size;
	}
	
	/**
	 * 
	 * @return size
	 */
	public int getSize() {
		return size;
	}
	
	/**
	 * Sets color.
	 * @param color
	 */
	public void setColor(int color) {
		this.color = color;
	}
	
	/**
	 * Calculates an integer position from a MouseEvent on a SpriteGui.
	 * @param spriteGui
	 * @param event
	 * @return position
	 */
	public static Integer[] eventToPosition(SpriteGui spriteGui, MouseEvent event) {
		Integer[] pos = new Integer[2];
		pos[0] = (int) (event.getX() / spriteGui.getScale());
		pos[1] = (int) (event.getY() / spriteGui.getScale());
		return pos;
	}
}
