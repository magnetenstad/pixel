package pixel.sprite;

/**
 * An extension of canvas, with name and visibility.
 * @author Magne Tenstad
 *
 */
public class SpriteLayer extends Canvas {
	private final static String newLine = "_\n";
	private String name = "New layer";
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
	
	@Override
	public String toString() {
		String string = "";
		for (int y = 0; y < width; y++) {
			for (int x = 0; x < height; x++) {
				int color = getPixel(x, y);
				String hex = color == -1 ? "ff" : Integer.toHexString(color); // -1 (no color) is saved as ff
				if (hex.length() < 2) {
					hex = "0" + hex;
				}
				string += hex;
			}
			string += newLine;
		}
		return string;
	}
	
	public void fromString(String string) throws NumberFormatException {
		int x = 0;
		int y = 0;
		for (int i = 0; i < string.length() - 1; i += 2) {
			String c = string.substring(i, i + 2);
			if (!c.equals(newLine)) {
				fillPixel(x, y, c == "ff" ? -1 : Integer.parseInt(c, 16));
				x++;
			}
			else {
				y++;
				x = 0;
			}
		}
	}
}
