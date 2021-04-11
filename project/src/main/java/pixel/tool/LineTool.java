package pixel.tool;

import javafx.geometry.Point2D;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import pixel.sprite.Sprite;
import pixel.sprite.SpriteGui;

public class LineTool implements Tool {
	private String name = "Line";
	private int size = 1;
	private int color = 1;
	private Point2D startPos;
	
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
		if (event.getButton() == MouseButton.PRIMARY) {
			if (event.isPrimaryButtonDown()) {
				if (startPos == null) {
					startPos = point(event.getX(), event.getY(), spriteGui);
				}
			}
			else {
				Point2D endPos = point(event.getX(), event.getY(), spriteGui);
				
				double dx = endPos.getX() - startPos.getX();
				double dy = endPos.getY() - startPos.getY();
				double angle = (0 <= dx) ? Math.atan(dy / dx) : Math.PI + Math.atan(dy / dx);
				double length = Math.ceil(Math.sqrt(Math.pow(dy, 2) + Math.pow(dx, 2)));
				
				for (int i = 0; i <= length; i++) {
					double x = startPos.getX() + i * Math.cos(angle);
					double y = startPos.getY() + i * Math.sin(angle);
					sprite.fillRect((int) (x - size / 2), (int) (y - size / 2), size, size, color, false);
				}
				sprite.notifyListeners();
				startPos = null;
			}
		}
	}
	private Point2D point(double x, double y, SpriteGui spriteGui) {
		return new Point2D(Math.floor(x/spriteGui.getScale()) + 0.5, Math.floor(y/spriteGui.getScale()) + 0.5);
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
