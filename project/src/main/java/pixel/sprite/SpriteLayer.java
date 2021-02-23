package pixel.sprite;

import javafx.scene.control.CheckBox;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class SpriteLayer {
	private Color[][] canvas;
	private Sprite spriteParent;
	private Pane guiParent;
	private String name;
	private Boolean visible = true;
	private HBox gui;
	private int width;
	private int height;
	
	public SpriteLayer(Sprite spriteParent, Pane guiParent, String name, int width, int height) {
		this.spriteParent = spriteParent;
		this.guiParent = guiParent;
		this.name = name;
		this.width = width;
		this.height = height;
		
		this.canvas = new Color[width][height];
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
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void fillRect(int x0, int y0, int width, int height, Color color) {
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
	
	public void fillPixel(int x, int y, Color color) {
		if (!isPointInCanvas(x, y)) {
			return;
		}
		canvas[x][y] = color;
	}
	
	public void clearPixel(int x, int y) {
		if (!isPointInCanvas(x, y)) {
			return;
		}
		canvas[x][y] = Color.TRANSPARENT;
	}
	
	private boolean isPointInCanvas(int x, int y) {
		return (0 <= x && x < width && 0 <= y && y < height);
	}
	
	public Color getPixel(int x, int y) {
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
	
	public Pane getGuiParent() {
		return guiParent;
	}
}
