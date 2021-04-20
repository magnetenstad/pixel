package pixel.tool;

import javafx.scene.input.MouseEvent;
import pixel.gui.SpriteGui;
import pixel.sprite.Sprite;

/**
 * Tools are used to draw to sprites in specific ways.
 * @author Magne Tenstad
 *
 */
public abstract class Tool {
	protected final String name;
	protected int size = 1;
	protected int color = -1;
	
	public Tool(String name) {
		this.name = name;
	}
	
	/**
	 * Applies some effect to the given sprite.
	 * @param spriteGui
	 * @param event
	 */
	public abstract void use(Sprite sprite, ToolInputEvent event);
	
	/**
	 * Applies some effect to the sprite of the given spriteGui.
	 * @param spriteGui
	 * @param event
	 */
	public void use(SpriteGui spriteGui, MouseEvent event) {
		use(spriteGui.getSprite(), new ToolInputEvent(spriteGui, event));
	}
	
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
}
