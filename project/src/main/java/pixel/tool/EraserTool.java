package pixel.tool;

import pixel.sprite.Sprite;

public class EraserTool extends Tool {

	public EraserTool() {
		super("Eraser");
	}
	
	@Override
	public void use(Sprite sprite, ToolInputEvent event) {
		int x = event.getX();
		int y = event.getY();
		sprite.clearRect((int) (x - size / 2), (int) (y - size / 2), size, size);
	}
}
