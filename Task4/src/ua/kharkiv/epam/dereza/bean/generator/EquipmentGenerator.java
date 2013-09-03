package ua.kharkiv.epam.dereza.bean.generator;

import ua.kharkiv.epam.dereza.bean.NetworkEquipment;

/**
 * Basic interface for equipment generating
 * 
 * @author Eduard
 *
 */
public abstract class EquipmentGenerator {
	
	protected EquipmentGenerator next;
	
	public void setNext(EquipmentGenerator next) {
		this.next = next;
	}
	
	public abstract NetworkEquipment generate(String name);

}
