package br.com.restassuredapitesting.runners;

import br.com.restassuredapitesting.tests.booking.tests.PutBookingTest;
import org.junit.experimental.categories.Categories;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Categories.class)
@Categories.IncludeCategory(br.com.restassuredapitesting.suites.SecurityTests.class)
@Suite.SuiteClasses({
        PutBookingTest.class
})
public class SecurityTests {
}
