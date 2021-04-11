package pixel.sprite;

import javafx.geometry.Pos;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.StackPane;
import pixel.PixelApp;
import pixel.ext.ZoomableScrollPane;

public class SpriteTab extends Tab {
	private static TabPane tabPane;
	private SpriteGui spriteGui;
	private StackPane pane = new StackPane();
	
	public SpriteTab() {
		this(null);
	}
	public SpriteTab(SpriteGui spriteGui) {
		tabPane.getTabs().add(this);
		tabPane.getSelectionModel().select(this);
		setContent(new ZoomableScrollPane(pane));
		setOnSelectionChanged(event -> {
			PixelApp.getController().updateSpriteGui();
		});
		if (spriteGui == null) {
			Sprite sprite = new Sprite(32, 32);
			setSpriteGui(new SpriteGui(sprite));
			sprite.addSpriteLayer();
		}
	}
	public void setSpriteGui(SpriteGui spriteGui) {
		this.spriteGui = spriteGui;
		pane.setPrefWidth(spriteGui.getImageWidth()*2);
		pane.setPrefHeight(spriteGui.getImageHeight()*2);
		pane.getChildren().clear();
		pane.getChildren().add(spriteGui.getImageView());
		StackPane.setAlignment(spriteGui.getImageView(), Pos.CENTER);
		setText(spriteGui.getSprite().getName());
	}
	public SpriteGui getSpriteGui() {
		return spriteGui;
	}
	public static void setTabPane(TabPane tabPane) {
		SpriteTab.tabPane = tabPane;
	}
}
