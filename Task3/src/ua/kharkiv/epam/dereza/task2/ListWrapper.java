package ua.kharkiv.epam.dereza.task2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * 
 * @author Eduard_Dereza
 * 
 * @param <E>
 */
public class ListWrapper<E> implements List<E> {

	List<E> unmodifiable;
	List<E> modifiable;

	public ListWrapper(List<E> unmodifiable, List<E> modifiable) {
		this.unmodifiable = unmodifiable;
		this.modifiable = modifiable;
	}

	@Override
	public boolean add(E e) {
		return modifiable.add(e);
	}

	@Override
	public void add(int index, E element) {
		int indexForModifiableList = index - unmodifiable.size();
		if (indexForModifiableList >= 0
				&& indexForModifiableList < modifiable.size()){
			modifiable.add(indexForModifiableList, element);
			return;
		}
		throw new IllegalArgumentException(
				"You cannot modify unmodifiable part of list, modifiable part starts from "
						+ unmodifiable.size() + " index");

	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		return modifiable.addAll(c);
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		int indexForModifiableList = index - unmodifiable.size();
		if (indexForModifiableList >= 0
				&& indexForModifiableList < modifiable.size()){
			return modifiable.addAll(indexForModifiableList, c);
		}
		throw new IllegalArgumentException(
				"You cannot modify unmodifiable part of list, modifiable part starts from "
						+ unmodifiable.size() + " index");
	}

	@Override
	public void clear() {
			throw new UnsupportedOperationException();
	}

	@Override
	public boolean contains(Object o) {
		return modifiable.contains(o) || unmodifiable.contains(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		for (Object obj : c) {
			if (!modifiable.contains(obj) && !unmodifiable.contains(obj))
				return false;
		}
		return true;
	}

	@Override
	public E get(int index) {
		if (index < unmodifiable.size()) {
			return unmodifiable.get(index);
		} else {
			return modifiable.get(index - unmodifiable.size());
		}
	}

	@Override
	public int indexOf(Object o) {
		int index = unmodifiable.indexOf(o);
		if (index != -1)
			return index;
		
		index = modifiable.indexOf(o);
		if (index != -1)
			return (index + unmodifiable.size());
		return index;
	}

	@Override
	public boolean isEmpty() {
		return modifiable.isEmpty() && unmodifiable.isEmpty();
	}

	@Override
	public Iterator<E> iterator() {
		return new IteratorImpl();
	}

	@Override
	public int lastIndexOf(Object o) {
		int lastIndex = modifiable.lastIndexOf(o);
		if (lastIndex != -1)
			return modifiable.size() + lastIndex;
		
		lastIndex = unmodifiable.lastIndexOf(o);
		if (lastIndex != -1)
			return lastIndex;
		return lastIndex;
	}

	@SuppressWarnings("unused")
	@Override
	public ListIterator<E> listIterator() {
		throw new UnsupportedOperationException();
	}

	@SuppressWarnings("unused")
	@Override
	public ListIterator<E> listIterator(int index) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean remove(Object o) {
		if (unmodifiable.contains(o))
			throw new IllegalArgumentException(
					"You cannot modify unmodifiable part of list, modifiable part starts from "
							+ unmodifiable.size() + " index");
		return modifiable.remove(o);
	}

	@Override
	public E remove(int index) {
		int indexForRemoving = index - unmodifiable.size();
		if (indexForRemoving >= 0 && indexForRemoving < modifiable.size())
			return modifiable.remove(indexForRemoving);
		
		throw new IllegalArgumentException(
				"You cannot modify unmodifiable part of list, modifiable part starts from "
						+ unmodifiable.size() + " index");
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		for (Object o : c) {
			if (unmodifiable.contains(o))
				throw new IllegalArgumentException(
						"You cannot modify unmodifiable part of list, modifiable part starts from "
								+ unmodifiable.size() + " index");
		}

		return modifiable.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		for (Object o : unmodifiable) {
			if (!c.contains(o))
				throw new IllegalArgumentException(
						"You cannot modify unmodifiable part of list, modifiable part starts from "
								+ unmodifiable.size() + " index");

		}

		return modifiable.retainAll(c);
	}

	@Override
	public E set(int index, E element) {
		int indexForSetting = index - unmodifiable.size();
		if (indexForSetting >= 0
				&& indexForSetting < modifiable.size())
			return modifiable.set(indexForSetting, element);
		
		throw new IllegalArgumentException(
					"You cannot modify unmodifiable part of list, modifiable part starts from "
							+ unmodifiable.size() + " index");
	}

	@Override
	public int size() {
		return modifiable.size() + unmodifiable.size();
	}

	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		List<E> sublist = new ArrayList<E>();
		sublist.addAll(unmodifiable);
		sublist.addAll(modifiable);
		return sublist.subList(fromIndex, toIndex);

	}

	@Override
	public Object[] toArray() {
		List<E> sublist = new ArrayList<E>();
		sublist.addAll(unmodifiable);
		sublist.addAll(modifiable);
		return sublist.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		List<E> sublist = new ArrayList<E>();
		sublist.addAll(unmodifiable);
		sublist.addAll(modifiable);
		return sublist.toArray(a);
	}
	
	@Override
	public String toString() {
		return "ListWrapper " + unmodifiable + modifiable;
	}
	
	/**
	 * Iterator implementation as inner class
	 * 
	 * @author Eduard_Dereza
	 * 
	 */
	public class IteratorImpl implements Iterator<E> {

		private Iterator<E> iterForUnmodifiable;
		private Iterator<E> iterForModifiable;
		private boolean activeListIsUnmodifiable;

		public IteratorImpl() {
			iterForUnmodifiable = unmodifiable.iterator();
			iterForModifiable = modifiable.iterator();
		}

		@Override
		public boolean hasNext() {
			if (iterForUnmodifiable.hasNext()) {
				activeListIsUnmodifiable = true;
				return true;
			}
			if (iterForModifiable.hasNext()) {
				activeListIsUnmodifiable = false;
				return true;
			}
			return false;
		}

		@Override
		public E next() {
			if (activeListIsUnmodifiable)
				return iterForUnmodifiable.next();
			return iterForModifiable.next();
		}

		@Override
		public void remove() {
			if (activeListIsUnmodifiable)
				throw new UnsupportedOperationException(
						"You cannot modify unmodifiable part of list, modifiable part starts from "
								+ unmodifiable.size() + " index");
			iterForModifiable.remove();
		}

	}

}
