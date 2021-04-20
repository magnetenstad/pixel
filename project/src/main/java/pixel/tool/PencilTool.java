package pixel.tool;

import javafx.scene.input.MouseEvent;
import pixel.gui.SpriteGui;
import pixel.sprite.Sprite;

public class PencilTool extends Tool {
	
	public PencilTool() {
		super("Pencil");
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
}
