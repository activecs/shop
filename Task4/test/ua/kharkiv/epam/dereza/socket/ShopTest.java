package ua.kharkiv.epam.dereza.socket;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ua.kharkiv.epam.dereza.bean.NetworkEquipment;
import ua.kharkiv.epam.dereza.bean.Router;
import ua.kharkiv.epam.dereza.dao.Basket;
import ua.kharkiv.epam.dereza.dao.Goods;
import ua.kharkiv.epam.dereza.dao.Orders;
import ua.kharkiv.epam.dereza.service.AdReccomendation;
import ua.kharkiv.epam.dereza.service.ShopService;

public class ShopTest {

	RestrictedShopService service;

	@Mock
	Basket basket;
	@Mock
	Goods goods;
	@Mock
	Orders orders;
	@Mock
	AdReccomendation reccomendation;

	Router r1, r2, r3;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		service = new ShopService(basket, goods, orders, reccomendation);
		List<NetworkEquipment> list = new LinkedList<NetworkEquipment>();
		r1 = new Router("1", 1., 1, new BigDecimal(1.), "1", "1");
		r2 = new Router("2", 2., 2, new BigDecimal(2.), "2", "2");
		r3= new Router("3", 3., 3, new BigDecimal(3.), "3", "3");
		list.add(r1);
		list.add(r2);
		list.add(r3);
		doReturn(list).when(goods).getListOfAvaliableGoods();
		doReturn(1).when(goods).getAvaliableCountOfGood(eq(r1));
		doReturn(2).when(goods).getAvaliableCountOfGood(eq(r2));
		doReturn(3).when(goods).getAvaliableCountOfGood(eq(r3));
	}

	@After
	public void tearDown() throws Exception {
		reset(basket, goods, orders, reccomendation);
		service = null;
	}

	@Test
	public void countGoodInShopTest() {
		assertEquals(6, service.countGoodInShop());

	}

	@Test
	public void foundGoodByModelTest(){
		assertEquals(r1, service.foundGoodByModel("1"));
	}

}
