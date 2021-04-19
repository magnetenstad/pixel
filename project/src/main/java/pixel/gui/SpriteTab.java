package pixel.gui;

import javafx.geometry.Pos;
import javafx.scene.control.Tab;
import javafx.scene.layout.StackPane;
import pixel.cursorlist.CursorList;
import pixel.cursorlist.CursorListEvent;
import pixel.cursorlist.CursorListListener;
import pixel.ext.ZoomableScrollPane;
import pixel.sprite.Sprite;

/**
 * An extension of Tab to hold a SpriteGui.
 * Should listen to its spriteGui's sprite.
 * @author Magne Tenstad
 */
public class SpriteTab extends Tab implements CursorListListener {
	private final SpriteGui spriteGui;
	
	public SpriteTab(SpriteGui spriteGui) {
		this.spriteGui = spriteGui;
		spriteGui.getSprite().addListener(this);
		setText(spriteGui.getSprite().getPath());

		StackPane pane = new StackPane();
		pane.setPrefWidth(spriteGui.getImageWidth()*2);
		pane.setPrefHeight(spriteGui.getImageHeight()*2);
		pane.getChildren().clear();
		pane.getChildren().add(spriteGui.getImageView());
		StackPane.setAlignment(spriteGui.getImageView(), Pos.CENTER);
		
		setContent(new ZoomableScrollPane(pane));
		setOnSelectionChanged(event -> {
			spriteGui.rebuildSprite();
		});
	}
	
	/**
	 * 
	 * @return spriteGui
	 */
	public SpriteGui getSpriteGui() {
		return spriteGui;
	}
	
	/**
	 * Listens to its spriteGui's sprite, and updates text.
	 */
	@Override
	public void cursorListChanged(CursorList<?> cursorList, CursorListEvent event, Object element) {
		if (cursorList instanceof Sprite) {
			setText(((Sprite) cursorList).getPath());
		}
	}
}
