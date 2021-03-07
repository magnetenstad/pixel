package pixel.tool;

import java.util.List;

import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
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
	public void use(Sprite sprite, MouseEvent event) {
		if (event.isPrimaryButtonDown()) {
			int x = (int) (event.getX() / sprite.getScale());
			int y = (int) (event.getY() / sprite.getScale());
			int colorMatch = sprite.getSpriteLayerCurrent().getPixel(x, y);
			if (color != colorMatch) {
				fill(sprite, colorMatch, x, y);
			}
		}
	}
	
	private void fill(Sprite sprite, int colorMatch, int x, int y) {
		SpriteLayer spriteLayer = sprite.getSpriteLayerCurrent();
		if (spriteLayer.isPointInCanvas(x, y) && spriteLayer.getPixel(x, y) == colorMatch) {
			sprite.fillRect(x, y, 1, 1, color);
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
