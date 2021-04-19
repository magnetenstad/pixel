package pixel.sprite;

/**
 * An integer matrix designed for painting rectangles.
 * @author Magne Tenstad
 */
public class Canvas {
	private Integer[][] canvas;
	private int width;
	private int height;
	
	public Canvas(int width, int height) {
		this.width = width;
		this.height = height;
		this.canvas = new Integer[width][height];
		fillRect(0, 0, width, height, -1);
	}
	
	/**
	 * Fills the given rect with the given integer.
	 * @param x0
	 * @param y0
	 * @param width
	 * @param height
	 * @param color
	 */
	public void fillRect(int x0, int y0, int width, int height, int color) {
		for (int x = x0; x < x0 + width; x++) {
			for (int y = y0; y < y0 + height; y++) {
				fillPixel(x, y, color);
			}
		}
	}
	
	/**
	 * Clears the given rect with -1.
	 * @param x0
	 * @param y0
	 * @param width
	 * @param height
	 */
	public void clearRect(int x0, int y0, int width, int height) {
		for (int x = x0; x < x0 + width; x++) {
			for (int y = y0; y < y0 + height; y++) {
				clearPixel(x, y);
			}
		}
	}
	
	/**
	 * Sets the given pixel to the given integer.
	 * @param x
	 * @param y
	 * @param color
	 */
	public void fillPixel(int x, int y, int color) {
		if (!isPointInCanvas(x, y)) {
			return;
		}
		canvas[x][y] = color;
	}
	
	/**
	 * Sets the given pixel to -1.
	 * @param x
	 * @param y
	 */
	public void clearPixel(int x, int y) {
		if (!isPointInCanvas(x, y)) {
			return;
		}
		canvas[x][y] = -1;
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @return Whether or not the position is within the canvas size.
	 */
	public boolean isPointInCanvas(int x, int y) {
		return (0 <= x && x < width && 0 <= y && y < height);
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @return The integer at the given position.
	 */
	public int getPixel(int x, int y) {
		return canvas[x][y];
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
}















