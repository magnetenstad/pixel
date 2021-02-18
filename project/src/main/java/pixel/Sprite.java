package pixel;

import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Sprite extends Canvas {
	private GraphicsContext graphicsContext2D = this.getGraphicsContext2D();
	private int scale = 32;
	
	public Sprite() {
		setWidth(640);
		setHeight(640);
		setCursor(Cursor.CROSSHAIR);
		graphicsContext2D.setFill(Color.WHITE);
		graphicsContext2D.fillRect(0, 0, 640, 640);
	}
	
	public void fillPixel(double x, double y) {
		graphicsContext2D.fillRect(((int) x / scale) * scale, ((int) y / scale) * scale, scale, scale);
	}
}
