package pixel.sprite;

import java.util.ArrayList;
import java.util.Iterator;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import pixel.gui.SpriteGui;
import pixel.palette.Palette;

/*
 * 
 */
public class Sprite implements Iterable<SpriteLayer> {
	private ArrayList<SpriteListener> listeners = new ArrayList<>();
	private ArrayList<SpriteLayer> spriteLayers = new ArrayList<>();
	private int spriteLayerIndex = -1;
	private String name = "untitled";
	private String path;
	private int width;
	private int height;
	
	public Sprite(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	public void fillRect(int x, int y, int width, int height, int color) {
		fillRect(x, y, width, height, color, true);
	}
	public void fillRect(int x, int y, int width, int height, int color, boolean notify) {
		if (!isSpriteLayerEditable()) {
			return;
		}
		getSpriteLayer().fillRect(x, y, width, height, color);
		if (notify) {
			notifyListeners();
		}
	}
	
	public void clearRect(int x, int y, int width, int height) {
		clearRect(x, y, width, height, true);
	}
	public void clearRect(int x, int y, int width, int height, boolean notify) {
		if (!isSpriteLayerEditable()) {
			return;
		}
		getSpriteLayer().clearRect(x, y, width, height);
		notifyListeners();
	}
	
	public boolean isSpriteLayerEditable() {
		return (getSpriteLayer() != null && getSpriteLayer().isVisible());
	}
	
	public SpriteLayer addSpriteLayer() {
		SpriteLayer spriteLayer = new SpriteLayer(this);
		return addSpriteLayer(spriteLayer);
	}
	
	public SpriteLayer addSpriteLayer(SpriteLayer spriteLayer) {
		spriteLayers.add(spriteLayer);
		offsetSpriteLayerIndex(0);
		notifyListeners();
		return spriteLayer;
	}
	public void removeSpriteLayer() {
		SpriteLayer spriteLayer = getSpriteLayer();
		if (spriteLayer != null) {
			removeSpriteLayer(spriteLayer);
		}
	}
	public void removeSpriteLayer(SpriteLayer spriteLayer) {
		if (!spriteLayers.contains(spriteLayer)) {
			return;
		}
		spriteLayers.remove(spriteLayer);
		offsetSpriteLayerIndex(-1);
		notifyListeners();
	}
	
	public SpriteLayer getSpriteLayer() {
		if (0 <= spriteLayerIndex && spriteLayerIndex < spriteLayers.size()) {
			return spriteLayers.get(spriteLayerIndex);
		}
		return null;
	}
	
	public void offsetSpriteLayerIndex(int offset) {
		if (spriteLayers.size() == 0) {
			spriteLayerIndex = -1;
		}
		else {
			spriteLayerIndex = Math.min(Math.max(0, spriteLayerIndex + offset), spriteLayers.size());
		}
		notifyListeners();
	}
	
	public void selectSpriteLayer(SpriteLayer spriteLayer) {
		if (spriteLayer != null && !spriteLayers.contains(spriteLayer)) {
			throw new IllegalArgumentException();
		}
		spriteLayerIndex = spriteLayers.indexOf(spriteLayer);
		notifyListeners();
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
	public void moveSpriteLayerUp() {
		if (getSpriteLayer() != null && spriteLayerIndex != 0) {
			SpriteLayer spriteLayerA = spriteLayers.get(spriteLayerIndex - 1);
			SpriteLayer spriteLayerB = spriteLayers.get(spriteLayerIndex);
			spriteLayers.set(spriteLayerIndex - 1, spriteLayerB);
			spriteLayers.set(spriteLayerIndex, spriteLayerA);
			selectSpriteLayer(spriteLayerB);
			notifyListeners();
		}
	}
	
	public void moveSpriteLayerDown() {
		if (getSpriteLayer() != null && spriteLayerIndex + 1 < spriteLayers.size()) {
			SpriteLayer spriteLayerA = spriteLayers.get(spriteLayerIndex);
			SpriteLayer spriteLayerB = spriteLayers.get(spriteLayerIndex + 1);
			spriteLayers.set(spriteLayerIndex, spriteLayerB);
			spriteLayers.set(spriteLayerIndex + 1, spriteLayerA);
			selectSpriteLayer(spriteLayerA);
			notifyListeners();
		}
	}

	@Override
	public Iterator<SpriteLayer> iterator() {
		return spriteLayers.iterator();
	}

	public void addListener(SpriteListener listener) {
		listeners.add(listener);
	}
	public void removeListener(SpriteListener listener) {
		listeners.remove(listener);
	}
	public void notifyListeners() {
		for (SpriteListener listener : listeners) {
			listener.spriteChanged(this);
		}
	}

	public Image exportImage(Palette palette) {
		// TODO Auto-generated method stub
		return null;
	}
}
























