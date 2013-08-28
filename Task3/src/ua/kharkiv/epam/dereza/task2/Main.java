package ua.kharkiv.epam.dereza.task2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		List<Integer> list1 = new ArrayList<Integer>();
		List<Integer> list2 = new ArrayList<Integer>();
		
		list1.add(0);
		list1.add(1);
		list1.add(2);
		list1.add(3);
		
		list2.add(4);
		list2.add(5);
		list2.add(3);
		list2.add(7);
		
		ListWrapper<Integer> list = new ListWrapper<Integer>(list1, list2);
		
//		list.add(8);
//		System.out.println(list);
		
//		list.add(4, 12);
//		System.out.println(list);
		
		//list.addAll(4, Arrays.asList(new Integer[]{77,78,79}));
		System.out.println(list);
		
//		System.out.println(list.indexOf(new Integer(0)));
//		System.out.println(list.indexOf(new Integer(3)));
//		System.out.println(list.indexOf(new Integer(77)));
		System.out.println(list.lastIndexOf(new Integer(3)));
		System.out.println(list.lastIndexOf(new Integer(1)));
		
		//list.retainAll(list2);
		list.retainAll(Arrays.asList(new Integer[]{0,2,3,4}));
		//list.retainAll(Arrays.asList(new Integer[]{77,78,79}));
		System.out.println(list);
		
		/*System.out.println("--------------------------------------");
		ArrayList<Integer> list5 = new ArrayList<Integer>();
		list5.addAll(Arrays.asList(new Integer[]{0,1,2,3,4,5,6,7}));
		System.out.println(list5);
		list5.retainAll(Arrays.asList(new Integer[]{0,3,1,2}));
		System.out.println(list5);*/
		
//		list.set(5, 99);
//		System.out.println(list);
	}

}
