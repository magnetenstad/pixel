package pixel.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;

import pixel.palette.Palette;
import pixel.sprite.Sprite;

/**
 * An interface for saving, loading and exporting sprites.
 * @author Magne Tenstad
 */
public interface FileManager {
	
	/**
	 * Writes the given string to the given path.
	 */
	public static void writeString(String path, String string) throws IOException {
		Files.writeString(Paths.get(path), string);
	}
	
	/**
	 * Reads a string from the given path.
	 * @return The read string.
	 */
	public static String readString(String path) throws IOException {
		return Files.readString(Paths.get(path));
	}
	
	/**
	 * Saves the given sprite to the given path.
	 */
	public void saveSprite(String path, Sprite sprite);
	
	/**
	 * Prompts the user to select a file and saves the given sprite to that file.
	 */
	public void saveSprite(Sprite sprite);
	
	/**
	 * Loads a sprite from the given path.
	 * @return The loaded sprite.
	 */
	public Sprite loadSprite(String path);
	
	/**
	 * Prompts the user to select a file and loads a sprite from that file.
	 * @return The loaded sprite.
	 */
	public Sprite loadSprite();
	
	/**
	 * Saves the given sprite to the given path as a PNG.
	 */
	public void exportSprite(String path, Sprite sprite, Palette palette);
	
	/**
	 * Prompts the user to select a file and saves the given sprite to that file as a PNG.
	 */
	public void exportSprite(Sprite sprite, Palette palette);
	
	/**
	 * @return A collection of recent paths.
	 */
	public Collection<String> getRecentPaths();
}
