package pixel.sprite;

import javafx.scene.control.CheckBox;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import pixel.PixelApp;

public class SpriteLayerGui {
	private HBox gui;
	private ToggleButton layerButton;
	private static Pane pane;
	private final SpriteLayer spriteLayer;
	
	public SpriteLayerGui(SpriteLayer spriteLayer) {
		if (spriteLayer == null) {
			throw new NullPointerException("Cannot create SpriteLayerGui from null.");
		}
		this.spriteLayer = spriteLayer;
	}
	
	public static void setPane(Pane pane) {
		SpriteLayerGui.pane = pane;
	}
	
	public void update() {
		if (pane != null && spriteLayer.getSprite().getSpriteGui() != null) {
			if (gui != null) {
				pane.getChildren().remove(gui);
			}
			gui = build();
			pane.getChildren().add(gui);
		}
	}
	
	public HBox build() {
		if (spriteLayer.getSprite().getSpriteGui() == null) {
			throw new IllegalStateException("Cannot build SpriteLayerGui when Sprite does not have SpriteGui!");
		}
		HBox gui = new HBox();
		layerButton = new ToggleButton(spriteLayer.getName());
		layerButton.setToggleGroup(spriteLayer.getSprite().getSpriteGui().getSpriteLayerToggleGroup());
		gui.getChildren().add(layerButton);
		layerButton.setOnAction(event -> {
			spriteLayer.getSprite().selectSpriteLayer(spriteLayer);
		});
		layerButton.setSelected(spriteLayer.getSprite().getSpriteLayer() == spriteLayer);
		CheckBox layerCheckBox = new CheckBox();
		gui.getChildren().add(layerCheckBox);
		layerCheckBox.setSelected(true);
		layerCheckBox.setOnAction(event -> {
			spriteLayer.setVisible(layerCheckBox.isSelected());
			spriteLayer.getSprite().updateGui();
		});
		return gui;
	}
	
	public static void updateAll() {
		if (pane != null) {
			pane.getChildren().clear();
			Sprite sprite = PixelApp.getController().getSprite();
			if (sprite != null) {
				for (SpriteLayer spriteLayer : sprite) {
					spriteLayer.updateGui();
				}
			}
		}
	}
}
