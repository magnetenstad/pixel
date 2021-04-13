package pixel.tool;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import pixel.gui.SpriteGui;
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
	public void use(SpriteGui spriteGui, MouseEvent event) {
		Integer[] pos = Tool.eventToPosition(spriteGui, event);
		Sprite sprite = spriteGui.getSprite();
		if (event.isPrimaryButtonDown()) {
			sprite.fillRect((int) (pos[0] - size / 2), (int) (pos[1] - size / 2), size, size, color);
		}
		else if (event.isSecondaryButtonDown()) {
			sprite.clearRect((int) (pos[0] - size / 2), (int) (pos[1] - size / 2), size, size);
		}
	}
	
	@Override
	public int getSize() {
		return size;
	}
}
