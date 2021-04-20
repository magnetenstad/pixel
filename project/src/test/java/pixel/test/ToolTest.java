package pixel.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import javafx.scene.input.MouseButton;
import pixel.sprite.Sprite;
import pixel.sprite.SpriteLayer;
import pixel.tool.*;

public class ToolTest {
	Sprite sprite;
	SpriteLayer spriteLayer0;
	SpriteLayer spriteLayer1;
	
	@BeforeEach
	void init() {
		sprite = new Sprite(4, 4);
		spriteLayer0 = new SpriteLayer(sprite);
		spriteLayer0.fillRect(0, 0, 4, 4, 17);
		spriteLayer1 = new SpriteLayer(sprite);
		spriteLayer1.fillRect(1, 1, 2, 2, 0);
		sprite.add(spriteLayer0);
		sprite.add(spriteLayer1);
	}
	
	@Test
	void testInitialSpriteValues() {
		assertEquals("11111111_\n11111111_\n11111111_\n11111111_\n", spriteLayer0.toString());
		assertEquals("ffffffff_\nff0000ff_\nff0000ff_\nffffffff_\n", spriteLayer1.toString());
	}
	
	@Test
	void PencilToolTest() {
		Tool pencilTool = new PencilTool();
		sprite.select(spriteLayer1);
		
		pencilTool.use(sprite, new ToolInputEvent(0, 0, MouseButton.PRIMARY, true, false));
		
		assertEquals("11111111_\n11111111_\n11111111_\n11111111_\n", spriteLayer0.toString());
		assertEquals("ffffffff_\nff0000ff_\nff0000ff_\nffffffff_\n", spriteLayer1.toString());
		
		pencilTool.setColor(10);
		
		pencilTool.use(sprite, new ToolInputEvent(0, 0, MouseButton.PRIMARY, true, false));
		
		assertEquals("11111111_\n11111111_\n11111111_\n11111111_\n", spriteLayer0.toString());
		assertEquals("0affffff_\nff0000ff_\nff0000ff_\nffffffff_\n", spriteLayer1.toString());
		
		pencilTool.use(sprite, new ToolInputEvent(0, 0, MouseButton.SECONDARY, false, true));
		
		assertEquals("11111111_\n11111111_\n11111111_\n11111111_\n", spriteLayer0.toString());
		assertEquals("ffffffff_\nff0000ff_\nff0000ff_\nffffffff_\n", spriteLayer1.toString());
	}
	
	@Test
	void EraserToolTest() {
		Tool eraserTool = new EraserTool();
		sprite.select(spriteLayer0);
		
		eraserTool.use(sprite, new ToolInputEvent(0, 0, MouseButton.PRIMARY, true, false));
		
		assertEquals("ff111111_\n11111111_\n11111111_\n11111111_\n", spriteLayer0.toString());
		assertEquals("ffffffff_\nff0000ff_\nff0000ff_\nffffffff_\n", spriteLayer1.toString());
		
		eraserTool.use(sprite, new ToolInputEvent(1, 0, MouseButton.SECONDARY, false, true));
		
		assertEquals("ffff1111_\n11111111_\n11111111_\n11111111_\n", spriteLayer0.toString());
		assertEquals("ffffffff_\nff0000ff_\nff0000ff_\nffffffff_\n", spriteLayer1.toString());
		
		sprite.select(spriteLayer1);
		
		eraserTool.use(sprite, new ToolInputEvent(1, 1, MouseButton.PRIMARY, true, false));
		
		assertEquals("ffff1111_\n11111111_\n11111111_\n11111111_\n", spriteLayer0.toString());
		assertEquals("ffffffff_\nffff00ff_\nff0000ff_\nffffffff_\n", spriteLayer1.toString());
	}
	
	@Test
	void LineToolTest() {
		Tool lineTool = new LineTool();
		sprite.select(spriteLayer1);
		
		lineTool.setColor(10);
		
		lineTool.use(sprite, new ToolInputEvent(1, 1, MouseButton.PRIMARY, true, false));
		
		assertEquals("11111111_\n11111111_\n11111111_\n11111111_\n", spriteLayer0.toString());
		assertEquals("ffffffff_\nff0000ff_\nff0000ff_\nffffffff_\n", spriteLayer1.toString());
		
		lineTool.use(sprite, new ToolInputEvent(2, 2, MouseButton.PRIMARY, false, false));
		
		assertEquals("11111111_\n11111111_\n11111111_\n11111111_\n", spriteLayer0.toString());
		assertEquals("ffffffff_\nff0a00ff_\nff000aff_\nffffffff_\n", spriteLayer1.toString());
		
		lineTool.use(sprite, new ToolInputEvent(0, 0, MouseButton.PRIMARY, true, false));
		lineTool.use(sprite, new ToolInputEvent(3, 0, MouseButton.PRIMARY, false, false));
		
		assertEquals("11111111_\n11111111_\n11111111_\n11111111_\n", spriteLayer0.toString());
		assertEquals("0a0a0a0a_\nff0a00ff_\nff000aff_\nffffffff_\n", spriteLayer1.toString());
	}
	
	@Test
	void BucketToolTest() {
		Tool bucketTool = new BucketTool();
		sprite.select(spriteLayer1);
		
		bucketTool.setColor(10);
		
		bucketTool.use(sprite, new ToolInputEvent(0, 0, MouseButton.PRIMARY, true, false));
		
		assertEquals("11111111_\n11111111_\n11111111_\n11111111_\n", spriteLayer0.toString());
		assertEquals("0a0a0a0a_\n0a00000a_\n0a00000a_\n0a0a0a0a_\n", spriteLayer1.toString());
		
		bucketTool.use(sprite, new ToolInputEvent(1, 1, MouseButton.PRIMARY, true, false));
		
		assertEquals("11111111_\n11111111_\n11111111_\n11111111_\n", spriteLayer0.toString());
		assertEquals("0a0a0a0a_\n0a0a0a0a_\n0a0a0a0a_\n0a0a0a0a_\n", spriteLayer1.toString());
		
		sprite.select(spriteLayer0);
		
		bucketTool.use(sprite, new ToolInputEvent(3, 3, MouseButton.PRIMARY, true, false));
		
		assertEquals("0a0a0a0a_\n0a0a0a0a_\n0a0a0a0a_\n0a0a0a0a_\n", spriteLayer0.toString());
		assertEquals("0a0a0a0a_\n0a0a0a0a_\n0a0a0a0a_\n0a0a0a0a_\n", spriteLayer1.toString());
	}
}
