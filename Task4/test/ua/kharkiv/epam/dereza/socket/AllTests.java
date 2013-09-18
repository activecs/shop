package ua.kharkiv.epam.dereza.socket;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ ServerTest.class, ShopTest.class, ClientTest.class })
public class AllTests {

}
