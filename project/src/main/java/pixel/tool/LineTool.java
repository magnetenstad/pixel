package pixel.tool;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import pixel.cursorlist.CursorListEvent;
import pixel.gui.SpriteGui;
import pixel.sprite.Sprite;

public class LineTool extends Tool {
	private ToolInputEvent startEvent;
	
	public LineTool() {
		super("Line");
	}
	
	@Override
	public void use(Sprite sprite, ToolInputEvent event) {
		if (event.getButton() == MouseButton.PRIMARY) {
			if (event.isPrimaryButtonDown()) {
				if (startEvent == null) {
					startEvent = event;
				}
			}
			else {
				ToolInputEvent endEvent = event;
				
				double dx = endEvent.getX() - startEvent.getX();
				double dy = endEvent.getY() - startEvent.getY();
				double angle = (0 <= dx) ? Math.atan(dy / dx) : Math.PI + Math.atan(dy / dx);
				double length = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
				double di = length / Math.max(Math.abs(dx), Math.abs(dy));
				
				for (int i = 0; i <= length; i += di) {
					double x = startEvent.getX() + i * Math.cos(angle) + 0.5;
					double y = startEvent.getY() + i * Math.sin(angle) + 0.5;
					sprite.fillRect((int) (x - size / 2), (int) (y - size / 2), size, size, color, false);
				}
				sprite.notifyListeners(CursorListEvent.ElementChanged, sprite.getSelected());
				startEvent = null;
			}
		}
	}
}
