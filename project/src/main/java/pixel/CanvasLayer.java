package pixel;

import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class CanvasLayer extends Canvas {
	private Pane guiParent;
	private HBox gui;
	private Sprite spriteParent;
	private String name;
	
	public CanvasLayer(Sprite spriteParent, Pane guiParent, String name, int width, int height) {
		super(width, width);
		
		this.spriteParent = spriteParent;
		this.guiParent = guiParent;
		this.name = name;
		
		gui = newGui();
		addGuiToParent();
		
		getGraphicsContext2D().setImageSmoothing(false);
	}
	
	public HBox newGui() {
		gui = new HBox();
		
		Button layerButton = new Button(name);
		gui.getChildren().add(layerButton);
		layerButton.setOnAction(event -> {
			spriteParent.setCanvasLayerCurrent(this);
		});
		
		CheckBox layerCheckBox = new CheckBox();
		gui.getChildren().add(layerCheckBox);
		layerCheckBox.setSelected(true);
		layerCheckBox.setOnAction(event -> {
			setVisible(layerCheckBox.isSelected());
			spriteParent.updateVisibleCanvas();
			System.out.println(layerCheckBox.isSelected());
		});
		return gui;
	}
	
	public void removeGuiFromParent() {
		guiParent.getChildren().remove(gui);
	}
	
	public void addGuiToParent() {
		guiParent.getChildren().add(gui);
	}
	
	public Pane getGuiParent() {
		return guiParent;
	}
}
