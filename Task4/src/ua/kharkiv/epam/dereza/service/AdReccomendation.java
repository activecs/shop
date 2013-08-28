package ua.kharkiv.epam.dereza.service;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import ua.kharkiv.epam.dereza.bean.NetworkEquipment;

public class AdReccomendation {
	
	// used for keeping 5 last unique added goods into basket
	private LinkedHashMap<NetworkEquipment, Integer> lastAddedGoods = new LinkedHashMap<NetworkEquipment, Integer>(16, 0.75f, true) {
		private static final int MAX_ENTRIES = 5;

		protected boolean removeEldestEntry(Map.Entry eldest) {
			return size() > MAX_ENTRIES;
		}
	};
		
	public void putGoodToLastAddedGoods(NetworkEquipment element, int count){
		synchronized (lastAddedGoods) {	
			lastAddedGoods.put(element, count);
		}
	}
		
	/**
	 * 
	 * @return 5 recently added goods into basket by any user
	 */
	public Map<NetworkEquipment, Integer> getLastAddedFiveGoods() {
		return Collections.unmodifiableMap(lastAddedGoods);
	}
}
