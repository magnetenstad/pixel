package pixel.sprite;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A serializer for Sprite and SpriteLayer.
 * @author Magne Tenstad
 *
 */
public class SpriteSerializer {
	
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
	 * Throws an JSONException if the string is invalid.
	 * @param string
	 * @return sprite
	 */
	public static Sprite deserializeFromString(String string) throws JSONException {
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
		json.put("totalLayerCount", sprite.getTotalLayerCount());
		JSONArray spriteLayers = new JSONArray();
		for (SpriteLayer spriteLayer : sprite) {
			spriteLayers.put(serializeSpriteLayer(spriteLayer));
		}
		json.put("data", spriteLayers);

		return json;
	}
	
	/**
	 * Deserializes the given JSONObject to a Sprite.
	 * Throws an JSONException if the JSONObject is invalid.
	 * @param JSONObject
	 * @return sprite
	 */
	public static Sprite deserializeSprite(JSONObject json) throws JSONException {
		if (json == null || !(json.has("width") && json.has("height") && json.has("data"))) {
			throw new JSONException("Invalid json!");
		}
		int width = json.getInt("width");
		int height = json.getInt("height");
		Sprite sprite = new Sprite(width, height);
		if (json.has("totalLayerCount")) {
			sprite.setTotalLayerCount(json.getInt("totalLayerCount"));
		}
		JSONArray spriteLayers = json.getJSONArray("data");
		for (Object spriteLayerJSONObject : spriteLayers) {
			try {
				SpriteLayer spriteLayer = deserializeSpriteLayer(sprite, (JSONObject) spriteLayerJSONObject);
				sprite.add(spriteLayer);
			} catch (ClassCastException e) {
				throw new JSONException("Invalid json!");
			}
		}
		return sprite;
	}
	
	/**
	 * Serializes the given SpriteLayer to a JSONObject.
	 * @param spriteLayer
	 * @return JSONObject
	 */
	public static JSONObject serializeSpriteLayer(SpriteLayer spriteLayer) {
		JSONObject json = new JSONObject();
		json.put("name", spriteLayer.getName());
		json.put("visible", spriteLayer.isVisible());
		json.put("data", spriteLayer.toString());
		
		return json;
	}
	
	/**
	 * Deserializes the given JSONObject to a SpriteLayer.
	 * Throws an JSONException if the JSONObject is invalid.
	 * @param JSONObject
	 * @return spriteLayer
	 */
	public static SpriteLayer deserializeSpriteLayer(Sprite sprite, JSONObject json) throws JSONException {
		if (json == null || !json.has("data")) {
			throw new JSONException("Invalid json!");
		}
		SpriteLayer spriteLayer = new SpriteLayer(sprite);
		if (json.has("name")) {
			spriteLayer.setName(json.getString("name"));
		}
		if (json.has("visible")) {
			spriteLayer.setVisible(json.getBoolean("visible"));
		}
		if (json.has("data")) {
			String string = json.getString("data");
			try {
				spriteLayer.fromString(string);
			} catch (NumberFormatException e) {
				throw new JSONException("Invalid json!");
			}
		}
		return spriteLayer;
	}
}
