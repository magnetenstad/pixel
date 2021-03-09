package pixel.sprite;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import pixel.PixelApp;

public class Sprite {
	private ArrayList<SpriteLayer> spriteLayers = new ArrayList<SpriteLayer>();
	private SnapshotParameters snapshotParameters = new SnapshotParameters();
	private ToggleGroup spriteLayerToggleGroup = new ToggleGroup();
	private ImageView imageView = new ImageView();
	private SpriteLayer spriteLayerCurrent;
	private WritableImage writableImage;
	private String name = "untitled";
	private String path;
	private int width;
	private int height;
	private final static int scale = 32;
	
	public Sprite(int width, int height) {
		this.width = width;
		this.height = height;
		snapshotParameters.setFill(Color.TRANSPARENT);
		writableImage = new WritableImage(width * scale, height * scale);
		imageView.setImage(writableImage);
		imageView.setOnMousePressed(event -> {
			PixelApp.getController().getToolbar().useToolSelected(this, event);
		});
		imageView.setOnMouseDragged(imageView.getOnMousePressed());
		imageView.setOnMouseReleased(imageView.getOnMousePressed());
		updateImageView();
	}
	public void updateImageView() {
		Canvas combinedCanvas = new Canvas(getImageWidth(), getImageHeight());
		GraphicsContext graphics = combinedCanvas.getGraphicsContext2D();
		fillTransparentBackground(graphics);
		drawSpriteLayersToGraphics(graphics, scale);
		combinedCanvas.snapshot(snapshotParameters, writableImage);
	}
	private void fillTransparentBackground(GraphicsContext graphics) {
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				graphics.setFill((x + y) % 2 == 0 ? Color.grayRgb(220) : Color.grayRgb(240));
				graphics.fillRect(x * scale, y * scale, scale, scale);
			}
		}
	}
	public void drawSpriteLayersToGraphics(GraphicsContext graphics, double scale) {
		ArrayList<Color> colors = PixelApp.getController().getPalette().getColors();
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
	}
	public WritableImage exportImage() {
		WritableImage writableImage = new WritableImage(getWidth(), getHeight());
		Canvas canvas = new Canvas(getWidth(), getHeight());
		GraphicsContext graphics = canvas.getGraphicsContext2D();
		drawSpriteLayersToGraphics(graphics, 1);
		canvas.snapshot(snapshotParameters, writableImage);
		return writableImage;
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
	public SpriteLayer addSpriteLayer(SpriteLayer spriteLayer) {
		spriteLayer.setSpriteParent(this);
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
	public void setName(String name) {
		this.name = name;
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
	public static JSONObject serialise(Sprite sprite) {
		JSONObject json = new JSONObject();
		json.put("name", sprite.getName());
		json.put("width", sprite.getWidth());
		json.put("height", sprite.getHeight());
		JSONArray spriteLayers = new JSONArray();
		for (SpriteLayer spriteLayer : sprite.spriteLayers) {
			spriteLayers.put(SpriteLayer.serialize(spriteLayer));
		}
		json.put("data", spriteLayers);
		
		return json;
	}
	
	public static Sprite deserialise(JSONObject json) {
		int width = json.getInt("width");
		int height = json.getInt("height");
		Sprite sprite = new Sprite(width, height);
		sprite.setName(json.getString("name"));
		JSONArray spriteLayers = json.getJSONArray("data");
		for (Object spriteLayerJSONObject : spriteLayers) {
			SpriteLayer spriteLayer = SpriteLayer.deserialize((JSONObject) spriteLayerJSONObject);
			sprite.addSpriteLayer(spriteLayer);
		}
		return sprite;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
}



























