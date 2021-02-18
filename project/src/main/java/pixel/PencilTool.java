package pixel;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class PencilTool implements Tool {
	private String name = "Pencil";
	private double size = 1.0;
	private Color color = Color.BLACK;
	
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
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	@Override
	public void use(Sprite sprite, MouseEvent event) {
		if (event.isPrimaryButtonDown()) {
			sprite.setPixel(event.getX(), event.getY(), color);
		}
		else if (event.isSecondaryButtonDown()) {
			sprite.clearPixel(event.getX(), event.getY());
		}
	}
}
