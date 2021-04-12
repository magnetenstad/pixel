package pixel.tool;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import pixel.gui.SpriteGui;
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
	public void use(SpriteGui spriteGui, MouseEvent event) {
		Sprite sprite = spriteGui.getSprite();
		sprite.clearRect((int) (event.getX()/spriteGui.getScale() - size / 2), (int) (event.getY()/spriteGui.getScale() - size / 2), size, size);
	}

	@Override
	public void setColor(int color) {}

	@Override
	public int getSize() {
		return size;
	}
}
