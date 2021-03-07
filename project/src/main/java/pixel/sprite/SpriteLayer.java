package pixel.sprite;

import javafx.scene.control.CheckBox;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import pixel.PixelApp;

public class SpriteLayer {
	private Integer[][] canvas;
	private Sprite spriteParent;
	private Pane guiParent = PixelApp.getController().getLayersVBox();
	private String name;
	private Boolean visible = true;
	private HBox gui;
	private int width;
	private int height;
	
	public SpriteLayer() {}
	public SpriteLayer(Sprite spriteParent, String name) {
		init(name, spriteParent);
	}
	public void init(String name, Sprite spriteParent) {
		this.name = name;
		this.spriteParent = spriteParent;
		this.width = spriteParent.getWidth();
		this.height = spriteParent.getHeight();
		
		this.canvas = new Integer[width][height];
		clearRect(0, 0, width, height);
		
		gui = newLayerGui();
		addGuiToParent();
	}
	
	public HBox newLayerGui() {
		HBox gui = new HBox();
		
		ToggleButton layerButton = new ToggleButton(name);
		layerButton.setToggleGroup(spriteParent.getSpriteLayerToggleGroup());
		gui.getChildren().add(layerButton);
		layerButton.setOnAction(event -> {
			spriteParent.setSpriteLayerCurrent(this);
		});
		
		CheckBox layerCheckBox = new CheckBox();
		gui.getChildren().add(layerCheckBox);
		layerCheckBox.setSelected(true);
		layerCheckBox.setOnAction(event -> {
			setVisible(layerCheckBox.isSelected());
		});
		
		return gui;
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
	private boolean isPointInCanvas(int x, int y) {
		return (0 <= x && x < width && 0 <= y && y < height);
	}
	public int getPixel(int x, int y) {
		return canvas[x][y];
	}
	public void setVisible(boolean visible) {
		this.visible = visible;
		spriteParent.updateImageView();
	}
	public boolean isVisible() {
		return visible;
	}
	public void removeGuiFromParent() {
		guiParent.getChildren().remove(gui);
	}
	public void addGuiToParent() {
		guiParent.getChildren().add(gui);
	}
	public static String serialize() {
		String string = "";
		return string;
	}
	public static SpriteLayer deserialize() {
		SpriteLayer spriteLayer = new SpriteLayer();
		return spriteLayer;
	}
}















