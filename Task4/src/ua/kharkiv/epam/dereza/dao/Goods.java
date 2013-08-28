package ua.kharkiv.epam.dereza.dao;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import ua.kharkiv.epam.dereza.bean.NetworkEquipment;


/**
 * This class is used for storing goods
 * 
 * @author Eduard_Dereza
 *
 */
public class Goods {

	private Map<NetworkEquipment, Integer> goods;

	public Goods() {
		goods = new LinkedHashMap<NetworkEquipment, Integer>();
	}
	
	/**
	 * Return list of goods that you can buy in shop
	 * 
	 * @return List<NetworkEquipment>
	 */
	public List<NetworkEquipment> getListOfAvaliableGoods() {
		return new ArrayList<NetworkEquipment>(goods.keySet());
	}
	
	/**
	 * Allow you to add good to shop
	 * 
	 * @param element
	 */
	public void addGood(NetworkEquipment element) {
		addGood(element, 1);
	}
	
	/**
	 * Allow you to add good to shop
	 * 
	 * @param element
	 * @param count
	 */
	public void addGood(NetworkEquipment element, int count) {
		int avaliableCount;
		if (goods.containsKey(element)) {
			avaliableCount = goods.get(element);
			goods.put(element, (avaliableCount + count));
		}
		goods.put(element, count);
	}
	
	/**
	 * 
	 * @param element
	 * @return count of particular good that shop contain
	 */
	public int getAvaliableCountOfGood(NetworkEquipment element) {
		return goods.get(element);
	}
	
	/**
	 * Moves good from shop
	 * 
	 * @param element
	 */
	public void reduceCountOfGood(NetworkEquipment element) {
		reduceCountOfGood(element, 1);
	}
	
	/**
	 * Moves good from shop
	 * 
	 * @param element
	 * @param count
	 */
	public void reduceCountOfGood(NetworkEquipment element, int count) {
		int avaliableCount = goods.get(element);
		if (avaliableCount < count)
			throw new IllegalArgumentException(
					"Cannot reduce count of products, avaliabe="
							+ avaliableCount + ", desiredCount=" + count);
		goods.put(element, (avaliableCount - count));
	}
	
}
