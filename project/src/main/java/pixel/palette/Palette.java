package pixel.palette;

import java.io.FileReader;
import java.util.Scanner;
import javafx.scene.paint.Color;
import pixel.cursorlist.CursorList;

/**
 * An extension of CursorList<Color>
 * @author Magne Tenstad
 */
public class Palette extends CursorList<Color> {
	private static final int maxSize = 255; // Because color indices are saved as hexadecimals with two digits.
	
	/**
	 * Overrides the add method because Palette has a max size.
	 */
	@Override
	public void add(Color element) {
		if (size() >= maxSize) {
			throw new IllegalStateException("Palette is at max size and cannot be added to!");
		}
		super.add(element);
	}
	
	/**
	 * Instantiaties a Color from the given hex string.
	 * Throws an IllegalArgumentException if the hex is invalid.
	 * @param hex
	 * @return The corresponding color.
	 */
	public static Color Color(String hex) throws IllegalArgumentException {
		hex = hex.toLowerCase();
		if (!hex.matches("#?(\\d|a|b|c|d|e|f){6}")) {
			throw new IllegalArgumentException("Invalid hex! " + hex);
		}
		hex = hex.replaceFirst("#", "");
		double r = Integer.parseInt(hex.substring(0, 2), 16) / 255.0;
		double g = Integer.parseInt(hex.substring(2, 4), 16) / 255.0;
		double b = Integer.parseInt(hex.substring(4, 6), 16) / 255.0;
		return Color.color(r, g, b);
	}
	
	/**
	 * Instantiates a Palette from the given path.
	 * Returns an empty palette if the path is invalid.
	 * @param path
	 * @return palette
	 * @throws Exception 
	 */
	public static Palette fromHexFile(String path) throws Exception {
		Palette palette = new Palette();
		Scanner in = new Scanner(new FileReader(path));
		while(in.hasNext()){
			String line = in.nextLine();
			try {
				palette.add(Color(line));
			} catch (IllegalArgumentException e) {
				throw new Exception("Hex file is invalid!");
			}
		}
		in.close();
		return palette;
	}
}