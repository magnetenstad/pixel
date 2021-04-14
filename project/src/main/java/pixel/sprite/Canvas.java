package pixel.sprite;

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
	
	public void fillRect(int x0, int y0, int width, int height, int color) {
		for (int x = x0; x < x0 + width; x++) {
			for (int y = y0; y < y0 + height; y++) {
				fillPixel(x, y, color);
			}
		}
	}
	
	public void clearRect(int x0, int y0, int width, int height) {
		for (int x = x0; x < x0 + width; x++) {
			for (int y = y0; y < y0 + height; y++) {
				clearPixel(x, y);
			}
		}
	}
	
	public void fillPixel(int x, int y, int color) {
		if (!isPointInCanvas(x, y)) {
			return;
		}
		canvas[x][y] = color;
	}
	
	public void clearPixel(int x, int y) {
		if (!isPointInCanvas(x, y)) {
			return;
		}
		canvas[x][y] = -1;
	}
	
	public boolean isPointInCanvas(int x, int y) {
		return (0 <= x && x < width && 0 <= y && y < height);
	}
	
	public int getPixel(int x, int y) {
		return canvas[x][y];
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
}















