package ua.kharkiv.epam.dereza.bean;

import java.math.BigDecimal;

import ua.kharkiv.epam.dereza.bean.generator.FieldNameMapping;

/**
 * 
 * @author Eduard_Dereza
 *
 */
public class WirelessRouter extends Router{
	
	private boolean dualBand;
	
	public WirelessRouter() {
		super();
	}
	
	public WirelessRouter(String model, double weight, int portCount, BigDecimal price,
			String protocolType, String operationSystem, boolean dualBand) {
		super(model, weight, portCount, price, protocolType, operationSystem);
		this.dualBand = dualBand;
	}
	
	@FieldNameMapping(fieldName="dual.band")
	public boolean isDualBand() {
		return dualBand;
	}
	
	@FieldNameMapping(fieldName="dual.band")
	public void setDualBand(boolean dualBand) {
		this.dualBand = dualBand;
	}

	@Override
	public String toString() {
		return super.toString() + ", dualBand=" + dualBand;
	}			
}
