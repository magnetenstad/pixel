package pixel.file;

import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.embed.swing.SwingFXUtils;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import pixel.PixelApp;
import pixel.palette.Palette;
import pixel.sprite.Sprite;
import pixel.sprite.SpriteSerializer;

/*
 * An implementation of FileManager.
 * Saves files to a .PIXEL (JSON-based) format,
 * and exports to .PNG.
 */
public class PixelFileManager implements FileManager {
	private final static File METADATA = new File("src/main/resources/metadata.json");
	private ArrayList<String> recentPaths = new ArrayList<>();
	
	public PixelFileManager() {
		readFromMetaData();
	}
	
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
			addToRecentPaths(path);
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
	public void exportSprite(String path, Sprite sprite, Palette palette) {
		checkNotNull(sprite);
		BufferedImage bufferedImage = new BufferedImage(sprite.getWidth(), sprite.getHeight(), BufferedImage.TYPE_INT_ARGB);
		SwingFXUtils.fromFXImage(sprite.exportImage(palette), bufferedImage);
		try {
			ImageIO.write(bufferedImage, "png", new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void exportSprite(Sprite sprite, Palette palette) {
		File file = showSaveDialog(new ExtensionFilter("Pixel Files", "*.png"));
		if (file != null) {
			exportSprite(file.getAbsolutePath(), sprite, palette);
		}
	}
	
	private void checkNotNull(Object obj) {
		if (obj == null) {
			throw new NullPointerException("This object cannot be null!");
		}
	}
	
	private void initializeMetaData() {
		if (!METADATA.exists()) {
			try {
				JSONObject json = new JSONObject();
				JSONArray recentPathsJSON = new JSONArray();
				json.put(Key.RecentPaths.toString(), recentPathsJSON);
				FileManager.writeString(METADATA.getAbsolutePath(), json.toString(2));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void readFromMetaData() {
		if (!METADATA.exists()) {
			initializeMetaData();
		}
		try {
			String string = FileManager.readString(METADATA.getAbsolutePath());
			JSONObject json = new JSONObject(string);
			
			if (!json.has(Key.RecentPaths.toString())) {
				METADATA.delete();
				readFromMetaData();
				return;
			}
			
			JSONArray recentPathsJSON = json.getJSONArray(Key.RecentPaths.toString());
			
			for (Object path : recentPathsJSON) {
				recentPaths.add((String) path);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void writeToMetaData() {
		if (!METADATA.exists()) {
			initializeMetaData();
		}
		try {
			String string = FileManager.readString(METADATA.getAbsolutePath());
			JSONObject json = new JSONObject(string);
			JSONArray recentPathsJSON = new JSONArray();
			
			for (String path : recentPaths) {
				recentPathsJSON.put(path);
			}
			
			json.put(Key.RecentPaths.toString(),  recentPathsJSON);
			FileManager.writeString(METADATA.getAbsolutePath(), json.toString(2));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void addToRecentPaths(String path) {
		recentPaths.add(path);
		writeToMetaData();
	}
}



