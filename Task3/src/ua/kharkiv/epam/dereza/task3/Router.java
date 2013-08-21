package ua.kharkiv.epam.dereza.task3;

/**
 * 
 * @author Eduard_Dereza
 *
 */
public class Router extends NetworkEquipment {
	
	private String protocolType;
	private String operationSystem;
	
	public Router() {
		super();
	}
	
	public Router(String model, double weight, int portCount, String protocolType, String operationSystem) {
		super(model, weight, portCount);
		this.protocolType = protocolType;
		this.operationSystem = operationSystem;
	}

	public String getProtocolType() {
		return protocolType;
	}

	public void setProtocolType(String protocolType) {
		this.protocolType = protocolType;
	}

	public String getOperationSystem() {
		return operationSystem;
	}

	public void setOperationSystem(String operationSystem) {
		this.operationSystem = operationSystem;
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}
		
	@Override
	public String toString() {
		return super.toString() + ", protocolType=" + protocolType + ", operationSystem="
				+ operationSystem;
	}
	
}
