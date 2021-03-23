package pixel.file;

import java.awt.image.BufferedImage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import org.json.JSONException;
import org.json.JSONObject;

import javafx.embed.swing.SwingFXUtils;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import pixel.PixelApp;
import pixel.sprite.Sprite;
import pixel.sprite.SpriteTab;

public class PixelFileManager implements FileManager {

	public File showSaveDialog(ExtensionFilter filter) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(filter);
		return fileChooser.showSaveDialog(PixelApp.getWindow());
	}
	
	public File showOpenDialog(ExtensionFilter filter) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(filter);
		return fileChooser.showOpenDialog(PixelApp.getWindow());
	}
	
	@Override
	public void saveSprite(String path, Sprite sprite) {
		checkNotNull(sprite);
		try {
			FileManager.saveString(path, Sprite.serialiseToString(sprite));
			sprite.setPath(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void saveSprite(Sprite sprite) {
		String path = showSaveDialog(new ExtensionFilter("Pixel Files", "*.pixel")).getAbsolutePath();
		saveSprite(path, sprite);
	}
	
	@Override
	public Sprite loadSprite(String path) {
		try {
			String string = FileManager.loadString(path);
			return Sprite.deserialiseFromString(string);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public Sprite loadSprite() {
		String path = showOpenDialog(new ExtensionFilter("Pixel Files", "*.pixel")).getAbsolutePath();
		return loadSprite(path);
	}
	
	@Override
	public void exportSprite(String path, Sprite sprite) {
		checkNotNull(sprite);
		BufferedImage bufferedImage = new BufferedImage(sprite.getWidth(), sprite.getHeight(), BufferedImage.TYPE_INT_ARGB);
		SwingFXUtils.fromFXImage(sprite.exportImage(), bufferedImage);
		try {
			ImageIO.write(bufferedImage, "png", new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void exportSprite(Sprite sprite) {
		String path = showSaveDialog(new ExtensionFilter("Pixel Files", "*.pixel")).getAbsolutePath();
		exportSprite(path, sprite);
	}
	
	private void checkNotNull(Object obj) {
		if (obj == null) {
			throw new NullPointerException("This object cannot be null!");
		}
	}
}






















