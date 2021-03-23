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
import pixel.Palette;
import pixel.PixelApp;

public class Sprite {
	private ArrayList<SpriteLayer> spriteLayers = new ArrayList<>();
	private SpriteLayer spriteLayer;
	private String name = "untitled";
	private String path;
	private SpriteGui spriteGui;
	private int width;
	private int height;
	private final static int scale = 32;
	
	public Sprite(int width, int height) {
		this.width = width;
		this.height = height;
		spriteGui = new SpriteGui(width, height, this);
		updateImageView();
	}
	
	public void updateImageView() {
		spriteGui.updateImageView();
	}
	
	public SpriteGui getSpriteGui() {
		return spriteGui;
	}
	
	public void fillRect(int x, int y, int width, int height, int color) {
		if (!isSpriteLayerEditable()) {
			return;
		}
		SpriteLayer spriteLayer = getSpriteLayer();
		spriteLayer.fillRect(x, y, width, height, color);
	}
	
	public void clearRect(int x, int y, int width, int height) {
		if (!isSpriteLayerEditable()) {
			return;
		}
		SpriteLayer spriteLayer = getSpriteLayer();
		spriteLayer.clearRect(x, y, width, height);
	}
	
	public boolean isSpriteLayerEditable() {
		SpriteLayer spriteLayer = getSpriteLayer();
		return (spriteLayer != null && spriteLayer.isVisible());
	}
	
	public SpriteLayer addSpriteLayer() {
		SpriteLayer spriteLayer = new SpriteLayer(this);
		spriteLayers.add(spriteLayer);
		if (spriteLayers.size() == 1) {
			selectSpriteLayer(spriteLayer);
			spriteLayer.getSpriteLayerGui().selectLayerButton();
		}
		updateImageView();
		return spriteLayer;
	}
	
	public SpriteLayer addSpriteLayer(SpriteLayer spriteLayer) {
		spriteLayer.setSprite(this);
		spriteLayers.add(spriteLayer);
		if (spriteLayers.size() == 1) {
			selectSpriteLayer(spriteLayer);
			spriteLayer.getSpriteLayerGui().selectLayerButton();
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
			selectSpriteLayer(index);
		}
		updateImageView();
	}
	
	public SpriteLayer getSpriteLayer() {
		return spriteLayer;
	}
	
	public void selectSpriteLayer(int index) {
		if (!(0 <= index && index < getSpriteLayerCount())) {
			throw new IllegalArgumentException();
		}
		spriteLayer = spriteLayers.get(index);
	}
	
	public void selectSpriteLayer(SpriteLayer spriteLayer) {
		if (spriteLayer != null && !spriteLayers.contains(spriteLayer)) {
			throw new IllegalArgumentException();
		}
		this.spriteLayer = spriteLayer;
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
	
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	
	public ArrayList<SpriteLayer> getSpriteLayers() {
		return spriteLayers;
	}
	
	public double getScale() {
		return (double) scale;
	}

	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
}



























