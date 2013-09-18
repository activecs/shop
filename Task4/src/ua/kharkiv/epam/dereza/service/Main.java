package ua.kharkiv.epam.dereza.service;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.Scanner;

import ua.kharkiv.epam.dereza.bean.NetworkEquipment;
import ua.kharkiv.epam.dereza.bean.Router;
import ua.kharkiv.epam.dereza.bean.WirelessRouter;
import ua.kharkiv.epam.dereza.bean.generator.*;
import ua.kharkiv.epam.dereza.dao.*;
import ua.kharkiv.epam.dereza.socket.*;
import ua.kharkiv.epam.dereza.util.Utility;

/**
 * Entry point of app
 * 
 * @author Eduard_Dereza
 *
 */
public class Main {

	public static final String basename = "messages";

	public static Locale[] getAvaliableLocales(){
		return new Locale[]{new Locale("en","US"), new Locale("ru","RU")};
	}

	public static void main(String[] args) {
		Map<String, Class> classes = new HashMap<String, Class>();
		classes.put("Router", ua.kharkiv.epam.dereza.bean.Router.class);
		classes.put("WirelessRouter", ua.kharkiv.epam.dereza.bean.WirelessRouter.class);

		Currency currency = Currency.getInstance("UAH");
		EquipmentGenerator generator = null;
		ShopService service = new ShopService(new Basket(), new Goods(),
				new Orders(), new AdReccomendation());

		init(service.getGoods());

		// starts socketServer
		//HandlerFactory factory = new ClientHandlerFactory(service);
		HandlerFactory factory = new TcpHandlerFactory();
		new SocketServer(factory);

		// Console menu
		Scanner scanner = new Scanner(System.in);
		Locale locale = chooseLocale(scanner);
		boolean flag = true;
		while (flag) {
			scanner = new Scanner(System.in);

			printMenu();

			System.out.println("Selection: ");
			int selection = scanner.nextInt();

			switch (selection) {
			case 1: {
				showAvaliableGoods(service);
				break;
			}

			case 2: {
				addGoodToBasket(service, scanner);
				break;
			}

			case 3: {
				lookIntoBasket(service);
				break;
			}

			case 4: {
				checkout(service, currency, locale);
				break;
			}

			case 5: {
				showLastAddedFiveGoods(service);
				break;
			}

			case 6: {
				showOrdersInTimeFrame(service, scanner);
				break;
			}

			case 7: {
				showNearestOrder(service, scanner);
				break;
			}

			case 8: {
				while (generator == null){
					ResourceBundle bundle = ResourceBundle.getBundle(basename, locale);
					System.out.println(bundle.getString("which.approach.would.you.like.to.choose"));
					System.out.println("1 - " + bundle.getString("random"));
					System.out.println("2 - " + bundle.getString("manual"));
					System.out.println("3 - " + bundle.getString("random.with.reflection"));
					System.out.println("4 - " + bundle.getString("manual.with.reflection"));

					int generatorType = scanner.nextInt();
					if(generatorType == 1){
						generator = new RandRouterGen();
						generator.setNext(new RandWirelessRouterGen());
					}
					if(generatorType == 2){
						generator = new ManualRouterGen(locale);
						generator.setNext(new ManualWirelessRouterGen(locale));
					}
					if(generatorType == 3){
						generator = new ReflectionRandomGenerator(classes);
					}
					if(generatorType == 4){
						generator = new ReflectionManualGenerator(classes, locale);
					}
				}
				addNewGood(service, scanner, generator, locale);
				break;
			}

			case 9: {
				flag = false; // Exit
				break;
			}

			default: {
				System.out.println("Please enter a valid selection.");
			}
			}
		}// menu cycle
		scanner.close();
	}

	public static void init(Goods goods) {
		goods.addGood(new Router("wr643", 0.7, 11, new BigDecimal(200), "a/b/g/n", "noOS"), 4);
		goods.addGood(new Router("wr642", 0.3, 8, new BigDecimal(230), "b/g/n", "noOS"), 3);
		goods.addGood(new Router("wr644", 0.2, 5, new BigDecimal(259), "a/b/g/n", "noOS"), 5);
		goods.addGood(new Router("wr645", 1.5, 1, new BigDecimal(320), "a/b/g/n", "noOS"), 2);
		goods.addGood(new Router("wr647", 2.3, 1, new BigDecimal(546), "a/b/g/n", "noOS"));
		goods.addGood(new Router("wr646", 0.1, 4, new BigDecimal(870), "g/n/a/c", "noOS"));
		goods.addGood(new Router("wr649", 0.6, 8, new BigDecimal(211), "g/n",	"noOS"), 23);
		goods.addGood(new Router("wr648", 0.4, 16, new BigDecimal(328), "a/b/g", "noOS"));
		goods.addGood(new Router("wr650", 1.7, 12, new BigDecimal(832), "a/b/n", "noOS"));
		goods.addGood(new WirelessRouter("wr651", 1.7, 12, new BigDecimal(1262), "a/b/n", "noOS", true), 7);
		goods.addGood(new WirelessRouter("wr652", 2.4, 8, new BigDecimal(1391), "a/b/n", "noOS", true));
		goods.addGood(new WirelessRouter("wr621", 3.7, 16, new BigDecimal(999), "a/b/n", "noOS", false), 3);
	}

	public static Locale chooseLocale(Scanner scanner){
		System.out.println("Please choose locale :");
		Locale[] locales = getAvaliableLocales();
		for(int i=0; i<locales.length; i++){
			System.out.println( i + " " + locales[i]);
		}
		int chosenLocale = scanner.nextInt();

		return locales[chosenLocale];
	}

	public static void printMenu(){
		System.out.println("\nPlease Make a selection:");
		System.out.println("[1]Print avaliable list of goods");
		System.out.println("[2]Add good to basket");
		System.out.println("[3]Look into basket");
		System.out.println("[4]Check out (Make order)");
		System.out.println("[5]Look at 5 recently added goods into basket");
		System.out.println("[6]Find list of orders in time frame");
		System.out.println("[7]Find nearest order");
		System.out.println("[8]Add new good");
		System.out.println("[9]Exit");
	}

	public static void showAvaliableGoods(ShopService service){
		System.out.println("\nGoods in our shop:");
		List<NetworkEquipment> avaliableGoods = service.getGoods().getListOfAvaliableGoods();
		for (int i = 0; i < avaliableGoods.size(); i++) {
			System.out.println("[" + i + "] " + avaliableGoods.get(i));
		}
	}

	public static void addGoodToBasket(ShopService service, Scanner scanner){
		List<NetworkEquipment> avaliableGoods = service.getGoods().getListOfAvaliableGoods();

		System.out.println("Input the number of good:");
		try{
			int numberOfGood = Utility.readIntInRange(0, avaliableGoods.size(), scanner);

			int avaliableCount = service.getGoods().getAvaliableCountOfGood(avaliableGoods.get(numberOfGood));
			System.out.println("There are " + avaliableCount + " items of good");

			if (avaliableCount != 0) {
				System.out.println("Input desired count of good:");
				int desiredCountOfGood = 0;
				desiredCountOfGood = Utility.readIntInRange(0, avaliableCount, scanner);
				service.putGoodToBasket(avaliableGoods.get(numberOfGood), desiredCountOfGood);
				System.out.println("Good was added");
			}
		}catch(IllegalArgumentException e){
			System.out.println("Please repeat selection");
		}

	}

	public static void lookIntoBasket(ShopService service){
		System.out.println("\nMy basket contains:");
		Map<NetworkEquipment, Integer> goodsInBasket = service.getBasket().getGoodsInBasket();
		for(Map.Entry<NetworkEquipment, Integer> entry : goodsInBasket.entrySet()){
			System.out.println("Good :" + entry.getKey() + ", count :" + entry.getValue());
		}
	}

	public static void checkout(ShopService service, Currency currency, Locale locale){
		ResourceBundle bundle = ResourceBundle.getBundle(basename, locale);
		NumberFormat nf = NumberFormat.getCurrencyInstance(locale);
		nf.setCurrency(currency);
		String bill = nf.format(service.checkOut().doubleValue());
		System.out.println("\n" + bundle.getString("your.bill") + " " + bill);
	}

	public static void showLastAddedFiveGoods(ShopService service){
		System.out.println("\n Last added:");
		for(Map.Entry<NetworkEquipment, Integer> entry : service.getAdRec().getLastAddedFiveGoods().entrySet()){
			System.out.println("Equipment :" + entry.getKey() + ", count :" + entry.getValue());
		}
	}

	public static void showOrdersInTimeFrame(ShopService service, Scanner scanner){
		System.out.println("Input date in next format - yyyy-MM-dd HH:mm");
		System.out.println("From :");
		String strFrom = scanner.next();
		System.out.println("To :");
		String strTo = scanner.next();
		Date from = Utility.parseDate(strFrom);
		Date to = Utility.parseDate(strTo);

		Map<Date, Map<NetworkEquipment, Integer>> orders = service
				.getOrders().getOrdersInTimeFrame(from, to);
		System.out.println("Orders :");
		for(Entry<Date, Map<NetworkEquipment, Integer>> entry : orders.entrySet()){

			System.out.println("Purchase was made :" + entry.getKey());

			for(Entry<NetworkEquipment, Integer> purchase : entry.getValue().entrySet()){
				System.out.println("good :" + purchase.getKey() + ", count :" + purchase.getValue());
			}
		}
	}

	public static void showNearestOrder(ShopService service, Scanner scanner){
		System.out.println("Input date in next format - yyyy-MM-dd HH:mm");
		System.out.println("Date :");
		String strDate = scanner.next();
		Date date = Utility.parseDate(strDate);

		Entry<Date, Map<NetworkEquipment, Integer>> entry = service
				.getOrders().getNearestOrder(date);
		System.out.println("Order :");
		if (entry != null) {
			System.out.println("Purchase was made :" + entry.getKey());

			for (Entry<NetworkEquipment, Integer> purchase : entry.getValue().entrySet()) {
				System.out.println("good :" + purchase.getKey()	+ ", count :" + purchase.getValue());
			}
		}
	}

	public static void addNewGood(ShopService service, Scanner scanner,
			EquipmentGenerator generator, Locale locale) {
		ResourceBundle bundle = ResourceBundle.getBundle(basename, locale);
		System.out.println(bundle.getString("which.product.would.you.like.to.create"));
		System.out.println(bundle.getString("router.or.wirelessrouter") + ", "
				+ bundle.getString("please.type.in.type.of.device"));

		String product = scanner.next();
		NetworkEquipment newEquip = null;
		try{
			newEquip = generator.generate(product);
		}catch(Exception ex){
			System.out.println(bundle.getString("cannot.generate.good"));
		}
		if(newEquip != null){
			service.getGoods().addGood(newEquip);
			System.out.println(bundle.getString("new.good.was.added"));
		}
	}
}
