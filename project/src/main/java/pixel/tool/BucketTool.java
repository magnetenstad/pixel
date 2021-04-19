package pixel.tool;

import java.util.List;

import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import pixel.cursorlist.CursorListEvent;
import pixel.gui.SpriteGui;
import pixel.sprite.Sprite;
import pixel.sprite.SpriteLayer;

public class BucketTool extends Tool {
	private List<Point2D> neighbours = List.of(new Point2D(1, 0), new Point2D(0, -1), new Point2D(-1, 0), new Point2D(0, 1));

	public BucketTool() {
		super("Bucket");
	}
	
	@Override
	public void use(SpriteGui spriteGui, MouseEvent event) {
		Sprite sprite = spriteGui.getSprite();
		if (event.isPrimaryButtonDown()) {
			Integer[] pos = Tool.eventToPosition(spriteGui, event);
			int colorMatch = sprite.getSelected().getPixel(pos[0], pos[1]);
			if (color != colorMatch) {
				fill(sprite, colorMatch, pos[0], pos[1]);
			}
			sprite.notifyListeners(CursorListEvent.ElementChanged, sprite.getSelected());
		}
	}
	
	private void fill(Sprite sprite, int colorMatch, int x, int y) {
		SpriteLayer spriteLayer = sprite.getSelected();
		if (spriteLayer.isPointInCanvas(x, y) && spriteLayer.getPixel(x, y) == colorMatch) {
			sprite.fillRect(x, y, 1, 1, color, false);
			for (Point2D neighbour : neighbours) {
				fill(sprite, colorMatch, x + (int) neighbour.getX(), y + (int) neighbour.getY());
			}
		}
	}
}
