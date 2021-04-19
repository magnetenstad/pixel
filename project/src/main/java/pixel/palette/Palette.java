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
	
	/**
	 * Instantiaties a Color from the given hex string.
	 * Throws an IllegalArgumentException if the hex is invalid.
	 * @param hex
	 * @return The corresponding color.
	 */
	public static Color Color(String hex) {
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
	 */
	public static Palette fromHexFile(String path) {
		Palette palette = new Palette();
		try {
			Scanner in = new Scanner(new FileReader(path));
			while(in.hasNext()){
				String line = in.nextLine();
				palette.add(Color(line));
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return palette;
	}
}