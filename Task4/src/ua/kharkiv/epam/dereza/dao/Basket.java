package ua.kharkiv.epam.dereza.dao;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import ua.kharkiv.epam.dereza.bean.NetworkEquipment;

/**
 * User's basket class. Used for keeping goods that might will be bought
 * 
 * @author Eduard_Dereza
 * 
 */
public class Basket {

	// main container
	private LinkedHashMap<NetworkEquipment, Integer> basket;

	public Basket() {
		basket = new LinkedHashMap<NetworkEquipment, Integer>();
	}

	/**
	 * Return map of goods that basket contains and their count
	 * 
	 * @return Map<NetworkEquipment, Integer>
	 */
	public Map<NetworkEquipment, Integer> getGoodsInBasket() {
		return Collections.unmodifiableMap(basket);
		 
	}

	/**
	 * Adds good to basket
	 * 
	 * @param element
	 * @param count
	 */
	public void putGood(NetworkEquipment element, int count) {
		Integer goodCount = basket.get(element);
		if (goodCount == null){
			goodCount = 0;
		}
		
		basket.put(element, (goodCount + count));
	}
	
	/**
	 * Allow to clear basket
	 * 
	 */
	public void clear(){
		basket.clear();
	}
	
	
}
