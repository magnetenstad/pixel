package pixel.tool;

import pixel.sprite.Sprite;

public class PencilTool extends Tool {
	
	public PencilTool() {
		super("Pencil");
	}
	
	@Override
	public void use(Sprite sprite, ToolInputEvent event) {
		int x = event.getX();
		int y = event.getY();
		if (event.isPrimaryButtonDown()) {
			sprite.fillRect((int) (x - size / 2), (int) (y - size / 2), size, size, color);
		}
		else if (event.isSecondaryButtonDown()) {
			sprite.clearRect((int) (x - size / 2), (int) (y - size / 2), size, size);
		}
	}
}
