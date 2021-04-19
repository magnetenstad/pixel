package pixel.sprite;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * A serializer for Sprite and SpriteLayer.
 * @author Magne Tenstad
 *
 */
public class SpriteSerializer {
	private final static String newLine = "_\n";
	
	/**
	 * Serializes the given Sprite to a String.
	 * @param sprite
	 * @return string
	 */
	public static String serializeToString(Sprite sprite) {
		return serializeSprite(sprite).toString(2);
	}
	
	/**
	 * Deserializes the given String to a Sprite.
	 * @param string
	 * @return sprite
	 */
	public static Sprite deserializeFromString(String string) {
		return deserializeSprite(new JSONObject(string));
	}
	
	/**
	 * Serializes the given Sprite to a JSONObject.
	 * @param sprite
	 * @return JSONObject
	 */
	public static JSONObject serializeSprite(Sprite sprite) {
		JSONObject json = new JSONObject();
		json.put("width", sprite.getWidth());
		json.put("height", sprite.getHeight());
		JSONArray spriteLayers = new JSONArray();
		for (SpriteLayer spriteLayer : sprite) {
			spriteLayers.put(serializeSpriteLayer(spriteLayer));
		}
		json.put("data", spriteLayers);

		return json;
	}
	
	/**
	 * Deserializes the given JSONObject to a Sprite.
	 * @param JSONObject
	 * @return sprite
	 */
	public static Sprite deserializeSprite(JSONObject json) {
		int width = json.getInt("width");
		int height = json.getInt("height");
		Sprite sprite = new Sprite(width, height);
		JSONArray spriteLayers = json.getJSONArray("data");
		for (Object spriteLayerJSONObject : spriteLayers) {
			SpriteLayer spriteLayer = deserializeSpriteLayer(sprite, (JSONObject) spriteLayerJSONObject);
			sprite.add(spriteLayer);
		}
		return sprite;
	}
	
	/**
	 * Serializes the given SpriteLayer to a JSONObject.
	 * @param spriteLayer
	 * @return JSONObject
	 */
	public static JSONObject serializeSpriteLayer(SpriteLayer spriteLayer) {
		String string = "";
		for (int y = 0; y < spriteLayer.getWidth(); y++) {
			for (int x = 0; x < spriteLayer.getHeight(); x++) {
				String hex = Integer.toHexString(spriteLayer.getPixel(x, y));
				if (hex.length() < 2) {
					hex = "0" + hex;
				}
				string += hex;
			}
			string += newLine;
		}
		
		JSONObject json = new JSONObject();
		json.put("name", spriteLayer.getName());
		json.put("data", string);
		
		return json;
	}
	
	/**
	 * Deserializes the given JSONObject to a SpriteLayer.
	 * @param JSONObject
	 * @return spriteLayer
	 */
	public static SpriteLayer deserializeSpriteLayer(Sprite sprite, JSONObject json) {
		String string = json.getString("data");
		SpriteLayer spriteLayer = new SpriteLayer(sprite);
		spriteLayer.setName(json.getString("name"));
		int x = 0;
		int y = 0;
		for (int i = 0; i < string.length() - 1; i += 2) {
			String c = string.substring(i, i + 2);
			if (!c.equals(newLine)) {
				spriteLayer.fillPixel(x, y, Integer.parseInt(c, 16));
				x++;
			}
			else {
				y++;
				x = 0;
			}
		}
		return spriteLayer;
	}	
}
