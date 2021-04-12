package pixel.tool;

import java.util.Vector;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import pixel.gui.SpriteGui;
import pixel.sprite.Sprite;

public interface Tool {
	
	public void setName(String name);
	
	public String getName();
	
	public void setSize(int size);
	
	public int getSize();
	
	public void use(SpriteGui spriteGui, MouseEvent event);
	
	public void setColor(int color);
	
	public static Integer[] eventToPosition(SpriteGui spriteGui, MouseEvent event) {
		Integer[] pos = new Integer[2];
		pos[0] = (int) (event.getX() / spriteGui.getScale());
		pos[1] = (int) (event.getY() / spriteGui.getScale());
		return pos;
	}
}
