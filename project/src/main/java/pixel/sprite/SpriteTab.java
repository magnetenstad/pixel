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
		this(new Sprite(32, 32));
	}
	public SpriteTab(Sprite sprite) {
		PixelApp.getController().getTabPane().getTabs().add(this);
		pane = new StackPane();
		setContent(new ZoomableScrollPane(pane));
		setSprite(sprite);
		setText(sprite.getName());
		updateSpriteLayerGui();
		setOnSelectionChanged(event -> {
			updateSpriteLayerGui();
		});
	}
	public Sprite getSprite() {
		return sprite;
	}
	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
		pane.setPrefWidth(sprite.getImageWidth()*2);
		pane.setPrefHeight(sprite.getImageHeight()*2);
		pane.getChildren().clear();
		pane.getChildren().add(sprite.getImageView());
		StackPane.setAlignment(sprite.getImageView(), Pos.CENTER);
	}
	public void updateSpriteLayerGui() {
		Pane layersVBox = PixelApp.getController().getLayersVBox();
		layersVBox.getChildren().clear();
		
		for (SpriteLayer canvasLayer : getSpriteCurrent().getSpriteLayers()) {
			canvasLayer.addGuiToParent();
		}
	}
	private Sprite getSpriteCurrent() {
		return ((SpriteTab) getTabPane().getSelectionModel().getSelectedItem()).getSprite();
	}
}
