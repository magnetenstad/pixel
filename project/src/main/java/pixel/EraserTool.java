package pixel;

import javafx.scene.paint.Color;

public class EraserTool implements Tool {
	private String name = "Eraser";
	private double size = 1.0;
	private Color color = Color.WHITE;
	
	@Override
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public void setSize(double size) {
		this.size = size;
	}

	@Override
	public void use(Sprite sprite, double x, double y) {
		sprite.getGraphicsContext2D().setFill(color);
		sprite.fillPixel(x, y);
	}
}
