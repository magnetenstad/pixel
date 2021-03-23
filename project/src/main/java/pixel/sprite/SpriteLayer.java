package pixel.sprite;

import org.json.JSONObject;

import javafx.scene.control.CheckBox;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import pixel.PixelApp;

public class SpriteLayer {
	private Integer[][] canvas;
	private Sprite sprite;
	private Pane guiParent = PixelApp.getController().getLayersVBox();
	private String name = "untitled";
	private Boolean visible = true;
	private HBox gui;
	private ToggleButton layerButton;
	private int width;
	private int height;
	public SpriteLayer(int width, int height) {
		this.width = width;
		this.height = height;
		this.canvas = new Integer[width][height];
		fillRect(0, 0, width, height, 0);
	}
	public SpriteLayer(Sprite spriteParent) {
		setSpriteParent(spriteParent);
	}
	public void setSpriteParent(Sprite sprite) {
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
		addGuiToParent();
	}
	public void setName(String name) {
		this.name = name;
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
		sprite.updateImageView();
	}
	public boolean isVisible() {
		return visible;
	}
	public void removeGuiFromParent() {
		if (gui != null) {
			guiParent.getChildren().remove(gui);
		}
	}
	public void addGuiToParent() {
		if (sprite == null) {
			return;
		}
		removeGuiFromParent();
		gui = newLayerGui();
		guiParent.getChildren().add(gui);
	}
	public HBox newLayerGui() {
		if (sprite == null) {
			throw new IllegalStateException("Cannot create a layerGui without a spriteParent!");
		}
		HBox gui = new HBox();
		layerButton = new ToggleButton(name);
		layerButton.setToggleGroup(sprite.getSpriteGui().getSpriteLayerToggleGroup());
		gui.getChildren().add(layerButton);
		layerButton.setOnAction(event -> {
			sprite.setSpriteLayerCurrent(this);
		});
		
		CheckBox layerCheckBox = new CheckBox();
		gui.getChildren().add(layerCheckBox);
		layerCheckBox.setSelected(true);
		layerCheckBox.setOnAction(event -> {
			setVisible(layerCheckBox.isSelected());
		});
		
		return gui;
	}
	
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	public void selectLayerButton() {
		layerButton.setSelected(true);
	}
}















