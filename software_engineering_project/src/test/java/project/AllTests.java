package project;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ OrderDetailsTest.class, OrderTest.class, PaymentsQueryTest.class, PaymentTest.class,
		ProductsNotSoldTest.class, ProductTest.class, ProfitTest.class })
public class AllTests {

}
