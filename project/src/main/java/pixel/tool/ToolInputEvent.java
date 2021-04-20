package pixel.tool;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import pixel.gui.SpriteGui;

/**
 * Since MouseEvent is fired from the scaled SpriteGui,
 * we need to adjust the position.
 * Additionally, this may be used to test tools without gui.
 * @author Magne Tenstad
 */
public class ToolInputEvent {
	private final int x;
	private final int y;
	private final boolean primaryButtonDown;
	private final boolean secondaryButtonDown;
	private MouseButton button;
	
	public ToolInputEvent(int x, int y, MouseButton button, boolean primaryButtonDown, boolean secondaryButtonDown) {
		this.x = x;
		this.y = y;
		this.button = button;
		this.primaryButtonDown = primaryButtonDown;
		this.secondaryButtonDown = secondaryButtonDown;
	}
	
	public ToolInputEvent(SpriteGui spriteGui, MouseEvent event) {
		x = (int) (event.getX() / spriteGui.getScale());
		y = (int) (event.getY() / spriteGui.getScale());
		button = event.getButton();
		primaryButtonDown = event.isPrimaryButtonDown();
		secondaryButtonDown = event.isSecondaryButtonDown();
	}
	
	/**
	 * 
	 * @return x
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * 
	 * @return y
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * 
	 * @return primaryButtonDown
	 */
	public boolean isPrimaryButtonDown() {
		return primaryButtonDown;
	}
	
	/**
	 * 
	 * @return secondaryButtonDown
	 */
	public boolean isSecondaryButtonDown() {
		return secondaryButtonDown;
	}
	
	/**
	 * 
	 * @return button
	 */
	public MouseButton getButton() {
		return button;
	}
}
