package pixel;

import java.util.ArrayList;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Sprite {
	private ArrayList<CanvasLayer> canvasLayers = new ArrayList<CanvasLayer>();
	private ArrayList<HBox> canvasLayerHBoxes = new ArrayList<HBox>();
	private ImageView imageView = new ImageView();
	private WritableImage writableImage;
	private CanvasLayer canvasLayerCurrent;
	private int width;
	private int height;
	private int scale = 32;
	
	public Sprite(int width, int height) {
		this.width = width;
		this.height = height;
		imageView.setSmooth(false);
		writableImage = new WritableImage(width * scale, height * scale);
		imageView.setImage(writableImage);
		updateVisibleCanvas();
	}
	
	public void updateVisibleCanvas() {
		Canvas combinedCanvas = new Canvas(getImageWidth(), getImageHeight());
		GraphicsContext graphics = combinedCanvas.getGraphicsContext2D();
		graphics.setImageSmoothing(false);
		for (CanvasLayer canvasLayer : canvasLayers) {
			if (canvasLayer.isVisible()) {
				for (int x = 0; x < canvasLayer.getWidth(); x++) {
					for (int y = 0; y < canvasLayer.getHeight(); y++) {
						graphics.setFill(canvasLayer.getPixel(x, y));
						graphics.fillRect(x * scale, y * scale, scale, scale);
					}
				}
			}
		}
		combinedCanvas.snapshot(null, writableImage);
	}
	
	public void setPixel(double x, double y, Color color) {
		if (!canvasLayerCurrentIsEditable()) {
			return;
		}
		CanvasLayer canvasLayer = getCanvasLayerCurrent();
		canvasLayer.setFill(color);
		canvasLayer.fillPixel(((int) x / scale), ((int) y / scale));
		updateVisibleCanvas();
	}
	
	public void clearPixel(double x, double y) {
		if (!canvasLayerCurrentIsEditable()) {
			return;
		}
		CanvasLayer canvasLayer = getCanvasLayerCurrent();
		canvasLayer.clearPixel(((int) x / scale), ((int) y / scale));
		updateVisibleCanvas();
	}
	
	public boolean canvasLayerCurrentIsEditable() {
		CanvasLayer canvas = getCanvasLayerCurrent();
		return (canvas != null && canvas.isVisible());
	}
	
	public CanvasLayer addCanvasLayer(Pane layersPane, String name) {
		CanvasLayer canvasLayer = new CanvasLayer(this, layersPane, name, width, height);
		canvasLayers.add(canvasLayer);
		updateVisibleCanvas();
		return canvasLayer;
	}
	
	public CanvasLayer getCanvasLayer(int index) {
		return canvasLayers.get(index);
	}
	
	public HBox getHBoxOfCanvasLayer(CanvasLayer canvasLayer) {
		int index = canvasLayers.indexOf(canvasLayer);
		return canvasLayerHBoxes.get(index);
	}
	
	public void removeCanvasLayer(CanvasLayer canvasLayer) {
		if (canvasLayer != null && !canvasLayers.contains(canvasLayer)) {
			return;
		}
		canvasLayers.remove(canvasLayer);
		setCanvasLayerCurrent(null);
		updateVisibleCanvas();
	}
	
	public CanvasLayer getCanvasLayerCurrent() {
		return canvasLayerCurrent;
	}
	
	public void setCanvasLayerCurrent(int canvasLayerIndex) {
		canvasLayerCurrent = canvasLayers.get(canvasLayerIndex);
	}
	
	public void setCanvasLayerCurrent(CanvasLayer canvasLayer) {
		if (canvasLayer != null && !canvasLayers.contains(canvasLayer)) {
			throw new IllegalArgumentException("This canvas is not a layer of this sprite!");
		}
		canvasLayerCurrent = canvasLayer;
	}
	
	public int getCanvasLayerCount() {
		return canvasLayers.size();
	}
	
	public double getImageWidth() {
		return writableImage.getWidth();
	}
	
	public double getImageHeight() {
		return writableImage.getHeight();
	}
	
	public ImageView getImageView() {
		return imageView;
	}

	public void setCanvasLayerVisible(Canvas canvas, boolean selected) {
		canvas.setVisible(selected);
		updateVisibleCanvas();
	}

	public ArrayList<CanvasLayer> getCanvasLayers() {
		return canvasLayers;
	}
}
