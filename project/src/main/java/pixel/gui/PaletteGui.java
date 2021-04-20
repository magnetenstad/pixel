package pixel.gui;

import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import pixel.cursorlist.CursorList;
import pixel.cursorlist.CursorListEvent;
import pixel.cursorlist.CursorListListener;
import pixel.palette.Palette;

/**
 * A gui to manage multiple palettes.
 * Should be listening to its palettes.
 * @author Magne Tenstad
 */
public class PaletteGui extends CursorList<Palette> implements CursorListListener {
	private ToggleGroup toggleGroup = new ToggleGroup();
	private Pane pane;
	private int columnCount = 6;
	
	/**
	 * Overrides the CursorList add method to also listen to the given palette.
	 */
	@Override
	public void add(Palette palette) {
		super.add(palette);
		palette.addListener(this);
	}
	
	/**
	 * Overrides the CursorList remove method to also stop listening to the given palette.
	 */
	@Override
	public void remove(Palette palette) {
		super.remove(palette);
		palette.removeListener(this);
	}
	
	/**
	 * Overrides the CursorList setCursor method to also rebuild.
	 */
	@Override
	public void setCursor(int index) {
		super.setCursor(index);
		rebuild();
	}
	
	/**
	 * Sets the pane where the gui is built.
	 * @param pane
	 */
	public void setPane(Pane pane) {
		if (this.pane != null) {
			pane.getChildren().clear();
		}
		this.pane = pane;
		rebuild();
	}
	
	/**
	 * Rebuilds the gui, if pane is not null.
	 */
	public void rebuild() {
		if (pane != null) {
			pane.getChildren().clear();
			
			Palette palette = getSelected();
			if (palette != null)  {
				ColorButton selectedColor = new ColorButton(palette.getSelected(), palette.getCursor());
				selectedColor.setPrefSize(64, 64);
				pane.getChildren().add(selectedColor);
				
				GridPane gridPane = new GridPane();
				pane.getChildren().add(gridPane);
				
				for (int i = 0; i < palette.size(); i++) {
					ColorButton colorButton = new ColorButton(palette.get(i), i);
					if (i == palette.getCursor()) {
						colorButton.setText("S");
					}
					gridPane.add(colorButton, i % columnCount, (int) (i / columnCount));
				}
			}
		}
	}
	
	/**
	 * An extension of ToggleButton, to represent a selectable color in a palette.
	 * @author Magne Tenstad
	 */
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
	
	/**
	 * Listens to the selected palette, and notifies listeners (e.g. SpriteGui, ToolbarGui) about changes.
	 */
	@Override
	public void cursorListChanged(CursorList<?> cursorList, CursorListEvent event, Object element) {
		if (cursorList == getSelected()) {
			rebuild();
			notifyListeners(CursorListEvent.CursorChanged, (Palette) element);
		}
	}
}
