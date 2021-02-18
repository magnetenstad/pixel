package pixel;

import javafx.geometry.Pos;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.StackPane;

public class SpriteTab extends Tab {
	private Sprite sprite;
	private StackPane pane;
	
	public SpriteTab(TabPane parent, String name) {
		parent.getTabs().add(this);
		pane = new StackPane();
		setText(name);
		setContent(new ZoomableScrollPane(pane));
		setSprite(new Sprite());
	}
	
	public Sprite getSprite() {
		return sprite;
	}

	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
		pane.setPrefHeight(sprite.getWidth()*2);
		pane.setPrefWidth(sprite.getHeight()*2);
		pane.getChildren().clear();
		pane.getChildren().add(sprite);
		StackPane.setAlignment(sprite, Pos.CENTER);
	}
}
