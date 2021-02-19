package pixel;

import javafx.geometry.Pos;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import pixel.ext.ZoomableScrollPane;

public class SpriteTab extends Tab {
	private Sprite sprite;
	private StackPane pane;
	
	public SpriteTab(TabPane parent, String name) {
		this(parent, name, new Sprite(32, 32));
	}
	
	public SpriteTab(TabPane parent, String name, Sprite sprite) {
		parent.getTabs().add(this);
		pane = new StackPane();
		setContent(new ZoomableScrollPane(pane));
		setText(name);
		setSprite(sprite);
	}
	
	public Sprite getSprite() {
		return sprite;
	}

	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
		pane.setPrefHeight(sprite.getWidth()*2);
		pane.setPrefWidth(sprite.getHeight()*2);
		pane.getChildren().clear();
		pane.getChildren().add(sprite.getImageView());
		StackPane.setAlignment(sprite.getImageView(), Pos.CENTER);
		
		setOnSelectionChanged(event -> {
			if (sprite.getCanvasLayerCount() > 0) {
				Pane guiParent = sprite.getCanvasLayer(0).getGuiParent();
				guiParent.getChildren().clear();
			}
			if (isSelected()) {
				for (CanvasLayer canvasLayer : sprite.getCanvasLayers()) {
					canvasLayer.addGuiToParent();
				}
			}
		});
	}
}
