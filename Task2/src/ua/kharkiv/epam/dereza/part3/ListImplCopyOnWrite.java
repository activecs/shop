package ua.kharkiv.epam.dereza.part3;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * 
 * @author Eduard_Dereza
 *
 * @param <E>
 */
public class ListImplCopyOnWrite<E> implements List<E>, Cloneable {

	private int capacity;
	private int size = 0;
	private E elementData[];
	

	public ListImplCopyOnWrite() {
		this(10);
	}

	public ListImplCopyOnWrite(int capacity) {
		if (capacity < 0)
			throw new IllegalArgumentException("Illegal Capacity: " + capacity);
		this.capacity = capacity;
		elementData = (E[]) new Object[capacity];
	}

	private void ensureCapacity(int value) {
		E tempElementData[];
		if (value > capacity) {
			capacity = (capacity * 3) / 2 + 1;
			tempElementData = (E[]) new Object[capacity];
			System.arraycopy(elementData, 0, tempElementData, 0,
					elementData.length);
			elementData = tempElementData;
		}
	}
	
	private void copyOnWrite(){
		E tempElementData[] = (E[]) new Object[capacity];
		System.arraycopy(elementData, 0, tempElementData, 0, elementData.length);
		elementData = tempElementData;
	}
	
	@Override
	public boolean add(E element) {
		copyOnWrite();
		ensureCapacity(size + 1);
		elementData[size++] = element;
		return true;
	}

	@Override
	public void add(int index, E element) {
		copyOnWrite();
		ensureCapacity(size + 1);
		System.arraycopy(elementData, index, elementData, index + 1, size
				- index);
		elementData[index] = element;
		size++;
	}

	@Override
	public boolean addAll(Collection<? extends E> list) {
		if (this == list)
			throw new IllegalArgumentException();
		if (list.isEmpty())
			return false;
		copyOnWrite();
		int length = list.size();
		ensureCapacity(size + length);
		System.arraycopy(list.toArray(), 0, elementData, size, list.size());
		size += length;
		return true;
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> list) {
		E tempElementData[];
		if (index > size)
			throw new IndexOutOfBoundsException();
		if (size == 0)
			addAll(list);
		if (this == list)
			throw new IllegalArgumentException();
		if (list.isEmpty())
			return false;
		copyOnWrite();
		int length = list.size();
		ensureCapacity(size + length);

		System.arraycopy(elementData, index, elementData, index + length, size
				- index);
		System.arraycopy((E[]) list.toArray(), 0, elementData, index, length);

		size += length;
		return true;
	}

	@Override
	public void clear() {
		copyOnWrite();
		Arrays.fill(elementData, null);
		size = 0;
	}

	@Override
	public boolean contains(Object o) {
		E element = (E) o;
		for (int i = 0; i < size; i++) {
			if (element == null) {
				if (elementData[i] == null)
					return true;
			} else {
				if (element.equals(elementData[i])) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> list) {
		for (Object element : list) {
			if (!contains(element))
				return false;
		}
		return true;
	}

	@Override
	public E get(int index) {
		if (index < 0 || index >= size)
			throw new IndexOutOfBoundsException();
		return elementData[index];
	}

	@Override
	public int indexOf(Object o) {
		for (int index = 0; index < size; index++) {
			if (o.equals(elementData[index]))
				return index;
		}
		return -1;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public Iterator<E> iterator() {
		return new IteratorImpl();
	}

	@Override
	public int lastIndexOf(Object o) {
		for (int index = size - 1; index >= 0; index--) {
			if (o.equals(elementData[index]))
				return index;
		}
		return -1;
	}

	@Override
	public boolean remove(Object o) {
		copyOnWrite();
		int index = indexOf(o);
		if (index == -1)
			return false;
		System.arraycopy(elementData, index + 1, elementData, index, size
				- index);
		size--;
		return true;
	}

	@Override
	public E remove(int index) {
		copyOnWrite();
		E item = get(index);
		System.arraycopy(elementData, index + 1, elementData, index, size
				- index);
		return item;
	}

	@Override
	public boolean removeAll(Collection<?> list) {
		copyOnWrite();
		if (list == null)
			throw new NullPointerException();
		boolean flag = false;
		for (Object obj : list) {
			if (remove((E) obj) == true)
				flag = true;
		}
		return flag;
	}

	@Override
	public boolean retainAll(Collection<?> list) {
		copyOnWrite();
		boolean flag = false;
		for (int i = 0; i < size; i++) {
			if (!list.contains(elementData[i])) {
				if (flag || (flag = remove(elementData[i])))
					remove(elementData[i]);
			}
		}
		return flag;
	}

	@Override
	public E set(int index, E element) {
		copyOnWrite();
		if (index < 0 || index >= size())
			throw new IndexOutOfBoundsException();
		E replacedElement = elementData[index];
		elementData[index] = element;
		return replacedElement;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		return Arrays.asList(Arrays
				.copyOfRange(elementData, fromIndex, toIndex));
	}

	@Override
	public Object[] toArray() {
		return Arrays.copyOf(elementData, size);
	}

	@Override
	public <T> T[] toArray(T[] a) {
		if (a.length < size)
			return (T[]) Arrays.copyOf(elementData, size, a.getClass());
		System.arraycopy(elementData, 0, a, 0, size);
		if (a.length > size)
			a[size] = null;
		return a;
	}

	@Override
	public ListIterator<E> listIterator() {
		if (true)
			throw new UnsupportedOperationException();
		return null;
	}

	@Override
	public ListIterator<E> listIterator(int index) {
		if (true)
			throw new UnsupportedOperationException();
		return null;
	}

	@Override
	public String toString() {
		return "ListImpl = " + Arrays.toString(elementData);
	}
	
	public class IteratorImpl implements Iterator<E> {

		private int currentIndex = -1;
		private boolean nextFlag = false;
		private int snapshotSize;
		private E snapshot[];		
		
		public IteratorImpl() {
			snapshotSize = size;
			snapshot = elementData;
		}

		@Override
		public boolean hasNext() {
			return (currentIndex + 1) < snapshotSize;
		}

		@Override
		public E next() {
			if (!hasNext())
				throw new NoSuchElementException();
			currentIndex++;
			nextFlag = true;
			return (E) snapshot[currentIndex];
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}

	}

}

