package pixel.file;

import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import pixel.PixelApp;
import pixel.sprite.Sprite;
import pixel.sprite.SpriteSerializer;

public class PixelFileManager implements FileManager {
	
	/*
	 * Prompts the user to save a file.
	 * @return The selected file, or null!
	 */
	public File showSaveDialog(ExtensionFilter filter) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(filter);
		return fileChooser.showSaveDialog(PixelApp.getWindow());
	}
	
	/*
	 * Prompts the user to open a file.
	 * @return The selected file, or null!
	 */
	public File showOpenDialog(ExtensionFilter filter) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(filter);
		return fileChooser.showOpenDialog(PixelApp.getWindow());
	}
	
	@Override
	public void saveSprite(String path, Sprite sprite) {
		checkNotNull(sprite);
		try {
			FileManager.writeString(path, SpriteSerializer.serializeToString(sprite));
			sprite.setPath(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void saveSprite(Sprite sprite) {
		File file = showSaveDialog(new ExtensionFilter("Pixel Files", "*.pixel"));
		if (file != null) {
			saveSprite(file.getAbsolutePath(), sprite);
		}
	}
	
	@Override
	public Sprite loadSprite(String path) {
		try {
			String string = FileManager.readString(path);
			return SpriteSerializer.deserializeFromString(string);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public Sprite loadSprite() {
		File file = showOpenDialog(new ExtensionFilter("Pixel Files", "*.pixel"));
		if (file != null) {
			return loadSprite(file.getAbsolutePath());
		}
		return null;
	}
	
	@Override
	public void exportSprite(String path, Sprite sprite) {
		checkNotNull(sprite);
		BufferedImage bufferedImage = new BufferedImage(sprite.getWidth(), sprite.getHeight(), BufferedImage.TYPE_INT_ARGB);
		SwingFXUtils.fromFXImage(sprite.getSpriteGui().exportImage(), bufferedImage);
		try {
			ImageIO.write(bufferedImage, "png", new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void exportSprite(Sprite sprite) {
		File file = showSaveDialog(new ExtensionFilter("Pixel Files", "*.png"));
		if (file != null) {
			exportSprite(file.getAbsolutePath(), sprite);
		}
	}
	
	private void checkNotNull(Object obj) {
		if (obj == null) {
			throw new NullPointerException("This object cannot be null!");
		}
	}
}




