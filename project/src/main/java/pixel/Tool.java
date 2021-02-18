package pixel;

import javafx.scene.input.MouseEvent;

public interface Tool {
	
	public void setName(String name);
	
	public String getName();
	
	public void setSize(double size);
	
	public void use(Sprite sprite, MouseEvent event);
	
}
