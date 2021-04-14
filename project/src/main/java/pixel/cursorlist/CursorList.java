package pixel.cursorlist;

import java.util.ArrayList;
import java.util.Iterator;

public class CursorList<T> implements Iterable<T> {
	protected ArrayList<CursorListListener> listeners = new ArrayList<>();
	protected ArrayList<T> elements = new ArrayList<>();
	protected int cursor = -1;
	
	public T get(int cursor) {
		if (isValidIndex(cursor)) {
			return elements.get(cursor);
		}
		return null;
	}
	public void set(int cursor, T element) {
		elements.set(cursor, element);
	}
	public void add(T element) {
		elements.add(element);
		notifyListeners(CursorListEvent.ElementAdded, element);	
		if (cursor == -1) {
			select(element);
		}
	}
	public void remove(T element) {
		if (getSelected() == element) {
			setCursor(-1);
		}
		elements.remove(element);
		notifyListeners(CursorListEvent.ElementRemoved, element);		
	}

	public void removeSelected() {
		T element = getSelected();
		if (element != null) {
			remove(element);
		}
	}
	
	public int size() {
		return elements.size();
	}
	public void select(T element) {
		setCursor(elements.indexOf(element));
	}
	public int getCursor() {
		return cursor;
	}
	public T getSelected() {
		if (isValidIndex(cursor)) {
			return elements.get(cursor);			
		}
		return null;
	}
	public void setCursor(int cursor) {
		if (!isValidIndex(cursor)) {
			throw new IllegalArgumentException();
		}
		this.cursor = cursor;
		notifyListeners(CursorListEvent.CursorChanged);
	}
	
	public boolean isValidIndex(int index) {
		return 0 <= index && index < size();
	}
	
	public void addListener(CursorListListener listener) {
		listeners.add(listener);
		notifyListeners(CursorListEvent.ListenerAdded);
	}
	public void removeListener(CursorListListener listener) {
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
