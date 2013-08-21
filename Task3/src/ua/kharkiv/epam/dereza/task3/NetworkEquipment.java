package ua.kharkiv.epam.dereza.task3;

/**
 *  * 
 * @author Eduard_Dereza
 *
 */
public abstract class NetworkEquipment implements Comparable<NetworkEquipment> {

	private String model;
	private double weight;
	private int portCount;
	
	public NetworkEquipment() {
		super();
	}
	
	public NetworkEquipment(String model, double weight, int portCount) {
		super();
		this.model = model;
		this.weight = weight;
		this.portCount = portCount;
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
	
	@Override
	public int compareTo(NetworkEquipment equip) {
		int result = model.compareTo(equip.model);
		if (result != 0) return (int)(result/Math.abs(result));
		result = (new Integer(portCount)).compareTo(equip.portCount);
		if (result != 0) return (int)(result/Math.abs(result));
		result = (int) Double.valueOf(weight).compareTo(equip.weight);
		if (result != 0) return (int)(result/Math.abs(result));
		return 0;
	}
	
	@Override
	public String toString() {
		return this.getClass().getSimpleName() + " model=" + model + ", weight=" + weight
				+ ", portCount=" + portCount;
	}
	
}
