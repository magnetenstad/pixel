package pixel.sprite;

import java.util.ArrayList;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Sprite {
	private ArrayList<SpriteLayer> canvasLayers = new ArrayList<SpriteLayer>();
	private ArrayList<HBox> canvasLayerHBoxes = new ArrayList<HBox>();
	private ImageView imageView = new ImageView();
	private ToggleGroup canvasLayerToggleGroup = new ToggleGroup();
	private WritableImage writableImage;
	private SpriteLayer canvasLayerCurrent;
	private Pane layersPane;
	private int width;
	private int height;
	private int scale = 32;
	
	public Sprite(Pane layersPane, int width, int height) {
		this.layersPane = layersPane;
		this.width = width;
		this.height = height;
		imageView.setSmooth(false);
		writableImage = new WritableImage(width * scale, height * scale);
		imageView.setImage(writableImage);
		setCanvasLayerCurrent(addCanvasLayer());
		updateImageView();
	}
	
	public void updateImageView() {
		Canvas combinedCanvas = new Canvas(getImageWidth(), getImageHeight());
		GraphicsContext graphics = combinedCanvas.getGraphicsContext2D();
		graphics.setImageSmoothing(false);
		for (SpriteLayer canvasLayer : canvasLayers) {
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
	
	public void fillPixel(double x, double y, Color color) {
		if (!canvasLayerCurrentIsEditable()) {
			return;
		}
		SpriteLayer canvasLayer = getCanvasLayerCurrent();
		canvasLayer.setFill(color);
		canvasLayer.fillPixel(((int) x / scale), ((int) y / scale));
		updateImageView();
	}
	
	public void clearPixel(double x, double y) {
		if (!canvasLayerCurrentIsEditable()) {
			return;
		}
		SpriteLayer canvasLayer = getCanvasLayerCurrent();
		canvasLayer.clearPixel(((int) x / scale), ((int) y / scale));
		updateImageView();
	}
	
	public void fillRect(double x, double y, int width, int height, Color color) {
		fillRect((int) x / scale, (int) y / scale, width, height, color);
	}
	
	public void fillRect(int x, int y, int width, int height, Color color) {
		if (!canvasLayerCurrentIsEditable()) {
			return;
		}
		SpriteLayer canvasLayer = getCanvasLayerCurrent();
		canvasLayer.setFill(color);
		canvasLayer.fillRect(x, y, width, height);
		updateImageView();
	}
	
	public void clearRect(double x, double y, int width, int height) {
		clearRect((int) x / scale, (int) y / scale, width, height);
	}
	
	public void clearRect(int x, int y, int width, int height) {
		if (!canvasLayerCurrentIsEditable()) {
			return;
		}
		SpriteLayer canvasLayer = getCanvasLayerCurrent();
		canvasLayer.clearRect(x, y, width, height);
		updateImageView();
	}
	
	public boolean canvasLayerCurrentIsEditable() {
		SpriteLayer canvas = getCanvasLayerCurrent();
		return (canvas != null && canvas.isVisible());
	}
	
	public SpriteLayer addCanvasLayer() {
		return addCanvasLayer("Layer " + getCanvasLayerCount());
	}
	
	public SpriteLayer addCanvasLayer(String name) {
		SpriteLayer canvasLayer = new SpriteLayer(this, layersPane, name, width, height);
		canvasLayers.add(canvasLayer);
		updateImageView();
		return canvasLayer;
	}
	
	public SpriteLayer getCanvasLayer(int index) {
		return canvasLayers.get(index);
	}
	
	public HBox getHBoxOfCanvasLayer(SpriteLayer canvasLayer) {
		int index = canvasLayers.indexOf(canvasLayer);
		return canvasLayerHBoxes.get(index);
	}
	
	public void removeCanvasLayer(SpriteLayer canvasLayer) {
		if (canvasLayer != null && !canvasLayers.contains(canvasLayer)) {
			return;
		}
		canvasLayers.remove(canvasLayer);
		setCanvasLayerCurrent(null);
		updateImageView();
	}
	
	public SpriteLayer getCanvasLayerCurrent() {
		return canvasLayerCurrent;
	}
	
	public void setCanvasLayerCurrent(int canvasLayerIndex) {
		canvasLayerCurrent = canvasLayers.get(canvasLayerIndex);
	}
	
	public void setCanvasLayerCurrent(SpriteLayer canvasLayer) {
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

	public void setCanvasLayerVisible(Canvas canvas, boolean visible) {
		canvas.setVisible(visible);
		updateImageView();
	}

	public ArrayList<SpriteLayer> getCanvasLayers() {
		return canvasLayers;
	}
	
	public ToggleGroup getCanvasLayerToggleGroup() {
		return canvasLayerToggleGroup;
	}
}
