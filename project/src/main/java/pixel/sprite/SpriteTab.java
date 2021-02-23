package pixel.sprite;

import javafx.geometry.Pos;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import pixel.ext.ZoomableScrollPane;

public class SpriteTab extends Tab {
	private Sprite sprite;
	private StackPane pane;
	
	public SpriteTab(TabPane parent, Pane layersPane, String name) {
		this(parent, name, new Sprite(layersPane, 32, 32));
	}
	
	public SpriteTab(TabPane parent, String name, Sprite sprite) {
		parent.getTabs().add(this);
		pane = new StackPane();
		setContent(new ZoomableScrollPane(pane));
		setText(name);
		setSprite(sprite);
		updateSpriteLayerGui();
		
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
		
		setOnSelectionChanged(event -> {
			updateSpriteLayerGui();
		});
	}
	
	public void updateSpriteLayerGui() {
		Pane guiParent = sprite.getSpriteLayer(0).getGuiParent();
		guiParent.getChildren().clear();
		
		for (SpriteLayer canvasLayer : getSpriteCurrent().getSpriteLayers()) {
			canvasLayer.addGuiToParent();
		}
	}
	
	public Sprite getSpriteCurrent() {
		return ((SpriteTab) getTabPane().getSelectionModel().getSelectedItem()).getSprite();
	}
}
