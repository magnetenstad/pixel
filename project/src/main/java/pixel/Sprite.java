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
	private SnapshotParameters snapshotParameters = new SnapshotParameters();
	private int width;
	private int height;
	private int scale = 16;
	
	public Sprite(int width, int height) {
		width = width * scale;
		height = height * scale;
		setWidth(width);
		setHeight(height);
		imageView.setSmooth(false);
		writableImage = new WritableImage(width, height);
		snapshotParameters.setFill(Color.TRANSPARENT);
		imageView.setImage(writableImage);
		updateVisibleCanvas();
	}
	
	public void updateVisibleCanvas() {
		Canvas combinedCanvas = new Canvas(width, height);
		combinedCanvas.getGraphicsContext2D().setImageSmoothing(false);
		for (CanvasLayer canvasLayer : canvasLayers) {
			combinedCanvas.getGraphicsContext2D().drawImage(canvasLayer.snapshot(snapshotParameters, null), 0, 0);
		}
		combinedCanvas.snapshot(null, writableImage);
	}
	
	public void setPixel(double x, double y, Color color) {
		if (!canvasLayerCurrentIsEditable()) {
			return;
		}
		GraphicsContext graphics = getCanvasLayerCurrent().getGraphicsContext2D();
		graphics.setFill(color);
		graphics.fillRect(((int) x / scale) * scale, ((int) y / scale) * scale, scale, scale);
		updateVisibleCanvas();
	}
	
	public void clearPixel(double x, double y) {
		if (!canvasLayerCurrentIsEditable()) {
			return;
		}
		GraphicsContext graphics = getCanvasLayerCurrent().getGraphicsContext2D();
		graphics.clearRect(((int) x / scale) * scale, ((int) y / scale) * scale, scale, scale);
		updateVisibleCanvas();
	}
	
	public boolean canvasLayerCurrentIsEditable() {
		Canvas canvas = getCanvasLayerCurrent();
		return (canvas != null && canvas.isVisible());
	}
	
	public Canvas addCanvasLayer(Pane layersPane, String name) {
		CanvasLayer canvasLayer = new CanvasLayer(this, layersPane, name, getWidth(), getHeight());
		canvasLayers.add(canvasLayer);
		updateVisibleCanvas();
		return canvasLayer;
	}
	public CanvasLayer getCanvasLayer(int index) {
		return canvasLayers.get(index);
	}
	
	public HBox getHBoxOfCanvasLayer(Canvas canvasLayer) {
		int index = canvasLayers.indexOf(canvasLayer);
		return canvasLayerHBoxes.get(index);
	}
	
	public void removeCanvasLayer(Canvas canvasLayer) {
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
	
	public int getWidth() {
		return width;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void setHeight(int height) {
		this.height = height;
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
