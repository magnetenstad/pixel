package pixel.tool;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import pixel.sprite.Sprite;

public interface Tool {
	
	public void setName(String name);
	
	public String getName();
	
	public void setSize(int size);
	
	public void use(Sprite sprite, MouseEvent event);
	
	public void setColor(Color color);
	
}
