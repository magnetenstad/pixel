package pixel.sprite;

public class SpriteLayer {
	private Integer[][] canvas;
	private Sprite sprite;
	private SpriteLayerGui spriteLayerGui = new SpriteLayerGui(this);
	private String name = "untitled";
	private Boolean visible = true;
	private int width;
	private int height;
	
	public SpriteLayer(int width, int height) {
		this.width = width;
		this.height = height;
		this.canvas = new Integer[width][height];
		fillRect(0, 0, width, height, 0);
	}
	
	public SpriteLayer(Sprite sprite) {
		setSprite(sprite);
	}
	
	public void setSprite(Sprite sprite) {
		if (canvas == null) {
			width = sprite.getWidth();
			height = sprite.getHeight();
			this.canvas = new Integer[width][height];
			fillRect(0, 0, width, height, 0);
		}
		else if (width != sprite.getWidth() || height != sprite.getHeight()) {
			throw new IllegalArgumentException(""
					+ "SpriteParent does not match SpriteLayer size! "
					+ "SpriteParent[" + sprite.getWidth() + ", "+ sprite.getHeight() + "], "
					+ "SpriteLayer[" + width + ", " + height + "]");
		}
		this.sprite = sprite;
		updateGui();
	}
	
	public void updateGui() {
		if (sprite != null && spriteLayerGui != null) {
			spriteLayerGui.updateGui();
		}
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
	
	public SpriteLayerGui getSpriteLayerGui() {
		return spriteLayerGui;
	}
}















