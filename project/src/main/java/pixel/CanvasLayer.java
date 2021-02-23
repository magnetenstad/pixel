package pixel;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class CanvasLayer {
	private Color[][] canvas;
	private Pane guiParent;
	private HBox gui;
	private Sprite spriteParent;
	private String name;
	private Boolean visible = true;
	private Color fill;
	private int width;
	private int height;
	
	public CanvasLayer(Sprite spriteParent, Pane guiParent, String name, int width, int height) {
		this.spriteParent = spriteParent;
		this.guiParent = guiParent;
		this.name = name;
		this.width = width;
		this.height = height;
		this.canvas = new Color[width][height];
		clearRect(0, 0, width, height);
		
		gui = newGui();
		addGuiToParent();
	}
	
	public HBox newGui() {
		gui = new HBox();
		
		Button layerButton = new Button(name);
		gui.getChildren().add(layerButton);
		layerButton.setOnAction(event -> {
			spriteParent.setCanvasLayerCurrent(this);
		});
		
		CheckBox layerCheckBox = new CheckBox();
		gui.getChildren().add(layerCheckBox);
		layerCheckBox.setSelected(true);
		layerCheckBox.setOnAction(event -> {
			setVisible(layerCheckBox.isSelected());
			spriteParent.updateVisibleCanvas();
		});
		return gui;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void setFill(Color fill) {
		this.fill = fill;
	}
	public void fillRect(int x0, int y0, int width, int height) {
		for (int x = x0; x < x0 + width; x++) {
			for (int y = y0; x < y0 + height; y++) {
				fillPixel(x, y);
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
	
	public void fillPixel(int x, int y) {
		if (!isPointInCanvas(x, y)) {
			return;
		}
		canvas[x][y] = fill;
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
