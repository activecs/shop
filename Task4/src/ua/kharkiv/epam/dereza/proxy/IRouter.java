package ua.kharkiv.epam.dereza.proxy;

import java.math.BigDecimal;

/**
 * Interface for dynamic proxy based on Router
 * 
 * @author Eduard_Dereza
 *
 */
public interface IRouter {
	
	public String getModel();
	
	public void setModel(String model);
	
	public double getWeight();
	
	public void setWeight(double weight);
	
	public int getPortCount();
	
	public void setPortCount(int portCount);
	
	public BigDecimal getPrice();
	
	public void setPrice(BigDecimal price);
	
	public String getProtocolType();
	
	public void setProtocolType(String protocolType);
	
	public String getOperationSystem();
	
	public void setOperationSystem(String operationSystem);
	
}
