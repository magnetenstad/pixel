package pixel.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.*;

import pixel.cursorlist.CursorList;
import pixel.cursorlist.CursorListEvent;
import pixel.cursorlist.CursorListListener;

public class CursorListTest {
	CursorList<String> cursorList;
	String zero = "zero";
	String one = "one";
	String two = "two";
	ArrayList<CursorListEvent> listenedEvents;
	ArrayList<Object> listenedObjects;
	CursorListListener listener;
	
	@BeforeEach
	void init() {
		cursorList = new CursorList<String>();
		listenedEvents = new ArrayList<CursorListEvent>();
		listenedObjects = new ArrayList<Object>();
		listener = (list, event, object) -> {
			listenedEvents.add(event);
			listenedObjects.add(object);
		};
	}
	
	void checkCursorList(CursorList<String> cursorList, List<String> elements) {
		for (int i = 0; i < cursorList.size(); i++) {
			assertEquals(elements.get(i), cursorList.get(i));
		}
	}
	
	@Test
	@DisplayName("Test for .add()")
	void addTest() {
		assertThrows(IndexOutOfBoundsException.class, () -> cursorList.get(0));
		
		cursorList.add(zero);
		cursorList.add(one);
		cursorList.add(one);
		
		assertEquals(zero, cursorList.get(0));
		assertEquals(one, cursorList.get(1));
		assertEquals(one, cursorList.get(2));
	}
	
	@Test
	@DisplayName("Test for iterability")
	void checkIterable() {
		cursorList.add(zero);
		cursorList.add(one);
		cursorList.add(two);

		checkCursorList(cursorList, List.of(zero, one, two));
	}
	
	@Test
	@DisplayName("Test for .set()")
	void setTest() {
		assertThrows(IndexOutOfBoundsException.class, () -> cursorList.set(0, zero));
		assertThrows(IndexOutOfBoundsException.class, () -> cursorList.get(0));
		
		cursorList.add(zero);
		cursorList.add(one);
		cursorList.add(two);
		
		checkCursorList(cursorList, List.of(zero, one, two));
		
		cursorList.set(0, zero);
		cursorList.set(1, zero);
		cursorList.set(2, zero);
		
		checkCursorList(cursorList, List.of(zero, zero, zero));
	}
	
	@Test
	@DisplayName("Test for .remove()")
	void removeTest() {
		assertThrows(IllegalArgumentException.class, () -> cursorList.remove(zero));
		
		cursorList.add(zero);
		cursorList.add(one);
		cursorList.add(two);
		
		checkCursorList(cursorList, List.of(zero, one, two));
		
		cursorList.remove(one);
		
		checkCursorList(cursorList, List.of(zero, two));
		
		assertThrows(IllegalArgumentException.class, () -> cursorList.remove(one));
	}
	
	@Test
	@DisplayName("Test for .size()")
	void sizeTest() {
		assertEquals(0, cursorList.size());
		
		cursorList.add(zero);
		cursorList.add(one);
		cursorList.add(two);
		
		assertEquals(3, cursorList.size());
		
		cursorList.remove(zero);
		
		assertEquals(2, cursorList.size());
	}
	
	@Test
	@DisplayName("Test for .select()")
	void selectTest() {
		assertNull(cursorList.getSelected());
		
		cursorList.add(zero);
		cursorList.add(one);
		cursorList.add(two);
		
		cursorList.select(one);
		
		assertEquals(one, cursorList.getSelected());
	}
	
	@Test
	@DisplayName("Test for autoselect with .add(), .remove() and .removeSelected()")
	void autoSelectTest() {
		assertNull(cursorList.getSelected());
		
		cursorList.add(zero);
		
		assertEquals(zero, cursorList.getSelected());
		
		cursorList.remove(zero);
		
		assertNull(cursorList.getSelected());
		
		cursorList.add(zero);
		cursorList.add(one);
		cursorList.add(two);
		
		cursorList.select(one);
		
		assertEquals(one, cursorList.getSelected());
		
		cursorList.removeSelected();
		
		assertEquals(zero, cursorList.getSelected());
		
		cursorList.removeSelected();
		
		assertEquals(two, cursorList.getSelected());
		
		cursorList.removeSelected();
		
		assertNull(cursorList.getSelected());
	}
	
	@Test
	@DisplayName("Test for .removeSelected()")
	void removeSelectedTest() {
		assertNull(cursorList.getSelected());
		
		cursorList.add(zero);
		cursorList.select(zero);
		
		assertEquals(zero, cursorList.getSelected());
		
		cursorList.removeSelected();
		
		assertNull(cursorList.getSelected());
		assertEquals(0, cursorList.size());
		
		assertThrows(IllegalStateException.class, () -> cursorList.removeSelected());
	}
	
	@Test
	@DisplayName("Test for .setCursor()")
	void setCursorTest() {
		assertEquals(-1, cursorList.getCursor());
		assertThrows(IndexOutOfBoundsException.class, () -> cursorList.setCursor(0));
		
		cursorList.add(zero);
		cursorList.setCursor(0);
		
		assertEquals(0, cursorList.getCursor());
		
		cursorList.remove(zero);
		
		assertEquals(-1, cursorList.getCursor());
	}
	
	@Test
	@DisplayName("Test for .moveSelectedForward() and .moveSelectedBackward()")
	void moveSelectedTest() {
		cursorList.add(zero);
		cursorList.add(one);
		cursorList.add(two);
		
		assertThrows(IllegalStateException.class, () -> cursorList.moveSelectedForward());
		
		cursorList.moveSelectedBackward();
		
		checkCursorList(cursorList, List.of(one, zero, two));
		
		cursorList.moveSelectedBackward();
		
		checkCursorList(cursorList, List.of(one, two, zero));
		
		assertThrows(IllegalStateException.class, () -> cursorList.moveSelectedBackward());
		
		cursorList.moveSelectedForward();
		
		checkCursorList(cursorList, List.of(one, zero, two));
	}
	
	@Test
	@DisplayName("Test for CursorListEvent.ListenerAdded")
	void listenerAddedListenerTest() {
		int i = 0, j = 0;
		
		cursorList.addListener(listener);
		
		assertEquals(CursorListEvent.ListenerAdded, listenedEvents.get(i++));
		assertNull(listenedObjects.get(j++));
	}
	
	@Test
	@DisplayName("Test for CursorListEvent.ElementAdded")
	void elementAddedListenerTest() {
		int i = 0, j = 0;
		
		cursorList.addListener(listener);
		
		assertEquals(CursorListEvent.ListenerAdded, listenedEvents.get(i++));
		assertNull(listenedObjects.get(j++));
		
		cursorList.add(zero);
		
		assertEquals(CursorListEvent.ElementAdded, listenedEvents.get(i++));
		assertEquals(zero, listenedObjects.get(j++));
		assertEquals(CursorListEvent.CursorChanged, listenedEvents.get(i++));
		assertNull(listenedObjects.get(j++));
		
		cursorList.add(one);
		
		assertEquals(CursorListEvent.ElementAdded, listenedEvents.get(i++));
		assertEquals(one, listenedObjects.get(j++));
	}
	
	@Test
	@DisplayName("Test for CursorListEvent.ElementRemoved")
	void elementRemovedListenerTest() {
		int i = 0, j = 0;
		
		cursorList.add(zero);
		cursorList.add(one);
		cursorList.add(two);
		
		cursorList.addListener(listener);
		
		assertEquals(CursorListEvent.ListenerAdded, listenedEvents.get(i++));
		assertNull(listenedObjects.get(j++));
		
		cursorList.remove(two);
		
		assertEquals(CursorListEvent.ElementRemoved, listenedEvents.get(i++));
		assertEquals(two, listenedObjects.get(j++));
		
		cursorList.remove(zero);
		
		assertEquals(CursorListEvent.CursorChanged, listenedEvents.get(i++));
		assertNull(listenedObjects.get(j++));
		assertEquals(CursorListEvent.ElementRemoved, listenedEvents.get(i++));
		assertEquals(zero, listenedObjects.get(j++));
	}
	
	@Test
	@DisplayName("Test for CursorListEvent.ElementsReordered")
	void elementsReorderedListenerTest() {
		int i = 0, j = 0;
		
		cursorList.add(zero);
		cursorList.add(one);
		cursorList.add(two);
		
		cursorList.addListener(listener);
		
		assertEquals(CursorListEvent.ListenerAdded, listenedEvents.get(i++));
		assertNull(listenedObjects.get(j++));
		
		assertThrows(IllegalStateException.class, () -> cursorList.moveSelectedForward());
		
		cursorList.moveSelectedBackward();
		
		assertEquals(CursorListEvent.CursorChanged, listenedEvents.get(i++));
		assertNull(listenedObjects.get(j++));
		assertEquals(CursorListEvent.ElementsReordered, listenedEvents.get(i++));
		assertNull(listenedObjects.get(j++));
		
		cursorList.moveSelectedForward();
		
		assertEquals(CursorListEvent.CursorChanged, listenedEvents.get(i++));
		assertNull(listenedObjects.get(j++));
		assertEquals(CursorListEvent.ElementsReordered, listenedEvents.get(i++));
		assertNull(listenedObjects.get(j++));
	}
}














