package pixel.test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.*;

import pixel.palette.Palette;

public class PaletteTest {
	Palette palette;
	
	@BeforeEach
	void init() {
		palette = new Palette();
		palette.add(Palette.Color("000000"));
		palette.add(Palette.Color("111111"));
		palette.add(Palette.Color("222222"));
		palette.add(Palette.Color("333333"));
	}
	
	@Test
	void testLoadPalette() {
		Palette testPalette = null;
		try {
			testPalette = Palette.fromHexFile("src/test/resources/testPalette.hex");
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertNotNull(testPalette);
		assertEquals(palette.size(), testPalette.size());
		for (int i = 0; i < palette.size(); i++) {			
			assertTrue(palette.get(i).equals(testPalette.get(i)));
		}
	}
	
	@Test
	void testLoadNonExistingPalette() {
		assertThrows(FileNotFoundException.class, () -> Palette.fromHexFile("src/test/resources/nonExistingPalette.hex"));
	}
	
	@Test
	void testLoadInvalidPalette() {
		assertThrows(Exception.class, () -> Palette.fromHexFile("src/test/resources/invalidPalette.hex"));
	}
	
	@Test
	void testMaxSize() {
		Palette palette2 = new Palette();
		for (int i = 0; i < 255; i++) {			
			palette2.add(Palette.Color("000000"));
		}
		assertThrows(IllegalStateException.class, () -> palette2.add(Palette.Color("000000")));
	}
}
