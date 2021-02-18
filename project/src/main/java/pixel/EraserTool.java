package pixel;

import javafx.scene.input.MouseEvent;

public class EraserTool implements Tool {
	private String name = "Eraser";
	private double size = 1.0;
	
	@Override
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public void setSize(double size) {
		this.size = size;
	}

	@Override
	public void use(Sprite sprite, MouseEvent event) {
		sprite.clearPixel(event.getX(), event.getY());
	}
}
