package pixel.tool;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import pixel.sprite.Sprite;

public class EraserTool implements Tool {
	private String name = "Eraser";
	private int size = 1;
	
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

	@Override
	public void use(Sprite sprite, MouseEvent event) {
		sprite.clearRect((int) event.getX()/sprite.getScale() - size/2, (int) event.getY()/sprite.getScale() - size/2, size, size);
	}

	@Override
	public void setColor(Color color) {}

	@Override
	public int getSize() {
		return size;
	}
}
