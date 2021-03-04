package pixel.sprite;

import java.util.ArrayList;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Sprite {
	private ArrayList<SpriteLayer> spriteLayers = new ArrayList<SpriteLayer>();
	private ImageView imageView = new ImageView();
	private ToggleGroup spriteLayerToggleGroup = new ToggleGroup();
	private WritableImage writableImage;
	private SpriteLayer spriteLayerCurrent;
	private Pane layerGuiPane;
	private int width;
	private int height;
	private int scale = 32;
	
	public Sprite(Pane layerGuiPane, int width, int height) {
		this.layerGuiPane = layerGuiPane;
		this.width = width;
		this.height = height;
		writableImage = new WritableImage(width * scale, height * scale);
		imageView.setImage(writableImage);
		setSpriteLayerCurrent(addSpriteLayer());
		updateImageView();
	}
	
	public void updateImageView() {
		Canvas combinedCanvas = new Canvas(getImageWidth(), getImageHeight());
		GraphicsContext graphics = combinedCanvas.getGraphicsContext2D();
		fillTransparentBackground(graphics);
		for (SpriteLayer spriteLayer : spriteLayers) {
			if (spriteLayer.isVisible()) {
				for (int x = 0; x < width; x++) {
					for (int y = 0; y < height; y++) {
						graphics.setFill(spriteLayer.getPixel(x, y));
						graphics.fillRect(x * scale, y * scale, scale, scale);
					}
				}
			}
		}
		combinedCanvas.snapshot(null, writableImage);
	}
	private void fillTransparentBackground(GraphicsContext graphics) {
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				graphics.setFill((x + y) % 2 == 0 ? Color.grayRgb(220) : Color.grayRgb(240));
				graphics.fillRect(x * scale, y * scale, scale, scale);
			}
		}
	}
	
	public void fillRect(int x, int y, int width, int height, Color color) {
		if (!isSpriteLayerCurrentEditable()) {
			return;
		}
		SpriteLayer spriteLayer = getSpriteLayerCurrent();
		spriteLayer.fillRect(x, y, width, height, color);
		updateImageView();
	}
	
	public void clearRect(int x, int y, int width, int height) {
		if (!isSpriteLayerCurrentEditable()) {
			return;
		}
		SpriteLayer spriteLayer = getSpriteLayerCurrent();
		spriteLayer.clearRect(x, y, width, height);
		updateImageView();
	}
	
	public boolean isSpriteLayerCurrentEditable() {
		SpriteLayer spriteLayer = getSpriteLayerCurrent();
		return (spriteLayer != null && spriteLayer.isVisible());
	}
	
	public SpriteLayer addSpriteLayer() {
		return addSpriteLayer("Layer " + getSpriteLayerCount());
	}
	
	public SpriteLayer addSpriteLayer(String name) {
		SpriteLayer spriteLayer = new SpriteLayer(this, layerGuiPane, name, width, height);
		spriteLayers.add(spriteLayer);
		updateImageView();
		return spriteLayer;
	}
	
	public SpriteLayer getSpriteLayer(int index) {
		return spriteLayers.get(index);
	}
	
	public void removeSpriteLayer(SpriteLayer spriteLayer) {
		if (spriteLayer != null && !spriteLayers.contains(spriteLayer)) {
			return;
		}
		spriteLayers.remove(spriteLayer);
		setSpriteLayerCurrent(null);
		updateImageView();
	}
	
	public SpriteLayer getSpriteLayerCurrent() {
		return spriteLayerCurrent;
	}
	
	public void setSpriteLayerCurrent(SpriteLayer spriteLayer) {
		if (spriteLayer != null && !spriteLayers.contains(spriteLayer)) {
			throw new IllegalArgumentException();
		}
		spriteLayerCurrent = spriteLayer;
	}
	
	public int getSpriteLayerCount() {
		return spriteLayers.size();
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

	public void setSpriteLayerVisible(SpriteLayer spriteLayer, boolean visible) {
		spriteLayer.setVisible(visible);
		updateImageView();
	}

	public ArrayList<SpriteLayer> getSpriteLayers() {
		return spriteLayers;
	}
	
	public ToggleGroup getSpriteLayerToggleGroup() {
		return spriteLayerToggleGroup;
	}
	
	public int getScale() {
		return scale;
	}
}
