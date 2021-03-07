package pixel.tool;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import pixel.sprite.Sprite;

public class PencilTool implements Tool {
	private String name = "Pencil";
	private int size = 1;
	private int color = 1;
	
	@Override
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public void setSize(int size) {
		this.size = size;
	}
	
	public void setColor(int color) {
		this.color = color;
	}
	
	@Override
	public void use(Sprite sprite, MouseEvent event) {
		if (event.isPrimaryButtonDown()) {
			sprite.fillRect((int) (event.getX()/sprite.getScale() - size / 2), (int) (event.getY()/sprite.getScale() - size / 2), size, size, color);
		}
		else if (event.isSecondaryButtonDown()) {
			sprite.clearRect((int) (event.getX()/sprite.getScale() - size / 2), (int) (event.getY()/sprite.getScale() - size / 2), size, size);
		}
	}
	
	@Override
	public int getSize() {
		return size;
	}
}
