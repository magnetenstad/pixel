package pixel;

import java.awt.image.BufferedImage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import org.json.JSONObject;

import javafx.embed.swing.SwingFXUtils;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import pixel.sprite.Sprite;
import pixel.sprite.SpriteTab;

public class Directory {
	public void openSprite() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Pixel Files", "*.pixel"));
		File fileSelected = fileChooser.showOpenDialog(PixelApp.getWindow());
		if (fileSelected != null) {
			Sprite sprite = loadSpriteFromPath(fileSelected.getAbsolutePath());
			new SpriteTab(sprite);
		}
	}
	public Sprite loadSpriteFromPath(String path) {
		try {
			String text = new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
			JSONObject json = new JSONObject(text);
			Sprite sprite = Sprite.deserialise(json);
			return sprite;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public void saveSprite(Sprite sprite) {
		checkNotNull(sprite);
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Pixel Files", "*.pixel"));
		File fileSelected = fileChooser.showSaveDialog(PixelApp.getWindow());
		if (fileSelected != null) {
			saveSpriteToPath(sprite, fileSelected.getAbsolutePath());
			sprite.setPath(fileSelected.getAbsolutePath());
		}
	}
	public static void saveSpriteToPath(Sprite sprite, String path) {
		try {
			PrintWriter file = new PrintWriter(path);
			file.print(Sprite.serialise(sprite));
			file.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	public void exportSpriteToPng(Sprite sprite) {
		checkNotNull(sprite);
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new ExtensionFilter("PNG Files", "*.png"));
		File fileSelected = fileChooser.showSaveDialog(PixelApp.getWindow());
		if (fileSelected != null) {
			BufferedImage bufferedImage = new BufferedImage(sprite.getWidth(), sprite.getHeight(), BufferedImage.TYPE_INT_ARGB);
			SwingFXUtils.fromFXImage(sprite.exportImage(), bufferedImage);
			try {
				ImageIO.write(bufferedImage, "png", fileSelected);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}
	private void checkNotNull(Object obj) {
		if (obj == null) {
			throw new NullPointerException("Sprite cannot be null!");
		}
	}
}






















