package ua.kharkiv.epam.dereza.socket;

import ua.kharkiv.epam.dereza.bean.NetworkEquipment;

/**
 * Interface that restrict number of available method from ShopService for
 * Socket usage(SocketClient).
 * 
 * Changed after previous code review.
 * 
 * @author Eduard_Dereza
 * 
 */
public interface RestrictedShopService {

	/**
	 * Count all goods in shop
	 * 
	 * @return total count
	 */
	public int countGoodInShop();

	/**
	 * Allow you to find necessary {@link NetworkEquipment} by model
	 * 
	 * @param model
	 * @return NetworkEquipment or null if equipment wasn't found
	 */
	public NetworkEquipment foundGoodByModel(String model);
}
