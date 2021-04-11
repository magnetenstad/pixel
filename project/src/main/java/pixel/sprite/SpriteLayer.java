package pixel.sprite;

public class SpriteLayer extends Canvas {
	private String name = "untitled";
	private boolean visible = true;
	
	public SpriteLayer(Sprite sprite) {
		super(sprite.getWidth(), sprite.getHeight());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
}
