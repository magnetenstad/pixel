package pixel.tool;

import javafx.scene.input.MouseEvent;
import pixel.Sprite;

public interface Tool {
	
	public void setName(String name);
	
	public String getName();
	
	public void setSize(double size);
	
	public void use(Sprite sprite, MouseEvent event);
	
}
