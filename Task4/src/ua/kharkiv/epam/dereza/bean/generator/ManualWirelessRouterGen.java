package ua.kharkiv.epam.dereza.bean.generator;

import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

import ua.kharkiv.epam.dereza.bean.NetworkEquipment;
import ua.kharkiv.epam.dereza.bean.WirelessRouter;
import ua.kharkiv.epam.dereza.service.Main;

/**
 * Full name of the class is manual wireless router generator. It is chain of
 * responsibility pattern implementation.
 * 
 * @author Eduard_Dereza
 * 
 */
public class ManualWirelessRouterGen extends EquipmentGenerator {

	private final static String DEVICE_NAME = "WirelessRouter";
	private Locale locale;

	public ManualWirelessRouterGen(Locale locale) {
		this.locale = locale;
	}

	@Override
	public NetworkEquipment generate(String name) {
		if (name.equalsIgnoreCase(DEVICE_NAME)) {
			ResourceBundle bundle = ResourceBundle.getBundle(Main.basename,
					locale);
			boolean repeat = false;
			Scanner scanner = null;
			do {
				scanner = new Scanner(System.in);
				try {
					System.out.println(bundle.getString("welcome.message"));
					System.out.println(bundle.getString("model"));
					String model = scanner.next();
					System.out.println(bundle.getString("weight"));
					double weight = scanner.nextDouble();

					System.out.println(bundle.getString("port.count"));
					int portCount = scanner.nextInt();

					System.out.println(bundle.getString("price"));
					BigDecimal price = scanner.nextBigDecimal();

					System.out.println(bundle.getString("protocol.type"));
					String protocolType = scanner.next();

					System.out.println(bundle.getString("operation.system"));
					String operationSystem = scanner.next();

					System.out.println("dual.band");
					boolean dualBand = scanner.nextBoolean();

					return new WirelessRouter(model, weight, portCount, price,
							protocolType, operationSystem, dualBand);
				} catch (InputMismatchException e) {
					System.out.println("Would you like to repeat input");
					System.out.println("1 - yes");
					System.out.println("2 - no");
					scanner = new Scanner(System.in);
					if (!scanner.next().equals("1")) {
						throw e;
					} else {
						repeat = true;
					}
				}
			} while (repeat);
		}

		if (next == null)
			return null;

		return next.generate(name);
	}

}
