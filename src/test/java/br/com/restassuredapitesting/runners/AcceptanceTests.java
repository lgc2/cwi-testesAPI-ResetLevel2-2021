package br.com.restassuredapitesting.runners;

import br.com.restassuredapitesting.tests.booking.tests.DeleteBookingTest;
import br.com.restassuredapitesting.tests.booking.tests.GetBookingTest;
import br.com.restassuredapitesting.tests.booking.tests.PostBookingTest;
import org.junit.experimental.categories.Categories;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Categories.class)
@Categories.IncludeCategory(br.com.restassuredapitesting.suites.AcceptanceTests.class)
@Suite.SuiteClasses({
        DeleteBookingTest.class,
        GetBookingTest.class,
        PostBookingTest.class
})
public class AcceptanceTests {
}
