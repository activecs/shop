package ua.kharkiv.epam.dereza.part0;

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
	
	public WirelessRouter(String model, double weight, int portCount,
			String protocolType, String operationSystem, boolean dualBand) {
		super(model, weight, portCount, protocolType, operationSystem);
		this.dualBand = dualBand;
	}
	
	public boolean isDualBand() {
		return dualBand;
	}

	public void setDualBand(boolean dualBand) {
		this.dualBand = dualBand;
	}

	@Override
	public String toString() {
		return super.toString() + ", dualBand=" + dualBand;
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
		}
			
}
