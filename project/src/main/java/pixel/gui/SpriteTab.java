package pixel.gui;

import javafx.geometry.Pos;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.StackPane;
import pixel.PixelApp;
import pixel.PixelController;
import pixel.ext.ZoomableScrollPane;
import pixel.sprite.Sprite;

public class SpriteTab extends Tab {
	private final SpriteGui spriteGui;
	
	public SpriteTab(SpriteGui spriteGui) {
		this.spriteGui = spriteGui;
		setText(spriteGui.getSprite().getPath());

		StackPane pane = new StackPane();
		pane.setPrefWidth(spriteGui.getImageWidth()*2);
		pane.setPrefHeight(spriteGui.getImageHeight()*2);
		pane.getChildren().clear();
		pane.getChildren().add(spriteGui.getImageView());
		StackPane.setAlignment(spriteGui.getImageView(), Pos.CENTER);
		
		setContent(new ZoomableScrollPane(pane));
		setOnSelectionChanged(event -> {
			spriteGui.update();
		});
	}
	
	public SpriteGui getSpriteGui() {
		return spriteGui;
	}
}
