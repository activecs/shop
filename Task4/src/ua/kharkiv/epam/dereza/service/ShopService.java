package ua.kharkiv.epam.dereza.service;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import ua.kharkiv.epam.dereza.bean.NetworkEquipment;
import ua.kharkiv.epam.dereza.dao.Basket;
import ua.kharkiv.epam.dereza.dao.Goods;
import ua.kharkiv.epam.dereza.dao.Orders;
import ua.kharkiv.epam.dereza.socket.RestrictedShopService;

/**
 * Contain links on goods, orders, basket
 * Allow to perform transfers between store and basket
 * 
 * @author Eduard_Dereza
 *
 */
public class ShopService implements RestrictedShopService{

	private Basket basket;
	private Goods goods;
	private Orders orders;
	private AdReccomendation adRec;

	public ShopService() {}

	public ShopService(Basket basket, Goods goods, Orders orders, AdReccomendation adRec) {
		super();
		this.basket = basket;
		this.goods = goods;
		this.orders = orders;
		this.adRec = adRec;
	}

	public Basket getBasket() {
		return basket;
	}

	public void setBasket(Basket basket) {
		this.basket = basket;
	}

	public Goods getGoods() {
		return goods;
	}

	public void setGoods(Goods goods) {
		this.goods = goods;
	}

	public Orders getOrders() {
		return orders;
	}

	public void setOrders(Orders orders) {
		this.orders = orders;
	}

	public AdReccomendation getAdRec() {
		return adRec;
	}

	public void setAdRec(AdReccomendation adRec) {
		this.adRec = adRec;
	}

	/**
	 * Checks ability to move good from Goods to basket
	 * 
	 * @param element
	 * @param desiredCount
	 * @return true if Goods contain necessary count of chosen good
	 */
	private boolean canAddToBasket(NetworkEquipment element, int desiredCount) {
		int avaliableCount = goods.getAvaliableCountOfGood(element);
		if (avaliableCount < desiredCount || desiredCount < 1) {
			return false;
		}
		return true;
	}

	/**
	 * Adds good to basket
	 * 
	 * @param element
	 * @param count
	 */
	public void putGoodToBasket(NetworkEquipment element, int count) {
		if (!canAddToBasket(element, count))
			throw new IllegalArgumentException(
					"Unfotunatelly cannot put good to basket");

		basket.putGood(element, count);
		adRec.putGoodToLastAddedGoods(element, count);

		goods.reduceCountOfGood(element, count);
	}

	/**
	 * Make order
	 * 
	 * @param orders
	 * @return bill for goods
	 */
	public BigDecimal checkOut() {
		Map<NetworkEquipment, Integer> basketMap = basket.getGoodsInBasket();
		orders.addOrder(new LinkedHashMap<NetworkEquipment, Integer>(basketMap));

		BigDecimal bill = new BigDecimal(0);
		NetworkEquipment element;
		Integer count;
		for (Entry<NetworkEquipment, Integer> entry : basketMap.entrySet()) {
			element = entry.getKey();
			count = entry.getValue();

			bill = bill.add(element.getPrice().multiply(new BigDecimal(count)));
		}
		basket.clear();
		return bill;
	}

	@Override
	public int countGoodInShop(){
		int totalCount = 0;
		for(NetworkEquipment good : goods.getListOfAvaliableGoods()){
			totalCount += goods.getAvaliableCountOfGood(good);
		}
		return totalCount;
	}

	@Override
	public NetworkEquipment foundGoodByModel(String model){
		NetworkEquipment foundGood = null;
		List<NetworkEquipment> allGoods =  goods.getListOfAvaliableGoods();
		for(NetworkEquipment equip : allGoods){
			if(equip.getModel().equalsIgnoreCase(model)){
				foundGood = equip;
				break;
			}
		}
		return foundGood;
	}
}
