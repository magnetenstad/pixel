package pixel;

import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class PaletteGui implements PaletteListener {
	ToggleGroup toggleGroup = new ToggleGroup();
	Palette palette;
	Pane pane;
	
	public Palette getPalette() {
		return palette;
	}
	
	public void setPalette(Palette palette) {
		if (palette != null) {
			palette.removeListener(this);
		}
		this.palette = palette;
		palette.addListener(this);
		updateGui();
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
					ToggleButton toggleButton = newColorButton(palette.getColor(i), i);
					if (i == palette.getIndex()) {
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
			getPalette().setIndex(index);
		});
		return toggleButton;
	}

	@Override
	public void paletteChanged(Palette palette) {
		updateGui();
	}
}
