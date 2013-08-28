package ua.kharkiv.epam.dereza.bean.generator;

import java.math.BigDecimal;
import java.util.Random;

import ua.kharkiv.epam.dereza.bean.NetworkEquipment;
import ua.kharkiv.epam.dereza.bean.WirelessRouter;

public class RandomWirelessRouterGenerator extends EquipmentGenerator{
	
	private final static String DEVICE_NAME = "WirelessRouter";

	@Override
	public NetworkEquipment generate(String name) {
		if(name.equalsIgnoreCase(DEVICE_NAME)){
			Random rand = new Random();
			String model = "model" + rand.nextInt(700);
			double weight = rand.nextDouble() * 5;
			int portCount = rand.nextInt(8);
			BigDecimal price = new BigDecimal(rand.nextInt(1500));
			String protocolType = "protocolType " + rand.nextInt(9);
			String operationSystem = "OS " + rand.nextInt(20);
			boolean dualBand = rand.nextBoolean();
			return new WirelessRouter(model, weight, portCount, price, protocolType, operationSystem, dualBand);
		}
		
		if (next == null)
			return null;

		return next.generate(name);
	}
}
