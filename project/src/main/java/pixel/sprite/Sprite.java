package pixel.sprite;

import java.util.ArrayList;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import pixel.PixelApp;

public class Sprite {
	private ArrayList<SpriteLayer> spriteLayers = new ArrayList<SpriteLayer>();
	private ImageView imageView = new ImageView();
	private ToggleGroup spriteLayerToggleGroup = new ToggleGroup();
	private WritableImage writableImage;
	private SpriteLayer spriteLayerCurrent;
	private String name = "untitled";
	private int width;
	private int height;
	private final static int scale = 32;
	
	public Sprite(int width, int height) {
		this.width = width;
		this.height = height;
		writableImage = new WritableImage(width * scale, height * scale);
		imageView.setImage(writableImage);
		imageView.setOnMousePressed(event -> {
			PixelApp.getController().getToolbar().useToolSelected(this, event);
		});
		imageView.setOnMouseDragged(imageView.getOnMousePressed());
		imageView.setOnMouseReleased(imageView.getOnMousePressed());
		setSpriteLayerCurrent(addSpriteLayer());
		updateImageView();
	}
	public void updateImageView() {
		Canvas combinedCanvas = new Canvas(getImageWidth(), getImageHeight());
		GraphicsContext graphics = combinedCanvas.getGraphicsContext2D();
		ArrayList<Color> colors = PixelApp.getController().getPalette().getColors();
		fillTransparentBackground(graphics);
		for (SpriteLayer spriteLayer : spriteLayers) {
			if (spriteLayer.isVisible()) {
				for (int x = 0; x < width; x++) {
					for (int y = 0; y < height; y++) {
						graphics.setFill(colors.get(spriteLayer.getPixel(x, y)));
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
	public void fillRect(int x, int y, int width, int height, int color) {
		if (!isSpriteLayerCurrentEditable()) {
			return;
		}
		SpriteLayer spriteLayer = getSpriteLayerCurrent();
		spriteLayer.fillRect(x, y, width, height, color);
	}
	public void clearRect(int x, int y, int width, int height) {
		if (!isSpriteLayerCurrentEditable()) {
			return;
		}
		SpriteLayer spriteLayer = getSpriteLayerCurrent();
		spriteLayer.clearRect(x, y, width, height);
	}
	public boolean isSpriteLayerCurrentEditable() {
		SpriteLayer spriteLayer = getSpriteLayerCurrent();
		return (spriteLayer != null && spriteLayer.isVisible());
	}
	public SpriteLayer addSpriteLayer() {
		SpriteLayer spriteLayer = new SpriteLayer(this);
		spriteLayers.add(spriteLayer);
		if (spriteLayers.size() == 1) {
			setSpriteLayerCurrent(spriteLayer);
			spriteLayer.selectLayerButton();
		}
		updateImageView();
		return spriteLayer;
	}
	public void removeSpriteLayer(SpriteLayer spriteLayer) {
		if (!spriteLayers.contains(spriteLayer)) {
			return;
		}
		int index = spriteLayers.indexOf(spriteLayer) - 1;
		spriteLayers.remove(spriteLayer);
		if (0 <= index) {
			setSpriteLayerCurrent(index);
		}
		updateImageView();
	}
	public SpriteLayer getSpriteLayerCurrent() {
		return spriteLayerCurrent;
	}
	public void setSpriteLayerCurrent(int index) {
		if (!(0 <= index && index < getSpriteLayerCount())) {
			throw new IllegalArgumentException();
		}
		spriteLayerCurrent = spriteLayers.get(index);
	}
	public void setSpriteLayerCurrent(SpriteLayer spriteLayer) {
		if (spriteLayer != null && !spriteLayers.contains(spriteLayer)) {
			throw new IllegalArgumentException();
		}
		spriteLayerCurrent = spriteLayer;
	}
	public String getName() {
		return name;
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
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	public ImageView getImageView() {
		return imageView;
	}
	public ArrayList<SpriteLayer> getSpriteLayers() {
		return spriteLayers;
	}
	public ToggleGroup getSpriteLayerToggleGroup() {
		return spriteLayerToggleGroup;
	}
	public double getScale() {
		return (double) scale;
	}
	public static String serialise() {
		String string = "";
		return string;
	}
	public static Sprite deserialise() {
		Sprite sprite = new Sprite(32, 32);
		return sprite;
	}
}
