package pixel.sprite;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import pixel.Palette;
import pixel.PixelApp;

public class SpriteGui {
	private static final SnapshotParameters snapshotParameters = new SnapshotParameters();
	private ToggleGroup spriteLayerToggleGroup = new ToggleGroup();
	private ImageView imageView = new ImageView();
	private WritableImage writableImage;
	private final Sprite sprite;
	private int height;
	private int width;
	private final static int scale = 32;
	
	public SpriteGui(Sprite sprite) {
		if (sprite == null) {
			throw new NullPointerException("Cannot create SpriteGui from null.");
		}
		this.sprite = sprite;
		this.width = sprite.getWidth();
		this.height = sprite.getHeight();
		
		snapshotParameters.setFill(Color.TRANSPARENT);
		writableImage = new WritableImage(width * scale, height * scale);
		imageView.setImage(writableImage);
		imageView.setOnMousePressed(event -> {
			PixelApp.getController().getToolbar().useToolSelected(sprite, event);
		});
		imageView.setOnMouseDragged(imageView.getOnMousePressed());
		imageView.setOnMouseReleased(imageView.getOnMousePressed());
	}
	
	public void update() {
		Canvas combinedCanvas = new Canvas(getImageWidth(), getImageHeight());
		GraphicsContext graphics = combinedCanvas.getGraphicsContext2D();
		fillTransparentBackground(graphics);
		drawSpriteLayersToGraphics(sprite, graphics, scale);
		combinedCanvas.snapshot(snapshotParameters, writableImage);
	}
	
	private void fillTransparentBackground(GraphicsContext graphics) {
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				graphics.setFill((x + y) % 2 == 0 ? Color.grayRgb(220) : Color.grayRgb(240));
				graphics.fillRect(x * scale, y * scale, scale, scale);
			}
		}
	}
	
	public static void drawSpriteLayersToGraphics(Sprite sprite, GraphicsContext graphics, double scale) {
		Palette palette = PixelApp.getController().getPalette();
		for (SpriteLayer spriteLayer : sprite) {
			if (spriteLayer.isVisible()) {
				for (int x = 0; x < sprite.getWidth(); x++) {
					for (int y = 0; y < sprite.getHeight(); y++) {
						graphics.setFill(palette.getColor(spriteLayer.getPixel(x, y)));
						graphics.fillRect(x * scale, y * scale, scale, scale);
					}
				}
			}
		}
	}
	
	public static WritableImage exportImage(Sprite sprite) {
		WritableImage writableImage = new WritableImage(sprite.getWidth(), sprite.getHeight());
		Canvas canvas = new Canvas(sprite.getWidth(), sprite.getHeight());
		GraphicsContext graphics = canvas.getGraphicsContext2D();
		drawSpriteLayersToGraphics(sprite, graphics, 1);
		canvas.snapshot(snapshotParameters, writableImage);
		return writableImage;
	}
	
	public ImageView getImageView() {
		return imageView;
	}
	
	public ToggleGroup getSpriteLayerToggleGroup() {
		return spriteLayerToggleGroup;
	}
	
	public double getImageHeight() {
		return writableImage.getHeight();
	}
	
	public double getImageWidth() {
		return writableImage.getWidth();
	}
	
	public void moveSpriteLayerUp() {
		
	}
	public void moveSpriteLayerDown() {
		
	}

	public Sprite getSprite() {
		return sprite;
	}
	
	public double getScale() {
		return (double) scale;
	}
}
