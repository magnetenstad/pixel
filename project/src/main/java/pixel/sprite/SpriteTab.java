package pixel.sprite;

import javafx.geometry.Pos;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import pixel.PixelApp;
import pixel.ext.ZoomableScrollPane;

public class SpriteTab extends Tab {
	private Sprite sprite;
	private StackPane pane;
	
	public SpriteTab() {
		this(null);
	}
	public SpriteTab(Sprite sprite) {
		PixelApp.getController().getTabPane().getTabs().add(this);
		PixelApp.getController().getTabPane().getSelectionModel().select(this);
		pane = new StackPane();
		setContent(new ZoomableScrollPane(pane));
		setOnSelectionChanged(event -> {
			updateSpriteLayerGui();
		});
		if (sprite == null) {
			sprite = new Sprite(32, 32);
			sprite.addSpriteLayer();
		}
		setSprite(sprite);
	}
	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
		pane.setPrefWidth(sprite.getImageWidth()*2);
		pane.setPrefHeight(sprite.getImageHeight()*2);
		pane.getChildren().clear();
		pane.getChildren().add(sprite.getImageView());
		StackPane.setAlignment(sprite.getImageView(), Pos.CENTER);
		setText(sprite.getName());
		updateSpriteLayerGui();
	}
	public Sprite getSprite() {
		return sprite;
	}
	public void updateSpriteLayerGui() {
		Pane layersVBox = PixelApp.getController().getLayersVBox();
		layersVBox.getChildren().clear();
		Sprite sprite = getSpriteCurrent();
		for (SpriteLayer canvasLayer : sprite.getSpriteLayers()) {
			canvasLayer.addGuiToParent();
		}
		if (sprite.getSpriteLayerCurrent() != null) {
			sprite.getSpriteLayerCurrent().selectLayerButton();
		}
	}
	private Sprite getSpriteCurrent() {
		return ((SpriteTab) getTabPane().getSelectionModel().getSelectedItem()).getSprite();
	}
}
