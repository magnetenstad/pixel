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

/*
 * 
 */
public class Sprite {
	private ArrayList<SpriteLayer> spriteLayers = new ArrayList<>();
	private int spriteLayerIndex = -1;
	private String name = "untitled";
	private String path;
	private SpriteGui spriteGui;
	private int width;
	private int height;
	
	public Sprite(int width, int height) {
		this.width = width;
		this.height = height;
		updateGui();
	}
	
	public void updateGui() {
		if (spriteGui != null) {
			spriteGui.update();
		}
	}
	
	public SpriteGui getSpriteGui() {
		return spriteGui;
	}
	
	public SpriteGui buildGui() {
		if (spriteGui == null) {
			spriteGui = new SpriteGui(this);
		}
		updateGui();
		return spriteGui;
	}
	
	public void fillRect(int x, int y, int width, int height, int color) {
		if (!isSpriteLayerEditable()) {
			return;
		}
		getSpriteLayer().fillRect(x, y, width, height, color);
	}
	
	public void clearRect(int x, int y, int width, int height) {
		if (!isSpriteLayerEditable()) {
			return;
		}
		getSpriteLayer().clearRect(x, y, width, height);
	}
	
	public boolean isSpriteLayerEditable() {
		return (getSpriteLayer() != null && getSpriteLayer().isVisible());
	}
	
	public SpriteLayer addSpriteLayerWithGui() {
		SpriteLayer spriteLayer = new SpriteLayer(this);
		spriteLayer.buildGui();
		return addSpriteLayer(spriteLayer);
	}
	
	public SpriteLayer addSpriteLayer() {
		SpriteLayer spriteLayer = new SpriteLayer(this);
		return addSpriteLayer(spriteLayer);
	}
	
	public SpriteLayer addSpriteLayer(SpriteLayer spriteLayer) {
		if (spriteLayer.getSprite() != this) {
			throw new IllegalStateException("Cannot add a SpriteLayer from a different Sprite!");
		}
		spriteLayers.add(spriteLayer);
		offsetSelectedSpriteLayer(0);
		return spriteLayer;
	}
	
	public void removeSpriteLayer(SpriteLayer spriteLayer) {
		if (!spriteLayers.contains(spriteLayer)) {
			return;
		}
		spriteLayers.remove(spriteLayer);
		offsetSelectedSpriteLayer(-1);
		updateGui();
	}
	
	public SpriteLayer getSpriteLayer() {
		if (0 <= spriteLayerIndex && spriteLayerIndex < spriteLayers.size()) {
			return spriteLayers.get(spriteLayerIndex);
		}
		return null;
	}
	
	public void offsetSelectedSpriteLayer(int offset) {
		if (spriteLayers.size() == 0) {
			spriteLayerIndex = -1;
		}
		else {
			spriteLayerIndex = Math.min(Math.max(0, spriteLayerIndex + offset), spriteLayers.size());
		}
		SpriteLayerGui.updateAll();
	}
	
	public void selectSpriteLayer(SpriteLayer spriteLayer) {
		if (spriteLayer != null && !spriteLayers.contains(spriteLayer)) {
			throw new IllegalArgumentException();
		}
		spriteLayerIndex = spriteLayers.indexOf(spriteLayer);
		SpriteLayerGui.updateAll();
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
		if (spriteGui == null) {
			return 1;
		}
		return spriteGui.getScale();
	}

	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
	public WritableImage exportImage() {
		return SpriteGui.exportImage(this);
	}
	
	public void moveSpriteLayerUp() {
		if (getSpriteLayer() != null && spriteLayerIndex != 0) {
			SpriteLayer spriteLayerA = spriteLayers.get(spriteLayerIndex - 1);
			SpriteLayer spriteLayerB = spriteLayers.get(spriteLayerIndex);
			spriteLayers.set(spriteLayerIndex - 1, spriteLayerB);
			spriteLayers.set(spriteLayerIndex, spriteLayerA);
			selectSpriteLayer(spriteLayerB);
			updateGui();
		}
	}
	
	public void moveSpriteLayerDown() {
		if (getSpriteLayer() != null && spriteLayerIndex + 1 < spriteLayers.size()) {
			SpriteLayer spriteLayerA = spriteLayers.get(spriteLayerIndex);
			SpriteLayer spriteLayerB = spriteLayers.get(spriteLayerIndex + 1);
			spriteLayers.set(spriteLayerIndex, spriteLayerB);
			spriteLayers.set(spriteLayerIndex + 1, spriteLayerA);
			selectSpriteLayer(spriteLayerA);
			updateGui();
		}
	}
}



























