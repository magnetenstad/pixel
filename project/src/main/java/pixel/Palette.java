package pixel;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.scene.Node;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Palette {
	private Pane pane = PixelApp.getController().getPaletteVBox();
	private ArrayList<Color> colors = new ArrayList<Color>();
	private ToggleGroup toggleGroup = new ToggleGroup();
	private int indexCurrent = 0;
	
	public Palette() {
		paletteFromHexFile("src/main/resources/endesga-16.hex");
		updateGui();
	}
	
	public void addColor(Color color) {
		colors.add(color);
		addColorGui(color);
	}
	
	public void addColorGui(Color color) {
		ToggleButton toggleButton = new ToggleButton();
		pane.getChildren().add(toggleButton);
		styleColorButton(toggleButton);
		toggleButton.setPrefSize(32, 32);
		toggleButton.setToggleGroup(toggleGroup);
		toggleButton.setOnAction(event -> {
			setIndexCurrent(pane.getChildren().indexOf(toggleButton));
			PixelApp.getController().getToolbar().updateToolColor(indexCurrent);
		});
	}
	public void updateGui() {
		for (Node button : pane.getChildren()) {
			((ToggleButton) button).setPrefWidth(button == pane.getChildren().get(indexCurrent) ? 48 : 32);
		}
	}
	public void setIndexCurrent(int indexCurrent) {
		this.indexCurrent = indexCurrent;
		updateGui();
	}
	public void setColorCurrent(Color color) {
		this.colors.set(indexCurrent, color);
		styleColorButton((ToggleButton) pane.getChildren().get(indexCurrent));
	}
	public static Color Color(String hex) {
		hex = hex.replaceFirst("#", "");
		double r = Integer.parseInt(hex.substring(0, 2), 16) / 255.0;
		double g = Integer.parseInt(hex.substring(2, 4), 16) / 255.0;
		double b = Integer.parseInt(hex.substring(4, 6), 16) / 255.0;
		return Color.color(r, g, b);
	}
	public void paletteFromHexFile(String path) {
		addColor(Color.TRANSPARENT);
		try {
			Scanner in = new Scanner(new FileReader(path));
			while(in.hasNext()){
				String line = in.nextLine();
				addColor(Color(line));
			}
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	public void styleColorButton(ToggleButton toggleButton) {
		int index = pane.getChildren().indexOf(toggleButton);
		Color color = colors.get(index);
		toggleButton.setTooltip(new Tooltip(color.toString()));
		toggleButton.setStyle("-fx-border-color: #111111; -fx-border-width: 1px; -fx-background-color: #" + color.toString().replaceFirst("0x", ""));
	}
	public int getIndexCurrent() {
		return indexCurrent;
	}
	public ArrayList<Color> getColors() {
		return colors;
	}
}







