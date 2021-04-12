package pixel.sprite;

import pixel.SelectableListListener;

public interface SpriteListener extends SelectableListListener {
	
	public void spriteChanged(Sprite sprite);
	
}
