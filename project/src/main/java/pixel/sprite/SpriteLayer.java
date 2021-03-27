package pixel.sprite;

public class SpriteLayer {
	private Integer[][] canvas;
	private final Sprite sprite;
	private SpriteLayerGui spriteLayerGui;
	private String name;
	private Boolean visible = true;
	private int width;
	private int height;
	
	public SpriteLayer(Sprite sprite) {
		if (sprite == null) {
			throw new NullPointerException("Cannot create SpriteLayer from null.");
		}
		this.sprite = sprite;
		this.width = sprite.getWidth();
		this.height = sprite.getHeight();
		this.name = "Layer " + sprite.getSpriteLayerCount();
		this.canvas = new Integer[width][height];
		fillRect(0, 0, width, height, 0);
	}
	
	public void setName(String name) {
		this.name = name;
		updateGui();
	}
	
	public String getName() {
		return name;
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
		canvas[x][y] = 0;
	}
	
	public boolean isPointInCanvas(int x, int y) {
		return (0 <= x && x < width && 0 <= y && y < height);
	}
	
	public int getPixel(int x, int y) {
		return canvas[x][y];
	}
	
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	public boolean isVisible() {
		return visible;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public Sprite getSprite() {
		return sprite;
	}

	public void updateGui() {
		if (spriteLayerGui != null) {
			spriteLayerGui.update();
		}
	}
	
	public void buildGui() {
		if (spriteLayerGui == null) {
			spriteLayerGui = new SpriteLayerGui(this);
		}
		updateGui();
	}
}















