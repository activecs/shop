package ua.kharkiv.epam.dereza.bean.generator;

import java.math.BigDecimal;
import java.util.Scanner;

import ua.kharkiv.epam.dereza.bean.NetworkEquipment;
import ua.kharkiv.epam.dereza.bean.Router;

public class ManualRouterGenerator extends EquipmentGenerator{
	
	private final static String DEVICE_NAME = "Router";
	
	@Override
	public NetworkEquipment generate(String name) {
		if(name.equalsIgnoreCase(DEVICE_NAME)){
			Scanner scanner = new Scanner(System.in);
			
			System.out.println("Enter following fields:");
			System.out.println("model:");
			String model = scanner.next();
			System.out.println("weight:");
			double weight = scanner.nextDouble();
			System.out.println("port count:");
			int portCount = scanner.nextInt();
			System.out.println("price:");
			BigDecimal price = scanner.nextBigDecimal();
			System.out.println("protocolType:");
			String protocolType = scanner.next();
			System.out.println("operation system:");
			String operationSystem = scanner.next();
			
			return new Router(model, weight, portCount, price, protocolType, operationSystem);
		}
		
		if(next == null)
			return null;
		
		return next.generate(name);
	}
	
}
