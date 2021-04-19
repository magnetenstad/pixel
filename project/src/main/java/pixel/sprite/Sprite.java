package pixel.sprite;

import java.util.ArrayList;
import java.util.Iterator;

import javafx.scene.image.Image;
import pixel.cursorlist.CursorList;
import pixel.cursorlist.CursorListEvent;
import pixel.cursorlist.CursorListListener;
import pixel.gui.SpriteGui;
import pixel.palette.Palette;

/*
 * 
 */
public class Sprite extends CursorList<SpriteLayer> {
	protected ArrayList<CursorListListener> listeners = new ArrayList<>();
	private String path = "new sprite";
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
			notifyListeners(CursorListEvent.ElementChanged, getSelected());
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
		notifyListeners(CursorListEvent.ElementChanged, getSelected());
	}
	
	public boolean isSpriteLayerEditable() {
		return (getSelected() != null && getSelected().isVisible());
	}
	
	public void addSpriteLayer() {
		add(new SpriteLayer(this));
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
		notifyListeners(CursorListEvent.ElementChanged);
	}
	
	public void moveSpriteLayerUp() {
		if (getSelected() != null && cursor != 0) {
			SpriteLayer spriteLayerA = elements.get(cursor - 1);
			SpriteLayer spriteLayerB = elements.get(cursor);
			elements.set(cursor - 1, spriteLayerB);
			elements.set(cursor, spriteLayerA);
			select(spriteLayerB);
			notifyListeners(CursorListEvent.ElementsReordered);
		}
	}
	
	public void moveSpriteLayerDown() {
		if (getSelected() != null && cursor + 1 < elements.size()) {
			SpriteLayer spriteLayerA = elements.get(cursor);
			SpriteLayer spriteLayerB = elements.get(cursor + 1);
			elements.set(cursor, spriteLayerB);
			elements.set(cursor + 1, spriteLayerA);
			select(spriteLayerA);
			notifyListeners(CursorListEvent.ElementsReordered);
		}
	}
	
	public void addListener(CursorListListener listener) {
		super.addListener(listener);
		listeners.add(listener);
	}
	
	public void removeListener(CursorListListener listener) {
		super.removeListener(listener);
		listeners.remove(listener);
	}
	
	public Image exportImage(Palette palette) {
		return SpriteGui.exportImage(this, palette);
	}
}




