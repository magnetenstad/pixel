package pixel;

import java.util.ArrayList;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Sprite {
	private ArrayList<Canvas> canvasLayers = new ArrayList<Canvas>();
	private Canvas canvasLayerCurrent;
	private Canvas visibleCanvas;
	private int width;
	private int height;
	private int scale = 32;
	
	public Sprite(int width, int height) {
		initVisibleCanvas(width, height);
		setWidth(width);
		setHeight(height);
		addCanvasLayer();
		setCanvasLayerCurrent(0);
	}
	
	public void initVisibleCanvas(int width, int height) {
		visibleCanvas = new Canvas(width, height);
		visibleCanvas.getGraphicsContext2D().setFill(Color.WHITE);
		visibleCanvas.getGraphicsContext2D().fillRect(0, 0, width, height);
	}
	
	public void setPixel(double x, double y, Color color) {
		GraphicsContext graphics = getCanvasLayerCurrent().getGraphicsContext2D();
		graphics.setFill(color);
		graphics.fillRect(((int) x / scale) * scale, ((int) y / scale) * scale, scale, scale);
		updateVisibleCanvas();
	}
	
	public void clearPixel(double x, double y) {
		GraphicsContext graphics = getCanvasLayerCurrent().getGraphicsContext2D();
		graphics.clearRect(((int) x / scale) * scale, ((int) y / scale) * scale, scale, scale);
		updateVisibleCanvas();
	}
	
	private void updateVisibleCanvas() {
		visibleCanvas.getGraphicsContext2D().fillRect(0, 0, width, height);
		for (Canvas canvasLayer : canvasLayers) {
			visibleCanvas.getGraphicsContext2D().drawImage(canvasLayer.snapshot(null, null), 0, 0);
		}
	}
	
	public void addCanvasLayer() {
		Canvas canvas = new Canvas(getWidth(), getHeight());
		canvasLayers.add(canvas);
	}
	
	public Canvas getCanvasLayerCurrent() {
		return canvasLayerCurrent;
	}
	
	public void setCanvasLayerCurrent(int layerIndex) {
		canvasLayerCurrent = canvasLayers.get(layerIndex);
	}

	public int getWidth() {
		return width;
	}
	
	public void setWidth(int width) {
		this.width = width;
		visibleCanvas.setWidth(width);
	}
	
	public int getHeight() {
		return height;
	}
	
	public void setHeight(int height) {
		this.height = height;
		visibleCanvas.setHeight(height);
	}
	
	public Canvas getVisibleCanvas() {
		return visibleCanvas;
	}
}
