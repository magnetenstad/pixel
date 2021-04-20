package pixel.sprite;

import javafx.scene.image.Image;
import pixel.cursorlist.CursorList;
import pixel.cursorlist.CursorListEvent;
import pixel.gui.SpriteGui;
import pixel.palette.Palette;

/**
 * An extension of CursorList<SpriteLayer>,
 * with methods to draw to the selected SpriteLayer.
 * @author Magne Tenstad
 *
 */
public class Sprite extends CursorList<SpriteLayer> {
	private String path = "New sprite";
	private int totalLayerCount;
	private int width;
	private int height;
	
	public Sprite(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	/**
	 * Fills the given rect of the selected SpriteLayer with the given integer,
	 * if the spriteLayer is editable.
	 * Notifies listeners.
	 * @param x0
	 * @param y0
	 * @param width
	 * @param height
	 * @param color
	 */
	public void fillRect(int x, int y, int width, int height, int color) {
		fillRect(x, y, width, height, color, true);
	}
	
	/**
	 * Fills the given rect of the selected SpriteLayer with the given integer,
	 * if the spriteLayer is editable.
	 * Notifies listeners if notify is true.
	 * @param x0
	 * @param y0
	 * @param width
	 * @param height
	 * @param color
	 */
	public void fillRect(int x, int y, int width, int height, int color, boolean notify) {
		checkSpriteLayerEditable();
		getSelected().fillRect(x, y, width, height, color);
		if (notify) {
			notifyListeners(CursorListEvent.ElementChanged, getSelected());
		}
	}
	
	/**
	 * Clears the given rect of the selected SpriteLayer with -1,
	 * if the spriteLayer is editable.
	 * Notifies listeners.
	 * @param x0
	 * @param y0
	 * @param width
	 * @param height
	 * @param color
	 */
	public void clearRect(int x, int y, int width, int height) {
		clearRect(x, y, width, height, true);
	}
	
	/**
	 * Clears the given rect of the selected SpriteLayer with -1,
	 * if the spriteLayer is editable.
	 * Notifies listeners if notify is true.
	 * @param x0
	 * @param y0
	 * @param width
	 * @param height
	 * @param color
	 */
	public void clearRect(int x, int y, int width, int height, boolean notify) {
		checkSpriteLayerEditable();
		getSelected().clearRect(x, y, width, height);
		if (notify) {
			notifyListeners(CursorListEvent.ElementChanged, getSelected());
		}
	}
	
	/**
	 * 
	 * @return Whether or not the selected spriteLayer is editable.
	 */
	public boolean isEditable() {
		return getSelected() != null && getSelected().isVisible();
	}
	
	/**
	 * 
	 * Throws an IllegalStateException if selected is null or not visible.
	 */
	public void checkSpriteLayerEditable() throws IllegalStateException {
		if (!isEditable()) {
			throw new IllegalStateException("Selected is null or not visible!");
		}
	}
	
	/**
	 * 
	 * @return width
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * 
	 * @return height
	 */
	public int getHeight() {
		return height;
	}
	
	/**
	 * Gets the last path this sprite was saved to.
	 * Is also used for naming.
	 * @return path
	 */
	public String getPath() {
		return path;
	}
	
	/**
	 * Sets path.
	 * @param path
	 */
	public void setPath(String path) {
		this.path = path;
		notifyListeners(CursorListEvent.ElementChanged);
	}
	
	/**
	 * Overrides the add method to also set name of spriteLayer.
	 */
	@Override
	public void add(SpriteLayer spriteLayer) {
		totalLayerCount++;
		spriteLayer.setName("Layer " + totalLayerCount);
		super.add(spriteLayer);
	}
	
	/**
	 * Exports the sprite to an Image, for saving to png.
	 * @param palette
	 * @return Image
	 */
	public Image exportImage(Palette palette) {
		return SpriteGui.exportImage(this, palette);
	}
}




