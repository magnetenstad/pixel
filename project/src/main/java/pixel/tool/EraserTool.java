package pixel.tool;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import pixel.gui.SpriteGui;
import pixel.sprite.Sprite;

public class EraserTool extends Tool {

	public EraserTool() {
		super("Eraser");
	}
	
	@Override
	public void use(SpriteGui spriteGui, MouseEvent event) {
		Integer[] pos = Tool.eventToPosition(spriteGui, event);
		Sprite sprite = spriteGui.getSprite();
		sprite.clearRect((int) (pos[0] - size / 2), (int) (pos[1] - size / 2), size, size);
	}
}
