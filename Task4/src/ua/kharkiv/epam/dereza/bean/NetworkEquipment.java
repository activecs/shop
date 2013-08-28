package ua.kharkiv.epam.dereza.bean;

import java.math.BigDecimal;

/**
 *  * 
 * @author Eduard_Dereza
 *
 */
public abstract class NetworkEquipment implements Comparable<NetworkEquipment> {

	private String model;
	private double weight;
	private int portCount;
	private BigDecimal price;
	
	public NetworkEquipment() {
		super();
	}
	
	public NetworkEquipment(String model, double weight, int portCount, BigDecimal price) {
		super();
		this.model = model;
		this.weight = weight;
		this.portCount = portCount;
		this.price = price;
	}
	
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public int getPortCount() {
		return portCount;
	}
	public void setPortCount(int portCount) {
		this.portCount = portCount;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == this) return true;
		if (!(obj instanceof NetworkEquipment))
			return false;
		NetworkEquipment equipment = (NetworkEquipment) obj;
		return model.equals(equipment.model);
	}
	
	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	@Override
	public int hashCode() {
		int hash = 37;
		hash *= 31 + model.hashCode();
		return hash;
	}
	
	@Override
	public int compareTo(NetworkEquipment equip) {
		int result = model.compareTo(equip.model);
		
		return result;
	}
	
	@Override
	public String toString() {
		return this.getClass().getSimpleName() + " model=" + model + ", price=" + price + ", weight=" + weight
				+ ", portCount=" + portCount;
	}
	
}
