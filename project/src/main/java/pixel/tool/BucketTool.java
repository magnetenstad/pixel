package pixel.tool;

import java.util.List;

import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import pixel.cursorlist.CursorListEvent;
import pixel.gui.SpriteGui;
import pixel.sprite.Sprite;
import pixel.sprite.SpriteLayer;

public class BucketTool extends Tool {
	private List<Offset> offsets = List.of(new Offset(1, 0), new Offset(0, -1), new Offset(-1, 0), new Offset(0, 1));

	public BucketTool() {
		super("Bucket");
	}
	
	private class Offset {
		public final int x;
		public final int y;
		
		public Offset(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
	
	@Override
	public void use(Sprite sprite, ToolInputEvent event) {
		int x = event.getX();
		int y = event.getY();
		if (event.isPrimaryButtonDown() && sprite.getSelected() != null && sprite.getSelected().isPointInCanvas(x, y)) {
			int colorMatch = sprite.getSelected().getPixel(x, y);
			if (color != colorMatch) {
				fill(sprite, colorMatch, x, y);
			}
			sprite.notifyListeners(CursorListEvent.ElementChanged, sprite.getSelected());
		}
	}
	
	/**
	 * A recursive method to apply a fill to a sprite.
	 * @param sprite
	 * @param colorMatch
	 * @param x
	 * @param y
	 */
	private void fill(Sprite sprite, int colorMatch, int x, int y) {
		SpriteLayer spriteLayer = sprite.getSelected();
		if (spriteLayer.isPointInCanvas(x, y) && spriteLayer.getPixel(x, y) == colorMatch) {
			sprite.fillRect(x, y, 1, 1, color, false);
			for (Offset offset : offsets) {
				fill(sprite, colorMatch, x + offset.x, y + offset.y);
			}
		}
	}
}
