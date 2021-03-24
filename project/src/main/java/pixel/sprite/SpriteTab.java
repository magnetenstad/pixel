package pixel.sprite;

import javafx.geometry.Pos;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import pixel.PixelApp;
import pixel.ext.ZoomableScrollPane;

public class SpriteTab extends Tab {
	private SpriteGui spriteGui;
	private StackPane pane = new StackPane();
	
	public SpriteTab() {
		this(null);
	}	
	public SpriteTab(SpriteGui spriteGui) {
		PixelApp.getController().getTabPane().getTabs().add(this);
		PixelApp.getController().getTabPane().getSelectionModel().select(this);
		setContent(new ZoomableScrollPane(pane));
		setOnSelectionChanged(event -> {
			SpriteLayerGui.updateAll();
		});
		if (spriteGui == null) {
			Sprite sprite = new Sprite(32, 32);
			spriteGui = sprite.buildGui();
			sprite.addSpriteLayerWithGui();
		}
		setSpriteGui(spriteGui);
	}
	public void setSpriteGui(SpriteGui spriteGui) {
		this.spriteGui = spriteGui;
		pane.setPrefWidth(spriteGui.getImageWidth()*2);
		pane.setPrefHeight(spriteGui.getImageHeight()*2);
		pane.getChildren().clear();
		pane.getChildren().add(spriteGui.getImageView());
		StackPane.setAlignment(spriteGui.getImageView(), Pos.CENTER);
		setText(spriteGui.getSprite().getName());
		SpriteLayerGui.updateAll();
	}
	public Sprite getSprite() {
		return spriteGui.getSprite();
	}
}
