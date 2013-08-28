package ua.kharkiv.epam.dereza.dao;

import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import ua.kharkiv.epam.dereza.bean.NetworkEquipment;

/**
 * Class for storing orders
 * 
 * @author Eduard_Dereza
 *
 */
public class Orders {

	// main container
	private TreeMap<Date, Map<NetworkEquipment, Integer>> orders;

	public Orders() {
		orders = new TreeMap<Date, Map<NetworkEquipment, Integer>>();
	}
	
	public void addOrder(Map<NetworkEquipment, Integer> order) {
		orders.put(new Date(), order);
	}
	
	/**
	 * 
	 * @param starting date from
	 * @param finishing date to
	 * @return Map of orders made in this time frame(Map<Date, Map<NetworkEquipment, Integer>>)
	 */
	public Map<Date, Map<NetworkEquipment, Integer>> getOrdersInTimeFrame(Date from, Date to) {
		return orders.subMap(from, to);
	}
	
	/**
	 * 
	 * @param desired date - Date 
	 * @return info about nearest order
	 */
	public Entry<Date, Map<NetworkEquipment, Integer>> getNearestOrder(Date date){
		Entry<Date, Map<NetworkEquipment, Integer>> ceilingEntry = orders.ceilingEntry(date);
		Entry<Date, Map<NetworkEquipment, Integer>> floorEntry = orders.floorEntry(date);
		
		if(ceilingEntry == null) return floorEntry;
		if(floorEntry == null) return ceilingEntry;
		
		Date ceilingDate = ceilingEntry.getKey();
		Date floorDate = floorEntry.getKey();
		
		long ceilingDifference = ceilingDate.getTime() - date.getTime();
		long floorDifference = date.getTime() - floorDate.getTime();
		
		if(ceilingDifference < floorDifference) return ceilingEntry;
		return floorEntry;
	}
	
}
