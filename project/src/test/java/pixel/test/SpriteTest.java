package pixel.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.*;

import pixel.cursorlist.CursorListEvent;
import pixel.cursorlist.CursorListListener;
import pixel.sprite.Sprite;
import pixel.sprite.SpriteLayer;

public class SpriteTest {
	Sprite sprite;
	ArrayList<CursorListEvent> listenedEvents;
	ArrayList<Object> listenedObjects;
	CursorListListener listener;
	
	@BeforeEach
	void init() {
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
		
		listenedEvents = new ArrayList<CursorListEvent>();
		listenedObjects = new ArrayList<Object>();
		listener = (list, event, object) -> {
			listenedEvents.add(event);
			listenedObjects.add(object);
		};
	}

	@Test
	void elementChangedEventTest() {
		int i = 0, j = 0;
		
		sprite.addListener(listener);
		
		assertEquals(CursorListEvent.ListenerAdded, listenedEvents.get(i++));
		assertNull(listenedObjects.get(j++));
		
		sprite.fillRect(0, 0, 1, 1, 0);
		
		assertEquals(2, listenedEvents.size());
		assertEquals(CursorListEvent.ElementChanged, listenedEvents.get(i++));
		assertEquals(sprite.getSelected(), listenedObjects.get(j++));
		
		sprite.fillRect(0, 0, 1, 1, 0, false);
		
		assertEquals(2, listenedEvents.size());
		
		sprite.clearRect(0, 0, 1, 1);
		
		assertEquals(3, listenedEvents.size());
		assertEquals(CursorListEvent.ElementChanged, listenedEvents.get(i++));
		assertEquals(sprite.getSelected(), listenedObjects.get(j++));
		
		sprite.clearRect(0, 0, 1, 1, false);
		
		assertEquals(3, listenedEvents.size());
	}
	
	@Test
	void isEditableTest() {
		assertTrue(sprite.isEditable());
		sprite.getSelected().setVisible(false);
		assertFalse(sprite.isEditable());
		sprite.setCursor(1);
		assertTrue(sprite.isEditable());
		sprite.deselect();
		assertFalse(sprite.isEditable());
		
		assertThrows(IllegalStateException.class, () -> sprite.fillRect(0, 0, 1, 1, 0));
	}
	
	@Test
	void layerNameTest() {
		assertTrue(sprite.get(0).getName().equals("Layer 1"));
		assertTrue(sprite.get(1).getName().equals("Layer 2"));
		assertTrue(sprite.get(2).getName().equals("Layer 3"));
		sprite.removeSelected();
		sprite.add(new SpriteLayer(sprite));
		assertTrue(sprite.get(2).getName().equals("Layer 4"));
	}
}
