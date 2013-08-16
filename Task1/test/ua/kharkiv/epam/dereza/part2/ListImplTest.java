package ua.kharkiv.epam.dereza.part2;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ua.kharkiv.epam.dereza.part1.WirelessRouter;

public class ListImplTest {
	
	private List<Integer> list;
	
	@Before
	public void before(){
		list = new ListImpl<Integer>();
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testCreateInstance(){
		list = new ListImpl<Integer>(-10);
	}
	
	@Test
	public void testAbilityToExpand() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
		for(int i = 0; i < 10; i++){
			list.add(i);
		}
		Field internalArray = ListImpl.class.getDeclaredField("elementData");
		internalArray.setAccessible(true);
		Integer lengthBeforeExpand = ((Object[])internalArray.get(list)).length;
		list.add(10);
		Integer lengthAfterExpand = ((Object[])internalArray.get(list)).length;
		assertNotSame("Internal array must expands according to expression oldCapacity*3/2+1, where starting capacity = 10",
				lengthBeforeExpand, lengthAfterExpand);
	}
	
	@Test
	public void testAddInPosition(){
		list.add(new Integer(0));
		list.add(new Integer(1));
		list.add(new Integer(2));
		list.add(new Integer(3));
		list.add(new Integer(4));
		list.add(2, new Integer(7));
		ArrayList<Integer> list2 = new ArrayList<Integer>();
		list2.add(new Integer(0));
		list2.add(new Integer(1));
		list2.add(new Integer(7));
		list2.add(new Integer(2));
		list2.add(new Integer(3));
		list2.add(new Integer(4));
		assertArrayEquals(list.toArray(),list2.toArray());
	}
	
	@Test
	public void testAddNull(){
		assertTrue("List must have oppotunity to contain null", list.add(null));
	}
	
	@Test
	public void testAddAll(){
		Integer array[] = {1,2,3,4,5,6,7,8,9,0,11};
		list.addAll(Arrays.asList(array));
		assertEquals("Arrays must be equals ",Arrays.toString(array), Arrays.toString(list.toArray()));
	}
	
	@Test
	public void testAddAllInPosition(){
		list.add(new Integer(0));
		list.add(new Integer(1));
		list.add(new Integer(5));
		Integer array[] = {2,3,4};
		Integer array2[] = {0,1,2,3,4,5};
		list.addAll(2, Arrays.asList(array));
		assertEquals("Arrays must be equals ",Arrays.toString(array2), Arrays.toString(list.toArray()));
	}
	
	@Test
	public void testAddAllEmptyList(){
		assertFalse(list.addAll(new ArrayList<Integer>()));
	}
	
	@Test
	public void testLasIndexOf(){
		list.add(new Integer(0));
		list.add(new Integer(1));
		list.add(new Integer(2));
		list.add(new Integer(1));
		list.add(new Integer(3));
		assertEquals(3, list.lastIndexOf(new Integer(1)));
		assertEquals(-1, list.lastIndexOf(new Integer(4)));
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void testGet(){
		list.add(1);
		list.add(2);
		list.add(3);
		assertEquals(new Integer(1),(list.get(0)));
		list.get(3);
	}
	
	@Test
	public void testSet(){
		list.add(1);
		list.add(2);
		list.add(3);
		assertEquals(new Integer(1),(list.get(0)));
		list.set(0, new Integer(5));
		assertEquals(new Integer(5),(list.get(0)));
	}
	
	@Test
	public void testClear(){
		list.add(1);
		list.add(2);
		list.add(3);
		assertFalse(list.isEmpty());
		list.clear();
		assertTrue(list.isEmpty());
	}
	
	@Test(expected = NullPointerException.class)
	public void testContainsAllNull(){
		list.containsAll(null);
	}
	
	@Test
	public void testContainNull(){
		list.add(90);
		assertFalse(list.contains(null));
		list.add(null);
		assertTrue(list.contains(null));
		list.add(1);
		list.add(null);
		list.add(3);
		assertTrue(list.contains(null));
	}
	
	@Test
	public void testContainAll(){
		List<Integer> listForAdd = new ListImpl<Integer>();
		listForAdd.add(1);
		listForAdd.add(2);
		listForAdd.add(3);
		listForAdd.add(4);
		
		list.addAll(listForAdd);
		list.add(5);
		assertTrue(list.containsAll(listForAdd));
		list.remove(2);
		assertFalse(list.containsAll(listForAdd));
	}
	
	@Test
	public void testRemove(){
		list.add(7);
		list.add(null);
		assertFalse(list.remove(new Integer(5)));
	}
	
	@Test
	public void testRemoveAll(){
		list.add(1);
		list.add(2);
		ArrayList<Integer> list2 = new ArrayList<Integer>();
		list2.add(3);
		assertFalse("You can't remove unexisting object",list.removeAll(list2));
		list2.add(1);
		assertTrue("Existing object must be removed",list.removeAll(list2));
	}
	
	@Test(expected = NullPointerException.class)
	public void testRemoveAllNull(){
		list.removeAll(null);
	}
	
	@Test
	public void testSize(){
		assertEquals(0,list.size());
		list.add(1);
		assertEquals(1,list.size());
	}
	
	@Test
	public void testIteratorNext(){
		list.add(1);
		list.add(2);
		list.add(3);
		Iterator<Integer> iter = list.iterator();
		assertTrue("Expected true, got false", iter.hasNext());
		assertEquals("Expected 1",new Integer(1),iter.next());
	}
	
	@Test(expected = IllegalStateException.class)
	public void testIteratorRemove(){
		list.add(1);
		list.add(2);
		list.add(3);
		Iterator<Integer> iter = list.iterator();
		iter.remove();
	}
	
	@Test(expected = IllegalStateException.class)
	public void testIteratorRemoveTwice(){
		list.add(1);
		list.add(2);
		list.add(3);
		Iterator<Integer> iter = list.iterator();
		iter.hasNext();
		iter.next();
		iter.remove();
		iter.remove();
	}
	
	@Test(expected = UnsupportedOperationException.class)
	public void testListIterator(){
		list.add(new Integer(1));
		list.listIterator();
	}
	
	@Test(expected = UnsupportedOperationException.class)
	public void testListIteratorWithIndex(){
		list.add(new Integer(1));
		list.listIterator(1);
	}
	
	@Test
	public void testCustomIterator() throws CloneNotSupportedException{
		ListImpl<WirelessRouter> list2 = new ListImpl<WirelessRouter>();
		WirelessRouter router1 = new WirelessRouter("wr642", 0.23, 5, "a/b/g", "noOS", false);
		WirelessRouter router2 = new WirelessRouter("wr643", 0.1, 3, "a/b/g/n", "noOS", false);
		WirelessRouter router3 = new WirelessRouter("wr644", 0.5, 8, "b/g/n", "noOS", false);
		WirelessRouter router4 = new WirelessRouter("wr645", 0.6, 5, "a/b/g/n/ac", "noOS", true);
		
		list2.add(router1);
		list2.add(router2);
		list2.add(router3);
		list2.add(router4);
		
		Iterator<WirelessRouter> iter = list2.customIterator(5, 0.4);
		
		assertTrue(iter.hasNext());
		assertEquals(router1, iter.next());
		assertFalse(iter.hasNext());
	}
}
