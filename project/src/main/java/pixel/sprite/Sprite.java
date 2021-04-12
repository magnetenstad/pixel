package pixel.sprite;

import java.util.ArrayList;
import java.util.Iterator;

import javafx.scene.image.Image;
import pixel.SelectableList;
import pixel.SelectableListListener;
import pixel.gui.SpriteGui;
import pixel.palette.Palette;

/*
 * 
 */
public class Sprite extends SelectableList<SpriteLayer> {
	protected ArrayList<SpriteListener> listeners = new ArrayList<>();
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
		getSelected().fillRect(x, y, width, height, color);
		if (notify) {
			spriteChanged();
		}
	}
	
	public void clearRect(int x, int y, int width, int height) {
		clearRect(x, y, width, height, true);
	}
	public void clearRect(int x, int y, int width, int height, boolean notify) {
		if (!isSpriteLayerEditable()) {
			return;
		}
		getSelected().clearRect(x, y, width, height);
		spriteChanged();
	}
	
	public boolean isSpriteLayerEditable() {
		return (getSelected() != null && getSelected().isVisible());
	}
	
	public SpriteLayer addSpriteLayer() {
		SpriteLayer spriteLayer = new SpriteLayer(this);
		add(spriteLayer);
		return spriteLayer;
	}

	public void removeSpriteLayer() {
		SpriteLayer spriteLayer = getSelected();
		if (spriteLayer != null) {
			remove(spriteLayer);
		}
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
		if (getSelected() != null && index != 0) {
			SpriteLayer spriteLayerA = elements.get(index - 1);
			SpriteLayer spriteLayerB = elements.get(index);
			elements.set(index - 1, spriteLayerB);
			elements.set(index, spriteLayerA);
			select(spriteLayerB);
			spriteChanged();
		}
	}
	
	public void moveSpriteLayerDown() {
		if (getSelected() != null && index + 1 < elements.size()) {
			SpriteLayer spriteLayerA = elements.get(index);
			SpriteLayer spriteLayerB = elements.get(index + 1);
			elements.set(index, spriteLayerB);
			elements.set(index + 1, spriteLayerA);
			select(spriteLayerA);
			spriteChanged();
		}
	}
	
	public void spriteChanged() {
		for (SpriteListener listener : listeners) {
			listener.spriteChanged(this);
		}
	}
	
	public void addListener(SpriteListener listener) {
		listeners.add(listener);
	}
	public void removeListener(SpriteListener listener) {
		listeners.remove(listener);
	}
	
	public Image exportImage(Palette palette) {
		return SpriteGui.exportImage(this, palette);
	}
}




