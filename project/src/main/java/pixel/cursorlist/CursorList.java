package pixel.cursorlist;

import java.util.ArrayList;
import java.util.Iterator;

public class CursorList<T> implements Iterable<T> {
	protected ArrayList<CursorListListener> listeners = new ArrayList<>();
	protected ArrayList<T> elements = new ArrayList<>();
	protected int cursor = -1;
	
	/*
	 * @param index
	 * @return The element at the given index, or null.
	 */
	public T get(int index) {
		checkValidIndex(index);
		return elements.get(index);
	}
	
	/*
	 * @param index
	 * @param element
	 */
	public void set(int index, T element) {
		checkValidIndex(index);
		elements.set(index, element);
	}
	
	/*
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
	
	public int size() {
		return elements.size();
	}
	
	public void select(T element) {
		checkElement(element);
		setCursor(elements.indexOf(element));
	}
	
	public T getSelected() {
		if (isValidIndex(cursor)) {
			return elements.get(cursor);			
		}
		return null;
	}
	
	public void removeSelected() {
		T element = getSelected();
		if (element == null) {
			throw new IllegalStateException("No element is selected!");
		}
		remove(element);
	}
	
	public int getCursor() {
		return cursor;
	}
	
	public void setCursor(int cursor) {
		checkValidIndex(cursor);
		this.cursor = cursor;
		notifyListeners(CursorListEvent.CursorChanged);
	}
	
	private void deselect() {
		this.cursor = -1;
		notifyListeners(CursorListEvent.CursorChanged);
	}
	public boolean isValidIndex(int index) {
		return 0 <= index && index < size();
	}
	private void checkValidIndex(int index) {
		if (!isValidIndex(index)) {
			throw new IndexOutOfBoundsException(index);
		}
	}
	private void checkElement(T element) {
		if (!elements.contains(element)) {
			throw new IllegalArgumentException("Elements does not contain" + element);
		}
	}
	
	public void addListener(CursorListListener listener) {
		if (listener == null) {
			throw new IllegalArgumentException("Listener cannot be null!");
		}
		listeners.add(listener);
		notifyListeners(CursorListEvent.ListenerAdded);
	}
	public void removeListener(CursorListListener listener) {
		if (!listeners.contains(listener)) {
			throw new IllegalArgumentException("Listeners does not contain" + listener);
		}
		listeners.remove(listener);
	}
	
	public void notifyListeners(CursorListEvent event) {
		notifyListeners(event, null);
	}
	
	public void notifyListeners(CursorListEvent event, T element) {
		for (CursorListListener listener : listeners) {
			listener.cursorListChanged(this, event, element);
		}
	}
	
	@Override
	public Iterator<T> iterator() {
		return elements.iterator();
	}
}
