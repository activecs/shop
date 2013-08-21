package ua.kharkiv.epam.dereza.task3;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Container {
	
	public static final void main(String[] args){
		Map<Article, NetworkEquipment> hashMap = new HashMap<Article, NetworkEquipment>();
		Map<Article, NetworkEquipment> linkedHashMap = new LinkedHashMap<Article, NetworkEquipment>();
		
		hashMap.put(new Article("001"), new Router("642", 1., 4, "a/b/g/n", "noOS"));
		hashMap.put(new Article("02"), new Router("643", 2., 4, "b/g/n", "noOS"));
		hashMap.put(new Article("002"), new Router("643", 2., 4, "b/g/n", "noOS"));
		hashMap.put(new Article("00001"), new Router("642", 1., 4, "a/b/g/n", "noOS"));
		
		System.out.println("\n----- HashMap, Hashcode from Article ------");
		for(Entry<Article, NetworkEquipment> entry : hashMap.entrySet()){
			System.out.println("hashcode " + entry.getKey().hashCode() + ",key, " + entry.getKey() + ", value " + entry.getValue());
		}
		
		hashMap.clear();
		hashMap.put(new ArticleBetter("001"), new Router("642", 1., 4, "a/b/g/n", "noOS"));
		hashMap.put(new ArticleBetter("02"), new Router("643", 2., 4, "b/g/n", "noOS"));
		hashMap.put(new ArticleBetter("002"), new Router("643", 2., 4, "b/g/n", "noOS"));
		hashMap.put(new ArticleBetter("00001"), new Router("642", 1., 4, "a/b/g/n", "noOS"));
		
		System.out.println("\n----- HashMap, Hashcode from ArticleBetter ------");
		for(Entry<Article, NetworkEquipment> entry : hashMap.entrySet()){
			System.out.println("hashcode " + entry.getKey().hashCode() + ",key, " + entry.getKey() + ", value " + entry.getValue());
		}
		
		linkedHashMap.put(new Article("001"), new Router("642", 1., 4, "a/b/g/n", "noOS"));
		linkedHashMap.put(new Article("02"), new Router("643", 2., 4, "b/g/n", "noOS"));
		linkedHashMap.put(new Article("002"), new Router("643", 2., 4, "b/g/n", "noOS"));
		linkedHashMap.put(new Article("00001"), new Router("642", 1., 4, "a/b/g/n", "noOS"));
		
		System.out.println("\n----- LinkedHashMap, Hashcode from Article ------");
		for(Entry<Article, NetworkEquipment> entry : linkedHashMap.entrySet()){
			System.out.println("hashcode " + entry.getKey().hashCode() + ",key, " + entry.getKey() + ", value " + entry.getValue());
		}
		
		linkedHashMap.clear();
		linkedHashMap.put(new ArticleBetter("001"), new Router("642", 1., 4, "a/b/g/n", "noOS"));
		linkedHashMap.put(new ArticleBetter("02"), new Router("643", 2., 4, "b/g/n", "noOS"));
		linkedHashMap.put(new ArticleBetter("002"), new Router("643", 2., 4, "b/g/n", "noOS"));
		linkedHashMap.put(new ArticleBetter("00001"), new Router("642", 1., 4, "a/b/g/n", "noOS"));
		
		System.out.println("\n----- LinkedHashMap, Hashcode from ArticleBetter ------");
		for(Entry<Article, NetworkEquipment> entry : linkedHashMap.entrySet()){
			System.out.println("hashcode " + entry.getKey().hashCode() + ",key, " + entry.getKey() + ", value " + entry.getValue());
		}
	}
}
