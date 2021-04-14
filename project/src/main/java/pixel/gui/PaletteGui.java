package pixel.gui;

import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import pixel.cursorlist.CursorList;
import pixel.cursorlist.CursorListEvent;
import pixel.cursorlist.CursorListListener;
import pixel.palette.Palette;

public class PaletteGui extends CursorList<Palette> implements CursorListListener {
	private ToggleGroup toggleGroup = new ToggleGroup();
	private Pane pane;
	
	@Override
	public void add(Palette palette) {
		super.add(palette);
		palette.addListener(this);
	}
	
	@Override
	public void remove(Palette palette) {
		super.remove(palette);
		palette.removeListener(this);
	}
	
	public void setPane(Pane pane) {
		if (this.pane != null) {
			pane.getChildren().clear();
		}
		this.pane = pane;
		rebuild();
	}
	
	public void rebuild() {
		if (pane != null) {
			pane.getChildren().clear();
			
			Button back = new Button("<");
			back.setOnAction(event -> {
				int index = (getCursor() - 1) % size();
				index += index < 0 ? size() : 0;			
				select(get(index));
				rebuild();
			});
			Button forward = new Button(">");
			forward.setOnAction(event -> {
				int index = (getCursor() + 1) % size();			
				select(get(index));
				rebuild();
			});
			
			pane.getChildren().add(back);
			pane.getChildren().add(forward);
			
			Palette palette = getSelected();
			if (palette != null)  {
				for (int i = 0; i < palette.size(); i++) {
					ColorButton colorButton = new ColorButton(palette.get(i), i);
					if (i == palette.getCursor()) {
						colorButton.setPrefWidth(64);
						colorButton.setText("Selected");
					}
					pane.getChildren().add(colorButton);
				}
			}
		}
	}
	
	private class ColorButton extends ToggleButton {
		public ColorButton(Color color, int index) {
			setToggleGroup(toggleGroup);
			setPrefSize(32, 32);
			setTooltip(new Tooltip(color.toString()));
			setStyle("-fx-border-color: #111111; -fx-border-width: 1px; -fx-background-color: #" + color.toString().replaceFirst("0x", ""));
			setOnAction(event -> {
				getSelected().select(color);
				rebuild();
			});
		}
	}
	
	/*
	 * Listens to the selected palette, and notifies listeners (SpriteGui, ToolbarGui) about changes.
	 */
	@Override
	public void cursorListChanged(CursorList<?> cursorList, CursorListEvent event, Object element) {
		if (cursorList == getSelected()) {
			rebuild();
			notifyListeners(CursorListEvent.CursorChanged, (Palette) element);
		}
	}
}
