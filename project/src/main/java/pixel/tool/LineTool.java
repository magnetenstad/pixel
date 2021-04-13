package pixel.tool;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import pixel.gui.SpriteGui;
import pixel.sprite.Sprite;

public class LineTool extends Tool {
	private Integer[] startPos;
	
	public LineTool() {
		super("Line");
	}
	
	@Override
	public void use(SpriteGui spriteGui, MouseEvent event) {
		Sprite sprite = spriteGui.getSprite();
		if (event.getButton() == MouseButton.PRIMARY) {
			if (event.isPrimaryButtonDown()) {
				if (startPos == null) {
					startPos = Tool.eventToPosition(spriteGui, event);
				}
			}
			else {
				Integer[] endPos = Tool.eventToPosition(spriteGui, event);
				
				double dx = endPos[0] - startPos[0];
				double dy = endPos[1] - startPos[1];
				double angle = (0 <= dx) ? Math.atan(dy / dx) : Math.PI + Math.atan(dy / dx);
				double length = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
				double di = length / Math.max(Math.abs(dx), Math.abs(dy));
				
				for (int i = 0; i <= length; i += di) {
					double x = startPos[0] + i * Math.cos(angle) + 0.5;
					double y = startPos[1] + i * Math.sin(angle) + 0.5;
					sprite.fillRect((int) (x - size / 2), (int) (y - size / 2), size, size, color, false);
				}
				sprite.spriteChanged();
				startPos = null;
			}
		}
	}
}
