package pixel;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import javafx.scene.paint.Color;

public class Palette implements Iterable<Color> {
	private ArrayList<Color> colors = new ArrayList<>();
	private int index = 0;
	
	public int getIndex() {
		return index;
	}
	public int getIndex(Color color) {
		return colors.indexOf(color);
	}
	public void setIndex(int index) {
		if (!(0 <= index && index < size())) {
			throw new IllegalArgumentException();
		}
		this.index = index;
	}
	public Color getColor() {
		return colors.get(index);
	}
	public Color getColor(int index) {
		return colors.get(index);
	}
	public void setColor(Color color) {
		colors.set(index, color);
	}
	public void setColor(int index, Color color) {
		colors.set(index, color);
	}
	public void addColor(Color color) {
		colors.add(color);
	}
	public int size() {
		return colors.size();
	}
	
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
	
	public static Palette fromHexFile(String path) {
		Palette palette = new Palette();
		palette.addColor(Color.TRANSPARENT);
		try {
			Scanner in = new Scanner(new FileReader(path));
			while(in.hasNext()){
				String line = in.nextLine();
				palette.addColor(Color(line));
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return palette;
	}
	
	@Override
	public Iterator<Color> iterator() {
		return colors.iterator();
	}
}


