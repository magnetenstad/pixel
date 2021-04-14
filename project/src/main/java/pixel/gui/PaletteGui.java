package pixel.gui;

import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import pixel.cursorlist.CursorList;
import pixel.cursorlist.CursorListEvent;
import pixel.cursorlist.CursorListListener;
import pixel.palette.Palette;

public class PaletteGui implements CursorListListener {
	private ToggleGroup toggleGroup = new ToggleGroup();
	private final Palette palette;
	private Pane pane;
	
	public PaletteGui(Palette palette) {
		this.palette = palette;
		palette.addListener(this);
	}
	
	public Palette getPalette() {
		return palette;
	}
	
	public void setPane(Pane pane) {
		if (this.pane != null) {
			pane.getChildren().clear();
		}
		this.pane = pane;
		updateGui();
	}

	public void updateGui() {
		if (pane != null) {
			pane.getChildren().clear();
			if (palette != null)  {
				for (int i = 0; i < palette.size(); i++) {
					ToggleButton toggleButton = newColorButton(palette.get(i), i);
					if (i == palette.getCursor()) {
						toggleButton.setPrefWidth(64);
						toggleButton.setText("Selected");
					}
					pane.getChildren().add(toggleButton);
				}
			}
		}
	}
	
	private ToggleButton newColorButton(Color color, int index) {
		ToggleButton toggleButton = new ToggleButton();
		toggleButton.setToggleGroup(toggleGroup);
		toggleButton.setPrefSize(32, 32);
		toggleButton.setTooltip(new Tooltip(color.toString()));
		toggleButton.setStyle("-fx-border-color: #111111; -fx-border-width: 1px; -fx-background-color: #" + color.toString().replaceFirst("0x", ""));
		toggleButton.setOnAction(event -> {
			getPalette().setCursor(index);
		});
		return toggleButton;
	}

	@Override
	public void cursorListChanged(CursorList<?> cursorList, CursorListEvent event, Object element) {
		updateGui();
	}
}
