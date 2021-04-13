package pixel.sprite;

import pixel.CursorListListener;

public interface SpriteListener extends CursorListListener {
	
	public void spriteChanged(Sprite sprite);
	
}
