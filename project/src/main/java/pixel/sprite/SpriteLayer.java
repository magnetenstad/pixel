package pixel.sprite;

/**
 * An extension of canvas, with name and visibility.
 * @author Magne Tenstad
 *
 */
public class SpriteLayer extends Canvas {
	private String name = "untitled";
	private boolean visible = true;
	
	public SpriteLayer(Sprite sprite) {
		super(sprite.getWidth(), sprite.getHeight());
	}
	
	/**
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets name.
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 
	 * @return visible
	 */
	public boolean isVisible() {
		return visible;
	}
	
	/**
	 * Sets visibility.
	 * @param visible
	 */
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
}
