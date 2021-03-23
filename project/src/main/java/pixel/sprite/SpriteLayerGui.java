package pixel.sprite;

import javafx.scene.control.CheckBox;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import pixel.PixelApp;

public class SpriteLayerGui {
	private HBox gui;
	private ToggleButton layerButton;
	private static Pane guiParent;
	private SpriteLayer spriteLayer;
	
	public SpriteLayerGui(SpriteLayer spriteLayer) {
		this.spriteLayer = spriteLayer;
	}
	public void removeGuiFromParent() {
		if (gui != null) {
			guiParent.getChildren().remove(gui);
		}
	}
	public static void setPane(Pane pane) {
		SpriteLayerGui.guiParent = pane;
	}
	public void updateGui() {
		if (spriteLayer.getSprite() == null) {
			return;
		}
		removeGuiFromParent();
		gui = newLayerGui();
		guiParent.getChildren().add(gui);
	}
	
	public HBox newLayerGui() {
		if (spriteLayer.getSprite() == null) {
			throw new IllegalStateException("Cannot create a layerGui without a spriteParent!");
		}
		HBox gui = new HBox();
		layerButton = new ToggleButton(spriteLayer.getName());
		layerButton.setToggleGroup(spriteLayer.getSprite().getSpriteGui().getSpriteLayerToggleGroup());
		gui.getChildren().add(layerButton);
		layerButton.setOnAction(event -> {
			spriteLayer.getSprite().selectSpriteLayer(spriteLayer);
		});
		
		CheckBox layerCheckBox = new CheckBox();
		gui.getChildren().add(layerCheckBox);
		layerCheckBox.setSelected(true);
		layerCheckBox.setOnAction(event -> {
			spriteLayer.setVisible(layerCheckBox.isSelected());
			spriteLayer.getSprite().updateImageView();
		});
		
		return gui;
	}

	public void selectLayerButton() {
		layerButton.setSelected(true);
	}
}
