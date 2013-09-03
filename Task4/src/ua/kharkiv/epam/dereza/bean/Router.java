package ua.kharkiv.epam.dereza.bean;

import java.math.BigDecimal;

import ua.kharkiv.epam.dereza.bean.generator.FieldNameMapping;

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
	
	public Router(String model, double weight, int portCount, BigDecimal price, String protocolType, String operationSystem) {
		super(model, weight, portCount, price);
		this.protocolType = protocolType;
		this.operationSystem = operationSystem;
	}
	
	@FieldNameMapping(fieldName="protocol.type")
	public String getProtocolType() {
		return protocolType;
	}
	
	@FieldNameMapping(fieldName="protocol.type")
	public void setProtocolType(String protocolType) {
		this.protocolType = protocolType;
	}
	
	@FieldNameMapping(fieldName="operation.system")
	public String getOperationSystem() {
		return operationSystem;
	}
	
	@FieldNameMapping(fieldName="operation.system")
	public void setOperationSystem(String operationSystem) {
		this.operationSystem = operationSystem;
	}
		
	@Override
	public String toString() {
		return super.toString() + ", protocolType=" + protocolType + ", operationSystem="
				+ operationSystem;
	}
	
}
