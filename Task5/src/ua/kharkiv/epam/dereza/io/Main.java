package ua.kharkiv.epam.dereza.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		int i = 0;
		FileWrapper fw = new FileWrapper(new File("C:\\1.log"));
		Iterator<String> iter1 = fw.iterator();
		Iterator<String> iter2 = fw.iterator();
		
		System.out.println(iter1.next());
		System.out.println(iter2.next());
		
		System.out.println(iter1.next());
		System.out.println(iter2.next());
		

//		LinkedHashMap<Integer, Integer> lastAddedGoods = new LinkedHashMap<Integer, Integer>() {
//			private static final int MAX_ENTRIES = 5;
//
//			protected boolean removeEldestEntry(Map.Entry eldest) {
//				return size() > MAX_ENTRIES;
//			}
//		};
//
//		lastAddedGoods.put(1, 1);
//		lastAddedGoods.put(2, 2);
//		lastAddedGoods.put(3, 3);
//		lastAddedGoods.put(4, 4);
//		lastAddedGoods.put(5, 5);
//		lastAddedGoods.put(6, 6);
//		lastAddedGoods.put(7, 7);
//		lastAddedGoods.put(8, 8);
//		System.out.println(lastAddedGoods.size());
//		
//		for (Map.Entry<Integer, Integer> entry : lastAddedGoods.entrySet()){
//			System.out.println(entry.getKey() + " " + entry.getValue());			
//		}
	}
}
