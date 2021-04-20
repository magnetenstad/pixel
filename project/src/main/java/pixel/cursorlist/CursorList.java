package pixel.cursorlist;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * CursorList is an extension of ArrayList, with a cursor.
 * The element at the cursor position is 'selected'.
 * Selection will occur automatically when adding or removing elements,
 * but may also be set explicitly.
 * Additionally, CursorList is observable by CursorListListeners.
 * @author Magne Tenstad
 */
public class CursorList<T> implements Iterable<T> {
	protected ArrayList<CursorListListener> listeners = new ArrayList<>();
	protected ArrayList<T> elements = new ArrayList<>();
	protected int cursor = -1;
	
	/**
	 * Gets the element at the given index.
	 * Throws an IndexOutOfBoundsException if the index is not valid.
	 * @param index
	 * @return The element at the given index.
	 */
	public T get(int index) {
		checkValidIndex(index);
		return elements.get(index);
	}
	
	/**
	 * Sets the given element at the given index.
	 * Throws an IndexOutOfBoundsException if the index is not valid.
	 * Notifies listeners.
	 * @param index
	 * @param element
	 */
	public void set(int index, T element) {
		checkValidIndex(index);
		elements.set(index, element);
		notifyListeners(CursorListEvent.ElementChanged, element);
	}
	
	/**
	 * Appends the given element to the end of the list.
	 * Notifies listeners.
	 * Selects the element if no element is selected.
	 * @param element
	 */
	public void add(T element) {
		elements.add(element);
		notifyListeners(CursorListEvent.ElementAdded, element);	
		if (cursor == -1) {
			setCursor(0);
		}
	}
	
	/**
	 * Removes the given element.
	 * Moves the cursor if the given element is selected.
	 * Notifies listeners.
	 * @param element
	 */
	public void remove(T element) {
		checkElement(element);
		if (getSelected() == element) {
			if (cursor == 0) {
				if (size() == 1) {
					deselect();
				}
				else {
					setCursor(cursor);
				}
			}
			else {
				setCursor(cursor - 1);
			}
		}
		elements.remove(element);
		notifyListeners(CursorListEvent.ElementRemoved, element);		
	}
	
	/**
	 * @return The number of elements.
	 */
	public int size() {
		return elements.size();
	}
	
	/**
	 * Sets the cursor to the index of the given element.
	 * @param element
	 */
	public void select(T element) {
		checkElement(element);
		setCursor(elements.indexOf(element));
	}
	
	/**
	 * 
	 * @return The selected element, or null.
	 */
	public T getSelected() {
		if (isValidIndex(cursor)) {
			return elements.get(cursor);			
		}
		return null;
	}
	
	/**
	 * Removes the selected element, or throws an IllegalStateException.
	 */
	public void removeSelected() {
		T element = getSelected();
		if (element == null) {
			throw new IllegalStateException("No element is selected!");
		}
		remove(element);
	}
	
	/**
	 * Moves the selected element one step forwards.
	 * Throws an IllegalStateException if selected is null or cannot be moved further.
	 */
	public void moveSelectedForward() {
		if (getSelected() == null || cursor == 0) {
			throw new IllegalStateException("Selected is null or already first!");
		}
		T a = elements.get(cursor - 1);
		T b = elements.get(cursor);
		elements.set(cursor - 1, b);
		elements.set(cursor, a);
		select(b);
		notifyListeners(CursorListEvent.ElementsReordered);
	}
	
	/**
	 * Moves the selected element one step backwards.
	 * Throws an IllegalStateException if selected is null or cannot be moved further.
	 */
	public void moveSelectedBackward() {
		if (getSelected() == null || cursor + 1 >= elements.size()) {
			throw new IllegalStateException("Selected is null or already last!");
		}
		T a = elements.get(cursor);
		T b = elements.get(cursor + 1);
		elements.set(cursor, b);
		elements.set(cursor + 1, a);
		select(a);
		notifyListeners(CursorListEvent.ElementsReordered);
	}
	
	/**
	 * Gets the cursor.
	 * @return cursor
	 */
	public int getCursor() {
		return cursor;
	}
	
	/**
	 * Sets the cursor to the given value.
	 * Throws an IndexOutOfBoundsException if the value is not a valid index.
	 * @param cursor
	 */
	public void setCursor(int cursor) {
		checkValidIndex(cursor);
		this.cursor = cursor;
		notifyListeners(CursorListEvent.CursorChanged);
	}
	
	/**
	 * Sets the cursor to -1.
	 * (Needed because -1 is not a valid argument for setCursor())
	 */
	public void deselect() {
		this.cursor = -1;
		notifyListeners(CursorListEvent.CursorChanged);
	}
	
	/**
	 * 
	 * @param index
	 * @return Whether or not the given index is valid.
	 */
	public boolean isValidIndex(int index) {
		return 0 <= index && index < size();
	}
	
	/**
	 * Throws an IndexOutOfBoundsException if the given index is invalid.
	 * @param index
	 */
	private void checkValidIndex(int index) {
		if (!isValidIndex(index)) {
			throw new IndexOutOfBoundsException(index);
		}
	}
	
	/**
	 * Throws an IllegalArgumentException if the list does not contain the given element.
	 * @param element
	 */
	private void checkElement(T element) {
		if (!elements.contains(element)) {
			throw new IllegalArgumentException("Elements does not contain" + element);
		}
	}
	
	/**
	 * Adds the given listener to listeners.
	 * Throws an IllegalArgumentException if the given listener is null.
	 * Notifies listeners.
	 * @param listener
	 */
	public void addListener(CursorListListener listener) {
		if (listener == null) {
			throw new IllegalArgumentException("Listener cannot be null!");
		}
		listeners.add(listener);
		notifyListeners(CursorListEvent.ListenerAdded);
	}
	
	/**
	 * Removes the given listener from listeners.
	 * Throws an IllegalArgumentException if listeners does not contain the given listener.
	 * @param listener
	 */
	public void removeListener(CursorListListener listener) {
		if (!listeners.contains(listener)) {
			throw new IllegalArgumentException("Listeners does not contain" + listener);
		}
		listeners.remove(listener);
	}
	
	/**
	 * Notifies listeners about the given event.
	 * @param event
	 */
	public void notifyListeners(CursorListEvent event) {
		notifyListeners(event, null);
	}
	
	/**
	 * Notifies listeners about the given event.
	 * Includes a relevant element.
	 * @param event
	 * @param element
	 */
	public void notifyListeners(CursorListEvent event, T element) {
		for (CursorListListener listener : listeners) {
			listener.cursorListChanged(this, event, element);
		}
	}
	
	/**
	 * @return iterator
	 */
	@Override
	public Iterator<T> iterator() {
		return elements.iterator();
	}
}
