package pixel.test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import org.json.JSONException;
import org.junit.jupiter.api.*;

import pixel.file.FileManager;
import pixel.file.PixelFileManager;
import pixel.sprite.Sprite;
import pixel.sprite.SpriteLayer;
import pixel.sprite.SpriteSerializer;

public class PixelFileManagerTest {
	FileManager fileManager;
	Sprite sprite;
	
	@BeforeEach
	void init() {
		fileManager = new PixelFileManager();
		sprite = new Sprite(4, 4);
		sprite.add(new SpriteLayer(sprite));
		sprite.add(new SpriteLayer(sprite));
		sprite.add(new SpriteLayer(sprite));
		sprite.setCursor(0);
		sprite.fillRect(0, 0, 2, 2, 0);
		sprite.setCursor(1);
		sprite.fillRect(1, 1, 2, 2, 1);
		sprite.setCursor(2);
		sprite.fillRect(2, 2, 2, 2, 2);
	}
	
	@Test
	public void testLoadSprite() {
		Sprite testSprite = null;
		try {
			testSprite = fileManager.loadSprite("src/test/resources/testSprite.pixel");
		} catch (JSONException | IOException e) {
			e.printStackTrace();
		}
		assertNotNull(testSprite);
		assertEquals(SpriteSerializer.serializeToString(sprite), SpriteSerializer.serializeToString(testSprite));
	}
	
	@Test
	public void testLoadNonExistingFile() {
		assertThrows(IOException.class, () -> fileManager.loadSprite("src/test/resources/nonExistingSprite.pixel"));
	}
	
	@Test
	public void testLoadInvalidSprite() {
		assertThrows(JSONException.class, () -> fileManager.loadSprite("src/test/resources/invalidSprite.pixel"));
		assertThrows(JSONException.class, () -> fileManager.loadSprite("src/test/resources/invalidSprite2.pixel"));
	}
	
	@Test
	public void testSaveSprite() {
		fileManager.saveSprite("src/test/resources/newTestSprite.pixel", sprite);
		
		byte[] testSprite = null, newTestSprite = null;
		
		try {
			testSprite = Files.readAllBytes(Path.of("src/test/resources/testSprite.pixel"));
		} catch (IOException e) {
			fail("Could not load test file");
		}

		try {
			newTestSprite = Files.readAllBytes(Path.of("src/test/resources/newTestSprite.pixel"));
		} catch (IOException e) {
			fail("Could not load saved file");
		}
		assertNotNull(testSprite);
		assertNotNull(newTestSprite);
		assertTrue(Arrays.equals(testSprite, newTestSprite));
	}
	
	@AfterAll
	static void cleanUp() {
		File newSavedSprite = new File("src/test/resources/newTestSprite.pixel");
		newSavedSprite.delete();
	}
}
