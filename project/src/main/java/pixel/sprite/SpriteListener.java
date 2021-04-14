package pixel.sprite;

import pixel.cursorlist.CursorListListener;

public interface SpriteListener extends CursorListListener {
	
	public void spriteChanged(Sprite sprite);
	
}
