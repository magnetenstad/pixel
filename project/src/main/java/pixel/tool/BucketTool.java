package pixel.tool;

import java.util.List;

import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import pixel.gui.SpriteGui;
import pixel.sprite.Sprite;
import pixel.sprite.SpriteLayer;

public class BucketTool implements Tool {
	private String name = "Bucket";
	private int size = 1;
	private int color = 1;
	private List<Point2D> neighbours = List.of(new Point2D(1, 0), new Point2D(0, -1), new Point2D(-1, 0), new Point2D(0, 1));
	
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
		if (event.isPrimaryButtonDown()) {
			Integer[] pos = Tool.eventToPosition(spriteGui, event);
			int colorMatch = sprite.getSelected().getPixel(pos[0], pos[1]);
			if (color != colorMatch) {
				fill(sprite, colorMatch, pos[0], pos[1]);
			}
			sprite.spriteChanged();
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
	
	@Override
	public void setColor(int color) {
		this.color = color;
	}

	@Override
	public int getSize() {
		return size;
	}
}
