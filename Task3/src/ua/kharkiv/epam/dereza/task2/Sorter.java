package ua.kharkiv.epam.dereza.task2;

import java.util.Comparator;

import ua.kharkiv.epam.dereza.task3.NetworkEquipment;

public class Sorter {
	
	/**
	 * Sorts NetworkEquipment by model
	 */
	public static final Comparator<NetworkEquipment> sortNetworkEquipmentByModel = new Comparator<NetworkEquipment>() {
		@Override
		public int compare(NetworkEquipment equip0, NetworkEquipment equip1) {
			return equip0.getModel().compareTo(equip1.getModel());
		}
	};
	
	/**
	 * Sorts NetworkEquipment by portCount
	 */
	public static final Comparator<NetworkEquipment> sortNetworkEquipmentByPortCount = new Comparator<NetworkEquipment>() {
		@Override
		public int compare(NetworkEquipment equip0, NetworkEquipment equip1) {
			if (equip0.getPortCount() < equip1.getPortCount())
				return -1;
			if (equip0.getPortCount() > equip1.getPortCount())
				return 1;
			return 0;
		}
	};
	
	/**
	 * Sorts NetworkEquipment by weight
	 */
	public static final Comparator<NetworkEquipment> sortNetworkEquipmentByWeight = new Comparator<NetworkEquipment>() {
		@Override
		public int compare(NetworkEquipment equip0, NetworkEquipment equip1) {
			if (equip0.getWeight() < equip1.getWeight())
				return -1;
			if (equip0.getWeight() > equip1.getWeight())
				return 1;
			return 0;
		}
	};
	
}
